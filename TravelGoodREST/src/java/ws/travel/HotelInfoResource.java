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

/**
 *
 * @author VAIO
 */

@Path("users/{userid}/itinerary/{itineraryid}/hotels")
public class HotelInfoResource {
    //itineraries
    
    /**
     * @GET
     * [Audrius]
     * Implement get possible hotels with query string.
     */
        
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
        Itinerary itinerary = ItineraryPool.getItinerary(userid, itineraryId);
        
        //itinerary doesn't exist
        if (itinerary == null)
        {
                return Response.
                    status(Response.Status.NOT_FOUND).
                    entity("Itinerary not found").
                    build();
        }
        
        //itinerary is already booked or cancelled
        if (itinerary.getStatus().equals(ItineraryStatus.UNCONFIRMED.toString()) == false)
        {
                return Response.
                    status(Response.Status.FORBIDDEN).
                    entity("Itinerary is already booked or cancelled").
                    build();
        }
        
        itinerary.addHotelToItinerary(hotel);
        return Response.ok("OK").build();
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
    
    //get flights
    //get hotels
    //getitinerary
    
    
}
