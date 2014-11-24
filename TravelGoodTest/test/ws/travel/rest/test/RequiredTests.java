/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.test;

import com.sun.jersey.api.client.Client;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.xml.schema.itinerarydata.CreditCardInfoType;
import ws.travel.bpel.test.TestUtils;
import ws.travel.rest.data.CreditCard;
import ws.travel.rest.data.FlightInfo;
import ws.travel.rest.data.HotelInfo;
import ws.travel.rest.data.Itinerary;
import ws.travel.rest.representation.*;

/**
 *
 * @author VAIO
 */
public class RequiredTests {
    
    Client client;
    
    private static final String TRAVELGOOD_ENDPOINT = "http://localhost:8080/TravelGoodREST/webresources";
    private static final String ITINERARY_CREATED = "itinerary successfully created";
    private static final String FLIGHT_ADDED        = "flight added to itinerary";
    private static final String HOTEL_ADDED         = "hotel added to itinerary";
    private static final String ITINERARY_TERMINATED = "itinerary terminated";
    private static final String ITINERARY_SUCCESSFULLY_BOOKED = "itinerary successfully booked";
    private static final String ITINERARY_NOT_FULLY_CANCELLED = "Not all bookings were canceled";
    
    private static final String RELATION_BASE = "http://travelgood.ws/relations/";
    private static final String CANCEL_RELATION = RELATION_BASE + "cancel";
    private static final String STATUS_RELATION = RELATION_BASE + "status";
    private static final String BOOK_RELATION = RELATION_BASE + "book";
    private static final String GET_FLIGHTS_RELATION = RELATION_BASE + "getFlights";
    private static final String GET_HOTELS_RELATION = RELATION_BASE + "getHotels";
    private static final String ADD_FLIGHT_RELATION = RELATION_BASE + "addFlight";
    private static final String ADD_HOTEL_RELATION = RELATION_BASE + "addHotel";
    private static final String CREATE_ITINERARY = RELATION_BASE + "createItinerary";

    @Before
    public void reset() {
        client = Client.create();
    }
    
    @Test
    public void testP1() {
        String userid       = "userP1";
        String itineraryid  = "itineraryP1";
        
        // create an itinerary
        assertCreateItinerary(userid, itineraryid);
        
        // get possible flights
        FlightInfo flightInfo1 = getAFlight(userid, itineraryid, "07-11-2014", "Copenhagen Lufthavnen", "Bucharest Otopeni");
        // add and assert
        assertAddFlight(userid, itineraryid, flightInfo1);
        
        // get a hotel
        HotelInfo hotelInfo1 = getAHotel(userid, itineraryid, "Paris", "07-11-2014", "10-11-2014");
        // add and assert
        assertAddHotel(userid, itineraryid, hotelInfo1);
        
        // get other possible flights
        FlightInfo flightInfo2 = getAFlight(userid, itineraryid, "18-12-2014", "Warsaw", "Madrid");
        // add and assert
        assertAddFlight(userid, itineraryid, flightInfo2);
        
        // get another flight
        FlightInfo flightInfo3 = getAFlight(userid, itineraryid, "21-01-2015", "Oslo", "Malmo");
        // add and assert
        assertAddFlight(userid, itineraryid, flightInfo3);
        
        // get another hotel
        HotelInfo hotelInfo2 = getAHotel(userid, itineraryid, "Bucharest", "07-11-2014", "10-11-2014");
        // add and assert
        assertAddHotel(userid, itineraryid, hotelInfo2);
        
        // assert itinerary is in unconfirmed status and have 5 items in.
        assertGetItinerary(userid, itineraryid, "UNCONFIRMED", 5);
        
        // book itinerary
        assertBookItinerary(userid, itineraryid, createValidCreditCard());
        
        // assert itinerary is in confirmed status and have 5 items in.
        assertGetItinerary(userid, itineraryid, "CONFIRMED", 5);
    }
    
