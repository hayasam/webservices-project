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
import ws.travel.rest.representation.FlightsRepresentation;
import ws.travel.rest.representation.HotelsRepresentation;
import ws.travel.rest.representation.ItineraryRepresentation;
import ws.travel.rest.representation.Link;
import ws.travel.rest.representation.StatusRepresentation;

/**
 *
 * @author VAIO
 */
public class RequiredTests {
    
    Client client;
    
    private static final String TRAVELGOOD_ENDPOINT = "http://localhost:8080/TravelGoodREST/webresources";

    private static final String ITINERARY_CREATED = "itinerary successfully created";
    private static final String FLIGHT_ADDED = "flight added to itinerary";
    private static final String HOTEL_ADDED = "hotel added to itinerary";
    private static final String ITINERARY_TERMINATED = "itinerary terminated";
     
    private static final String RELATION_BASE = "http://travelgood.ws/relations/";
    private static final String CANCEL_RELATION = RELATION_BASE + "cancel";
    private static final String STATUS_RELATION = RELATION_BASE + "status";
    private static final String BOOK_RELATION = RELATION_BASE + "book";
    private static final String GET_FLGHTS_RELATION = RELATION_BASE + "getFlights";
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
        
        // create itinerary
        StatusRepresentation statusRep = client.resource(itineraryUrl(userid, itineraryid))
                                                .accept(MediaType.APPLICATION_XML)
                                                .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, statusRep.getStatus());
        
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
        
        // assert itinerary is in expected status and have 5 items in.
        assertItinerary(userid, itineraryid, "UNCONFIRMED", 5);
    }
    
    private HotelInfo getAHotel(String userid, String itineraryid, String city, String arrival, String departure) {
        HotelsRepresentation hotelsRep = client.resource(hotelsUrl(userid, itineraryid))
                                                .queryParam("city", city)
                                                .queryParam("arrival", arrival)
                                                .queryParam("departure", departure)
                                                .accept(MediaType.APPLICATION_XML)
                                                .get(HotelsRepresentation.class);
        assertTrue(hotelsRep.getHotelInfo().size() > 0);
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
        return flightsRep.getFlightInfo().get(0);
    }
    
    private void assertItinerary(String userid, String itineraryid, String expectedStatus, int expectedCount) {
        ItineraryRepresentation itineraryRep = client.resource(itineraryUrl(userid, itineraryid))
                                                    .accept(MediaType.APPLICATION_XML)
                                                    .get(ItineraryRepresentation.class);
        assertEquals(expectedCount, itineraryRep.getItinerary().getFlightInfos().size()
                                        + itineraryRep.getItinerary().getHotelInfos().size());
        assertEquals(expectedStatus, itineraryRep.getItinerary().getStatus());
    }
    
    private void assertAddHotel(String userid, String itineraryid, HotelInfo hotelInfo) {
        StatusRepresentation addHotelStatus = client.resource(addHotelUrl(userid, itineraryid))
                                                    .type(MediaType.APPLICATION_XML)
                                                    .accept(MediaType.APPLICATION_XML)
                                                    .entity(hotelInfo)
                                                    .post(StatusRepresentation.class);
        assertEquals(HOTEL_ADDED, addHotelStatus.getStatus());
    } 
    
    private void assertAddFlight(String userid, String itineraryid, FlightInfo flightInfo) {
        // add another flight
        StatusRepresentation statusRep = client.resource(addFlightUrl(userid, itineraryid))
                                                .type(MediaType.APPLICATION_XML)
                                                .accept(MediaType.APPLICATION_XML)
                                                .entity(flightInfo)
                                                .post(StatusRepresentation.class);
        assertEquals(FLIGHT_ADDED, statusRep.getStatus());
    }
    
    @Test
    public void testP2 () {
        String userid       = "userP2";
        String itineraryid  = "itineraryP2";
        
        // create itinerary
        StatusRepresentation result = client.resource(itineraryUrl(userid, itineraryid))
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        // get possible flights
        List<FlightInfo> flightInfos = client.resource(flightsUrl(userid, itineraryid))
                                             .queryParam("date", "07-11-2014")
                                             .queryParam("startAirport", "Copenhagen Lufthavnen")
                                             .queryParam("endAirport", "Bucharest Otopeni")
                                             .accept(MediaType.APPLICATION_XML)
                                             .get(FlightsRepresentation.class)
                                             .getFlightInfo();
                
        // add a flight
        StatusRepresentation status = client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfos.get(0))
              .post(StatusRepresentation.class);
        
        assertEquals(FLIGHT_ADDED, status.getStatus());
        
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
    public void testC2 () {
        String userid       = "userC2";
        String itineraryid  = "itineraryC2";
        
         // create itinerary
        StatusRepresentation result = client.resource(itineraryUrl(userid, itineraryid))
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
         // get possible flights
        List<FlightInfo> flightInfos = client.resource(flightsUrl(userid, itineraryid))
                                             .queryParam("date", "07-11-2014")
                                             .queryParam("startAirport", "Copenhagen Lufthavnen")
                                             .queryParam("endAirport", "Bucharest Otopeni")
                                             .accept(MediaType.APPLICATION_XML)
                                             .get(FlightsRepresentation.class)
                                             .getFlightInfo();
                
        // add a flight
        StatusRepresentation statusFlight1 = client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfos.get(0))
              .post(StatusRepresentation.class);
        assertEquals(FLIGHT_ADDED, statusFlight1.getStatus());
        
        // add a second flight
        StatusRepresentation statusFlight2 = client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfos.get(1))
              .post(StatusRepresentation.class);
        assertEquals(FLIGHT_ADDED, statusFlight2.getStatus());
                
        // get possible hotels
        List<HotelInfo> hotelInfos = client.resource(hotelsUrl(userid, itineraryid))
                                      .queryParam("city", "Paris")
                                      .queryParam("arrival", "07-11-2014")
                                      .queryParam("departure", "10-11-2014")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(HotelsRepresentation.class).getHotelInfo();
        
        // add a hotel
        StatusRepresentation statusHotel = client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(hotelInfos.get(0))
              .post(StatusRepresentation.class);
        assertEquals(HOTEL_ADDED, statusHotel.getStatus());
        
        // book itinerary
        //StatusRepresentation bookItinerary = client.resource()
        /**
         * IN PROGRESS
         */
        
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
}
