/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import ws.travel.rest.data.Itinerary;
import static org.junit.Assert.*;
import ws.travel.rest.data.FlightInfo;
import ws.travel.rest.data.FlightInfos;
import ws.travel.rest.data.HotelInfo;
import ws.travel.rest.data.HotelInfos;
import ws.travel.rest.representation.StatusRepresentation;


/**
 *
 * @author Moni
 */
public class TravelGoodRESTTest {
    
    Client client;
    String baseURI;
    WebResource itineraries;
    
    public TravelGoodRESTTest() {
        client = Client.create();
        baseURI = "http://localhost:8080/TravelGoodREST/webresources/users";
       
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
       
    @Test
    public void createItinerary() {        
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "123", "111");
        
        String result = client.resource(itineraryURI).
                accept(MediaType.APPLICATION_XML).
                type(MediaType.APPLICATION_XML).
                put(String.class);
        
        assertEquals("OK", result);
    }
    
    @Test
    public void getItinerary () {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "123", "111");
        
        Itinerary result = client.resource(itineraryURI).
                accept(MediaType.APPLICATION_XML).
                type(MediaType.APPLICATION_XML).get(Itinerary.class);
        
        
        assertEquals("UNCONFIRMED", result.getStatus());
    }
    
    @Test
    public void getPossibleHotels() {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "user1", "itinerary1");
        
        // create itinerary first
        String result = client.resource(itineraryURI)
                              .accept(MediaType.APPLICATION_XML)
                              .put(String.class);
        assertEquals("OK", result);
        
        String possibleHotelsURI = String.format("%s/hotels", itineraryURI);
        
        // get possible hotels
        List<HotelInfo> hotelInfos = client.resource(possibleHotelsURI)
                                      .queryParam("city", "Paris")
                                      .queryParam("arrival", "07-11-2014")
                                      .queryParam("departure", "10-11-2014")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(HotelInfos.class).getHotelInfo();
        assertEquals(2, hotelInfos.size());
        assertEquals("UNCONFIRMED", hotelInfos.get(0).getStatus());
        assertEquals("UNCONFIRMED", hotelInfos.get(1).getStatus());
    }
    
    @Test
    public void getPossibleFlights() {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "123", "111");
        
        String createItinerary_result = client.resource(itineraryURI)
                                        .accept(MediaType.APPLICATION_XML)
                                        .put(String.class);
        assertEquals("OK", createItinerary_result);
        
        String possibleFlightsURI = String.format("%s/flights", itineraryURI);
        
        // get possible flights
        List<FlightInfo> flights = client.resource(possibleFlightsURI)
                                      .queryParam("date", "07-11-2014")
                                      .queryParam("startAirport", "Copenhagen Lufthavnen")
                                      .queryParam("endAirport", "Bucharest Otopeni")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(FlightInfos.class).getFlightInfo();
        
        assertEquals("UNCONFIRMED", flights.get(0).getStatus());
        assertEquals("UNCONFIRMED", flights.get(1).getStatus());
    }
    
     @Test
    public void addFlightToItinerary () {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "123", "111");
        
        // create itinerary first
        String result = client.resource(itineraryURI)
                              .accept(MediaType.APPLICATION_XML)
                              .put(String.class);
        assertEquals("OK", result);
        
        String possibleFlightsURI = String.format("%s/flights", itineraryURI);
        
        // get possible flights
        List<FlightInfo> flights = client.resource(possibleFlightsURI)
                                      .queryParam("date", "07-11-2014")
                                      .queryParam("startAirport", "Copenhagen Lufthavnen")
                                      .queryParam("endAirport", "Bucharest Otopeni")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(FlightInfos.class).getFlightInfo();
        
        String addFlightURI = String.format("%s/add", possibleFlightsURI);
        StatusRepresentation status;
        status = client.resource(addFlightURI)
                       .accept(MediaType.APPLICATION_XML)
                       .type(MediaType.APPLICATION_XML)
                       .post(StatusRepresentation.class, flights.get(0));
        
        assertEquals("FLIGHT ADDED", status.getStatus());
        
                
    }
}