    private HotelInfo getAHotel(String userid, String itineraryid, String city, String arrival, String departure) {
        HotelsRepresentation hotelsRep = client.resource(hotelsUrl(userid, itineraryid))
                                                .queryParam("city", city)
                                                .queryParam("arrival", arrival)
                                                .queryParam("departure", departure)
                                                .accept(MediaType.APPLICATION_XML)
                                                .get(HotelsRepresentation.class);
        assertTrue(hotelsRep.getHotelInfo().size() > 0);
        assertHaveLinks(hotelsRep, ADD_HOTEL_RELATION, CANCEL_RELATION, BOOK_RELATION, 
                                GET_FLIGHTS_RELATION, STATUS_RELATION, GET_HOTELS_RELATION);
        return hotelsRep.getHotelInfo().get(0);
    }
    
    private FlightInfo getAFlight(String userid, String itineraryid, String date, String startAirport, String endAirport) {
        // get possible flights
        FlightsRepresentation flightsRep = client.resource(flightsUrl(userid, itineraryid))
                                             .queryParam("date", date)
                                             .queryParam("startAirport", startAirport)
                                             .queryParam("endAirport", endAirport)
                                             .accept(MediaType.APPLICATION_XML)
                                             .get(FlightsRepresentation.class);
        assertTrue(flightsRep.getFlightInfo().size() > 0);
        assertHaveLinks(flightsRep, ADD_FLIGHT_RELATION, CANCEL_RELATION, BOOK_RELATION, 
                                GET_FLIGHTS_RELATION, STATUS_RELATION, GET_HOTELS_RELATION);
        return flightsRep.getFlightInfo().get(0);
    }
    
