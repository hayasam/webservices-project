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
import ws.travel.rest.data.FlightInfo;
import ws.travel.rest.data.FlightInfos;
import ws.travel.rest.data.HotelInfo;
import ws.travel.rest.data.HotelInfos;
import ws.travel.rest.representation.*;

/**
 *
 * @author VAIO
 */
public class RequiredTests {
    
    Client client;
    
    private static final String TRAVELGOOD_ENDPOINT = "http://localhost:8080/TravelGoodREST/webresources";
    private static final String ITINERARY_CREATED = "itinerary successfully created";
    
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
    public void testC1()
    {
        String userid       = "userC1";
        String itineraryid  = "itineraryC1";
        
        // create itinerary
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
        CreditCardInfoType ccInfo = TestUtils.validCCInfo();
        ItineraryRepresentation bookResult = client.resource(bookItineraryUrl(userid, itineraryid))
                .type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .post(ItineraryRepresentation.class, ccInfo);
        
        assertEquals(bookResult.getItinerary().getStatus(), "CONFIRMED");
        
        
        //itinerary with three bookings - flights + hotels and book it
        //get itinerary and make sure that status is confirmed for each entry
        //cancel trip and chceck that booking status is cancelled for each entry
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
}
