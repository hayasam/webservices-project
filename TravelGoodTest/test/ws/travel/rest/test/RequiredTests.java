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
import ws.travel.rest.data.FlightInfo;
import ws.travel.rest.data.FlightInfos;
import ws.travel.rest.data.HotelInfo;
import ws.travel.rest.data.HotelInfos;
import ws.travel.rest.representation.FlightsRepresentation;
import ws.travel.rest.representation.HotelsRepresentation;
import ws.travel.rest.representation.ItineraryRepresentation;
import ws.travel.rest.representation.StatusRepresentation;

/**
 *
 * @author VAIO
 */
public class RequiredTests {
    
    Client client;
    
    private static final String TRAVELGOOD_ENDPOINT = "http://localhost:8080/TravelGoodREST/webresources";
    
    private static final String ITINERARY_CREATED   = "itinerary successfully created";
    private static final String FLIGHT_ADDED        = "flight added to itinerary";
    private static final String HOTEL_ADDED         = "hotel added to itinerary";
    
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
}
