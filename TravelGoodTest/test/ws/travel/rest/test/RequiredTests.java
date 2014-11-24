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
import ws.travel.rest.data.FlightInfos;
import ws.travel.rest.data.HotelInfo;
import ws.travel.rest.data.HotelInfos;
import ws.travel.rest.representation.FlightsRepresentation;
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
        String result = client.resource(itineraryUrl(userid, itineraryid))
                              .accept(MediaType.APPLICATION_XML)
                              .put(String.class);
        assertEquals("OK", result);
        
        // get possible flights
        List<FlightInfo> flightInfos = client.resource(flightsUrl(userid, itineraryid))
                                             .queryParam("date", "07-11-2014")
                                             .queryParam("startAirport", "Copenhagen Lufthavnen")
                                             .queryParam("endAirport", "Bucharest Otopeni")
                                             .accept(MediaType.APPLICATION_XML)
                                             .get(FlightInfos.class)
                                             .getFlightInfo();
        FlightInfo flightInfo1 = flightInfos.get(0);
        FlightInfo flightInfo2 = flightInfos.get(1);
        
        // add a flight
        client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfo1)
              .post();
        
        // get possible hotels
        List<HotelInfo> hotelInfos = client.resource(hotelsUrl(userid, itineraryid))
                                            .queryParam("", "")
                                            .queryParam("", "")
                                            .queryParam("", "")
                                            .accept(MediaType.APPLICATION_XML)
                                            .get(HotelInfos.class)
                                            .getHotelInfo();
        HotelInfo hotelInfo1 = hotelInfos.get(0);
        HotelInfo hotelInfo2 = hotelInfos.get(1);
        
        // add a hotel
        client.resource(addHotelUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(hotelInfo1)
              .post();
        
        // add two more flights
        client.resource(addFlightUrl(userid, itineraryid))
              .type(MediaType.APPLICATION_XML)
              .accept(MediaType.APPLICATION_XML)
              .entity(flightInfo2)
              .post();
        // to be continued
        
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
