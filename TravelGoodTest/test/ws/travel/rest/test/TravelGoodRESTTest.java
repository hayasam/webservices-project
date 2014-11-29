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
import static org.junit.Assert.*;
import ws.travel.rest.data.FlightInfo;
import ws.travel.rest.data.HotelInfo;
import ws.travel.rest.representation.FlightsRepresentation;
import ws.travel.rest.representation.HotelsRepresentation;
import ws.travel.rest.representation.ItineraryRepresentation;
import ws.travel.rest.representation.StatusRepresentation;


/**
 *
 * @author Moni
 */
public class TravelGoodRESTTest {
    
    Client client;
    String baseURI;
    WebResource itineraries;
    
    private static final String FLIGHT_ADDED = "flight added to itinerary";
    private static final String ITINERARY_CREATED = "itinerary successfully created";
     
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
        
        StatusRepresentation result = client.resource(itineraryURI).
                accept(MediaType.APPLICATION_XML).
                type(MediaType.APPLICATION_XML).
                put(StatusRepresentation.class);
        
        assertEquals(ITINERARY_CREATED, result.getStatus());
    }
    
    @Test
    public void getItinerary () {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "123", "111");
        
        StatusRepresentation result = client.resource(itineraryURI).
                accept(MediaType.APPLICATION_XML).
                type(MediaType.APPLICATION_XML).
                put(StatusRepresentation.class);
        
        assertEquals(ITINERARY_CREATED, result.getStatus());
                
        ItineraryRepresentation getResult = client.resource(itineraryURI).
                accept(MediaType.APPLICATION_XML).
                type(MediaType.APPLICATION_XML).get(ItineraryRepresentation.class);
        
        
        assertEquals("UNCONFIRMED", getResult.getItinerary().getStatus());
    }
    
    @Test
    public void getPossibleHotels() {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "user1", "itinerary1");
        
        // create itinerary first
        StatusRepresentation result = client.resource(itineraryURI)
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        String possibleHotelsURI = String.format("%s/hotels", itineraryURI);
        
        // get possible hotels
        List<HotelInfo> hotelInfos = client.resource(possibleHotelsURI)
                                      .queryParam("city", "Paris")
                                      .queryParam("arrival", "07-11-2014")
                                      .queryParam("departure", "10-11-2014")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(HotelsRepresentation.class).getHotelInfo();
        assertEquals(2, hotelInfos.size());
        assertEquals("UNCONFIRMED", hotelInfos.get(0).getStatus());
        assertEquals("UNCONFIRMED", hotelInfos.get(1).getStatus());
    }
    
    @Test
    public void getPossibleFlights() {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "user2", "itinerary2");
        
        StatusRepresentation result = client.resource(itineraryURI)
                                .accept(MediaType.APPLICATION_XML)
                                .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        String possibleFlightsURI = String.format("%s/flights", itineraryURI);
        
        // get possible flights
        List<FlightInfo> flightinfos = client.resource(possibleFlightsURI)
                                      .queryParam("date", "07-11-2014")
                                      .queryParam("startAirport", "Copenhagen Lufthavnen")
                                      .queryParam("endAirport", "Bucharest Otopeni")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(FlightsRepresentation.class).getFlightInfo();
        assertEquals(2, flightinfos.size());
        assertEquals("UNCONFIRMED", flightinfos.get(0).getStatus());
        assertEquals("UNCONFIRMED", flightinfos.get(1).getStatus());
    }
    
     @Test
    public void addFlightToItinerary () {
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "123", "111");
        
        // create itinerary first
        StatusRepresentation result = client.resource(itineraryURI)
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        String possibleFlightsURI = String.format("%s/flights", itineraryURI);
        
        // get possible flights
        List<FlightInfo> flights = client.resource(possibleFlightsURI)
                                      .queryParam("date", "07-11-2014")
                                      .queryParam("startAirport", "Copenhagen Lufthavnen")
                                      .queryParam("endAirport", "Bucharest Otopeni")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(FlightsRepresentation.class).getFlightInfo();
        
        String addFlightURI = String.format("%s/add", possibleFlightsURI);
        StatusRepresentation status;
        status = client.resource(addFlightURI)
                       .accept(MediaType.APPLICATION_XML)
                       .type(MediaType.APPLICATION_XML)
                       .post(StatusRepresentation.class, flights.get(0));
        
        assertEquals(FLIGHT_ADDED, status.getStatus());            
    }
     
    @Test
    public void getStatus (){
        String userid1       = "222";
        String itineraryid1  = "222";
        // It with good and bad deta and test
        String itineraryURI = String.format("%s/%s/itinerary/%s", baseURI, "222", "222");
        
        // create a new itinerary.
        StatusRepresentation result = client.resource(itineraryURI)
                              .accept(MediaType.APPLICATION_XML)
                              .put(StatusRepresentation.class);
        assertEquals(ITINERARY_CREATED, result.getStatus());
        
        // Add data from pass.
        String possibleFlightsURI = String.format("%s/flights", itineraryURI);
        // get possible flights
        List<FlightInfo> flights = client.resource(possibleFlightsURI)
                                      .queryParam("date", "21-11-2014")
                                      .queryParam("startAirport", "Copenhagen Lufthavnen")
                                      .queryParam("endAirport", "Amsterdam Schiphol")
                                      .accept(MediaType.APPLICATION_XML)
                                      .get(FlightsRepresentation.class).getFlightInfo();
        
        String addFlightURI = String.format("%s/add", possibleFlightsURI);
        StatusRepresentation status;
        status = client.resource(addFlightURI)
                       .accept(MediaType.APPLICATION_XML)
                       .type(MediaType.APPLICATION_XML)
                       .post(StatusRepresentation.class, flights.get(0));
        assertEquals(FLIGHT_ADDED, status.getStatus()); 
        
        ItineraryRepresentation getResult = client.resource(itineraryURI).
                accept(MediaType.APPLICATION_XML).
                type(MediaType.APPLICATION_XML).get(ItineraryRepresentation.class);
        
        // This itinerary is supposed to be invalid, because the flight is in the passed.
        assertEquals("INVALID", getResult.getItinerary().getStatus());
    }
}