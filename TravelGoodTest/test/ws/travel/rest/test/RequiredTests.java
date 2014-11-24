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

/**
 *
 * @author VAIO
 */
public class RequiredTests {
    
    Client client;
    
    private static final String TRAVELGOOD_ENDPOINT = "http://localhost:8080/TravelGoodREST/webresources";
    
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
