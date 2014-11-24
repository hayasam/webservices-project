/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import ws.travel.rest.data.Itinerary;
import static org.junit.Assert.*;


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
}