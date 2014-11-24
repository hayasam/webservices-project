/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.travel.ItineraryResource.ItineraryStatus;
import ws.travel.data.HotelInfo;
import ws.travel.data.Itinerary;
import ws.travel.representation.Link;
import ws.travel.representation.Representation;
import ws.travel.representation.StatusRepresentation;

/**
 *
 * @author VAIO
 */

@Path("users/{userid}/itinerary/{itineraryid}/hotels")
public class HotelInfoResource {
    
    private static final String ITINERARY_NOT_FOUND = "itinerary not found";
    private static final String ITINERARY_BOOKED_ALREADY = "itinerary booked already";
    private static final String ITINERARY_CANCELLED_ALREADY = "itinerary cancelled already"; 

    public static final String BASE_URI = "http://localhost:8080/TravelGoodREST/webresources/";
    
    /**
     * @GET
     * [Audrius]
     * Implement get possible hotels with query string.
     */
        


    /**
     * 
     * @Path("add")
     * [Paulina]
     * Implement add to itinerary.
     */
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addHotelToItinerary(@PathParam("userid") String userid,
                                        @PathParam("itineraryId") String itineraryId,
                                        HotelInfo hotel)
    {
        StatusRepresentation status = new StatusRepresentation();
        Itinerary itinerary = ItineraryPool.getItinerary(userid, itineraryId);
        
        //itinerary doesn't exist
        if (itinerary == null)
        {
                return Response.
                    status(Response.Status.NOT_FOUND).
                    entity(ITINERARY_NOT_FOUND).
                    build();
        }
        
        //itinerary is already booked or cancelled
        if (itinerary.getStatus().equals(ItineraryStatus.CONFIRMED.toString()))
        {
                return Response.
                    status(Response.Status.NOT_ACCEPTABLE).
                    entity(ITINERARY_BOOKED_ALREADY).
                    build();
        }
        
        if (itinerary.getStatus().equals(ItineraryStatus.CANCELLED.toString()))
        {
                return Response.
                    status(Response.Status.NOT_ACCEPTABLE).
                    entity(ITINERARY_CANCELLED_ALREADY).
                    build();
        }
        
        itinerary.addHotelToItinerary(hotel);
        
        addCancelLink(userid, itineraryId, status);
        addBookLink(userid, itineraryId, status);
        addGetItineraryLink(userid, itineraryId, status);
        
        return Response.ok(status).build();
    }
    
    
    //add cancel link
    static void addCancelLink(String userId, String itineraryId, Representation response) {
        Link link = new Link();
        link.setUri(String.format("users/%s/itinerary/%s/cancel", BASE_URI, userId, itineraryId));
        //link.setRel(CANCEL_RELATION);
        response.getLinks().add(link);
    }
    
    //add book link
    static void addBookLink(String userId, String itineraryId, Representation response) {
        Link link = new Link();
        link.setUri(String.format("users/%s/itinerary/%s/book", BASE_URI, userId, itineraryId));
        //link.setRel(CANCEL_RELATION);
        response.getLinks().add(link);
    }
   
    //add get itinerary link
    static void addGetItineraryLink(String userId, String itineraryId, Representation response) {
        Link link = new Link();
        link.setUri(String.format("users/%s/itinerary/%s", BASE_URI, userId, itineraryId));
        //link.setRel(CANCEL_RELATION);
        response.getLinks().add(link);
    }
    
    //get flights
    //get hotels
    
}