    private void assertCreateItinerary(String userid, String itineraryid) {
        StatusRepresentation statusRep = client.resource(itineraryUrl(userid, itineraryid))
                                                .accept(MediaType.APPLICATION_XML)
                                                .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, statusRep.getStatus());
    }
    
    private void assertGetItinerary(String userid, String itineraryid, String expectedStatus, int expectedCount) {
        ItineraryRepresentation itineraryRep = client.resource(itineraryUrl(userid, itineraryid))
                                                    .accept(MediaType.APPLICATION_XML)
                                                    .get(ItineraryRepresentation.class);
        assertEquals(expectedCount, itineraryRep.getItinerary().getFlightInfos().size()
                                        + itineraryRep.getItinerary().getHotelInfos().size());
        assertEquals(expectedStatus, itineraryRep.getItinerary().getStatus());
        assertHaveLinks(itineraryRep, BOOK_RELATION, CANCEL_RELATION, GET_FLIGHTS_RELATION, 
                                            GET_HOTELS_RELATION);
    }
    
    private void assertBookItinerary(String userid, String itineraryid, CreditCard ccInfo) {
        StatusRepresentation bookingStatus = client.resource(bookItineraryUrl(userid, itineraryid))
                                                   .accept(MediaType.APPLICATION_XML)
                                                   .entity(ccInfo)
                                                   .post(StatusRepresentation.class);
        assertEquals(ITINERARY_SUCCESSFULLY_BOOKED, bookingStatus.getStatus());
        assertHaveLinks(bookingStatus, STATUS_RELATION, CANCEL_RELATION);
    }
    
    private void assertAddHotel(String userid, String itineraryid, HotelInfo hotelInfo) {
        StatusRepresentation addHotelStatus = client.resource(addHotelUrl(userid, itineraryid))
                                                    .type(MediaType.APPLICATION_XML)
                                                    .accept(MediaType.APPLICATION_XML)
                                                    .entity(hotelInfo)
                                                    .post(StatusRepresentation.class);
        assertEquals(HOTEL_ADDED, addHotelStatus.getStatus());
        assertHaveLinks(addHotelStatus, BOOK_RELATION, CANCEL_RELATION, GET_HOTELS_RELATION, 
                                        GET_FLIGHTS_RELATION, STATUS_RELATION);
    } 
    
    private void assertAddFlight(String userid, String itineraryid, FlightInfo flightInfo) {
        // add another flight
        StatusRepresentation statusRep = client.resource(addFlightUrl(userid, itineraryid))
                                                .type(MediaType.APPLICATION_XML)
                                                .accept(MediaType.APPLICATION_XML)
                                                .entity(flightInfo)
                                                .post(StatusRepresentation.class);
        assertEquals(FLIGHT_ADDED, statusRep.getStatus());
        assertHaveLinks(statusRep, BOOK_RELATION, CANCEL_RELATION, GET_HOTELS_RELATION, 
                                        GET_FLIGHTS_RELATION, STATUS_RELATION);
    }
    
    private void assertHaveLinks(Representation rep, String... relations) {
        if(relations == null || relations.length == 0) {
            fail("There should be at least one relation to assert!");
        }
        for(String relation : relations) {
            assertNotNull(rep.getLinkByRelation(relation));
        }
    }
    
    /*
     * Monica
     */
    @Test
    public void testP2 () {
        String userid       = "userP2";
        String itineraryid  = "itineraryP2";
        
        // create itinerary
        StatusRepresentation result = client.resource(itineraryUrl(userid, itineraryid))
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
                        
        // add a flight
        assertAddFlight(userid, itineraryid, getAFlight(userid, itineraryid, "07-11-2014", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        
        // cancel planning
        StatusRepresentation cancelStatus = client.resource(cancelItineraryUrl(userid, itineraryid))
                .type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .entity(createValidCreditCard())
                .post(StatusRepresentation.class);
        
        assertEquals(ITINERARY_TERMINATED, cancelStatus.getStatus());
        
        /*
         * Test links, next possible actions are:
         * -create itinerary
         */
        Link createLink = cancelStatus.getLinkByRelation(CREATE_ITINERARY);
        assertNotNull(createLink);
        assertEquals(itineraryUrl(userid, itineraryid), createLink.getUri());
        
    }

    @Test
    public void testB() {
        String userid       = "userB";
        String itineraryid  = "itineraryB";
        
        // create itinerary
        StatusRepresentation statusRep = client.resource(itineraryUrl(userid, itineraryid))
                                                .accept(MediaType.APPLICATION_XML)
                                                .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, statusRep.getStatus());
        
        // get possible flights
        FlightInfo flightInfo1 = getAFlight(userid, itineraryid, "07-11-2014", "Copenhagen Lufthavnen", "Bucharest Otopeni");
        // add and assert
        assertAddFlight(userid, itineraryid, flightInfo1);
        assertEquals("UNCONFIRMED", flightInfo1.getStatus());
        
        // get a hotel
        HotelInfo hotelInfo1 = getAHotel(userid, itineraryid, "Paris", "07-11-2014", "10-11-2014");
        // add and assert
        assertAddHotel(userid, itineraryid, hotelInfo1);
        assertEquals("UNCONFIRMED", flightInfo1.getStatus());
        
        // get other possible flights
        FlightInfo flightInfo2 = getAFlight(userid, itineraryid, "18-12-2014", "Warsaw", "Madrid");
        // add and assert
        assertAddFlight(userid, itineraryid, flightInfo2);
        assertEquals("UNCONFIRMED", flightInfo1.getStatus());  
        
        //To be cotinued
    }
    
    @Test
    public void testC1()
    {
        String userid       = "userC1";
        String itineraryid  = "itineraryC1";
        
        /* Create itinerary with three bookings and book it */
        StatusRepresentation result = client.resource(itineraryUrl(userid, itineraryid))
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        // get possible flights
        List<FlightInfo> flightInfos1 = client.resource(flightsUrl(userid, itineraryid))
                                                .queryParam("date", "07-11-2014")
                                                .queryParam("startAirport", "Copenhagen Lufthavnen")
                                                .queryParam("endAirport", "Bucharest Otopeni")
                                                .accept(MediaType.APPLICATION_XML)
                                                .get(FlightsRepresentation.class)
                                                .getFlightInfo();
        
        FlightInfo flightInfo1 = flightInfos1.get(0);
        client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfo1)
              .post();
        
        List<FlightInfo> flightInfos2 = client.resource(flightsUrl(userid, itineraryid))
                                        .queryParam("date", "10-11-2014")
                                        .queryParam("startAirport", "Bucharest Otopeni")
                                        .queryParam("endAirport", "Copenhagen Lufthavnen")
                                        .accept(MediaType.APPLICATION_XML)
                                        .get(FlightsRepresentation.class)
                                        .getFlightInfo();
        
        FlightInfo flightInfo2 = flightInfos2.get(0);
        client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfo2)
              .post();
        
        // get possible hotels
        List<HotelInfo> hotelInfos = client.resource(hotelsUrl(userid, itineraryid))
                                            .queryParam("city", "Bucharest")
                                            .queryParam("arrival", "07-11-2014")
                                            .queryParam("departure", "10-11-2014")
                                            .accept(MediaType.APPLICATION_XML)
                                            .get(HotelsRepresentation.class)
                                            .getHotelInfo();
        HotelInfo hotelInfo1 = hotelInfos.get(0);
        
        // add a hotel
        client.resource(addHotelUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(hotelInfo1)
              .post();
        
        //book itinerary
        StatusRepresentation bookResult = client.resource(bookItineraryUrl(userid, itineraryid))
                .type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .post(StatusRepresentation.class, createValidCreditCard());
        
        assertEquals(bookResult.getStatus(), ITINERARY_SUCCESSFULLY_BOOKED);
        
        /* get itinerary and make sure that status is confirmed for each entry */
        ItineraryRepresentation itineraryResult = client.resource(itineraryUrl(userid, itineraryid))
                      .accept(MediaType.APPLICATION_XML)
                      .get(ItineraryRepresentation.class);
        
        for(FlightInfo flight : itineraryResult.getItinerary().getFlightInfos())
        {
            assertEquals(flight.getStatus(), "CONFIRMED");
        }
        
        for(HotelInfo hotel : itineraryResult.getItinerary().getHotelInfos())
        {
            assertEquals(hotel.getStatus(), "CONFIRMED");
        }

        /* cancel trip and chceck that booking status is cancelled for each entry */
        ItineraryRepresentation cancelResult = client.resource(cancelItineraryUrl(userid, itineraryid))
                .type(MediaType.APPLICATION_XML)
                .post(ItineraryRepresentation.class, createValidCreditCard());
        
        assertEquals(cancelResult.getItinerary().getStatus(), "CANCELLED");
        
        ItineraryRepresentation cancelledItineraryResult = client.resource(itineraryUrl(userid, itineraryid))
              .accept(MediaType.APPLICATION_XML)
              .get(ItineraryRepresentation.class);
        
        for(FlightInfo flight : cancelledItineraryResult.getItinerary().getFlightInfos())
        {
            assertEquals(flight.getStatus(), "CANCELLED");
        }
        
        for(HotelInfo hotel : cancelledItineraryResult.getItinerary().getHotelInfos())
        {
            assertEquals(hotel.getStatus(), "CANCELLED");
        }
    }
    
    /*
     * Monica
     */
    public void testC2() {
        String userid       = "userC2";
        String itineraryid  = "itineraryC2";
        
         // create itinerary
        StatusRepresentation result = client.resource(itineraryUrl(userid, itineraryid))
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        // add a flight
        assertAddFlight(userid, itineraryid, getAFlight(userid, itineraryid, "07-11-2014", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
               
        // add a second flight
        assertAddFlight(userid, itineraryid, getAFlight(userid, itineraryid, "21-01-2015", "Oslo", "Malmo"));
                        
        // add a hotel
        assertAddHotel(userid, itineraryid, getAHotel(userid, itineraryid, "Paris", "07-11-2014", "10-11-2014"));
        
        // book itinerary
        assertBookItinerary(userid, itineraryid, createValidCreditCard());
                
        // check booking status for each flight
        assertBookingsConfirmed(userid, itineraryid);
        
        // cancel itinerary
        StatusRepresentation cancelStatus = client.resource(cancelItineraryUrl(userid, itineraryid))
                .type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .entity(createInvalidCreditCard())
                .post(StatusRepresentation.class);
        assertEquals(ITINERARY_NOT_FULLY_CANCELLED, cancelStatus.getStatus());
        
        // check status of bookings
        assertSomeBookingsCanceled(userid, itineraryid);
    }

    /**
     * Helper for test C2, checking the status of bookings after failed cancelation
     * @param userid
     * @param itineraryid 
     *  Monica
     */
    private void assertSomeBookingsCanceled (String userid, String itineraryid) {
        ItineraryRepresentation itineraryRep = client.resource(itineraryUrl(userid, itineraryid))
                                                    .accept(MediaType.APPLICATION_XML)
                                                    .get(ItineraryRepresentation.class);
        Itinerary itinerary = itineraryRep.getItinerary();
        
        // check first booking is cancelled
        assertEquals("CANCELLED", itinerary.getFlightInfos().get(0).getStatus());
        
        // check second booking still confirmed
        assertEquals("CONFIRMED", itinerary.getFlightInfos().get(1).getStatus());
        
        // check third booking is cancelled
        assertEquals("CANCELLED", itinerary.getHotelInfos().get(0).getStatus());
    }
    
    /**
     * Helper for test C2, checks all bookings are confirmed after booking itinerary
     * @param userid
     * @param itineraryid 
     *  Monica
     */
    private void assertBookingsConfirmed (String userid, String itineraryid) {
        ItineraryRepresentation itineraryRep = client.resource(itineraryUrl(userid, itineraryid))
                                                    .accept(MediaType.APPLICATION_XML)
                                                    .get(ItineraryRepresentation.class);
        Itinerary itinerary = itineraryRep.getItinerary();
        
        for(FlightInfo flightInfo : itinerary.getFlightInfos())
            assertEquals("CONFIRMED", flightInfo.getStatus());
        
        for(HotelInfo hotelInfo : itinerary.getHotelInfos())
            assertEquals("CONFIRMED", hotelInfo.getStatus());
    }
    
    
    private String itineraryUrl(String userid, String itineraryid) {
        return String.format("%s/users/%s/itinerary/%s", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }
    
    private String flightsUrl(String userid, String itineraryid) {
        return String.format("%s/users/%s/itinerary/%s/flights", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }
    
    private String hotelsUrl(String userid, String itineraryid) {
        return String.format("%s/users/%s/itinerary/%s/hotels", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }
    
    private String addFlightUrl(String userid, String itineraryid) {
        return String.format("%s/users/%s/itinerary/%s/flights/add", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }
    
    private String addHotelUrl(String userid, String itineraryid) {
        return String.format("%s/users/%s/itinerary/%s/hotels/add", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }
    
    private String bookItineraryUrl(String userid, String itineraryid) {
        return String.format("%s/users/%s/itinerary/%s/book", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }

    private String cancelItineraryUrl(String userid, String itineraryid) {

        return String.format("%s/users/%s/itinerary/%s/cancel", 
                             TRAVELGOOD_ENDPOINT, userid, itineraryid);
    }


    private CreditCard createValidCreditCard () {
        CreditCardInfoType ccInfo = TestUtils.validCCInfo();
        
        CreditCard cc = new CreditCard();
        cc.setCardnumber(ccInfo.getCardNumber());
        cc.setMonth(ccInfo.getExpirationDate().getMonth());
        cc.setYear(ccInfo.getExpirationDate().getYear());
        cc.setName(ccInfo.getHolderName());
        
        return cc;
    }
    /*
     * Fails for expensive flights
     */
     private CreditCard createInvalidCreditCard () {
        CreditCardInfoType ccInfo = TestUtils.invalidCCInfo();
        
        CreditCard cc = new CreditCard();
        cc.setCardnumber(ccInfo.getCardNumber());
        cc.setMonth(ccInfo.getExpirationDate().getMonth());
        cc.setYear(ccInfo.getExpirationDate().getYear());
        cc.setName(ccInfo.getHolderName());
        
        return cc;
    }
}

