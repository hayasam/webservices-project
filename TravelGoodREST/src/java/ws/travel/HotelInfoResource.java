/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.travel.data.HotelInfo;
import ws.travel.data.Itinerary;
import ws.travel.representation.StatusRepresentation;

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
    
    
    /**
     * 
     * @Path("add")
     * [Paulina]
     * Implement add to itinerary.
     */
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addHotelToItinerary(@PathParam("userid") String userid, 
                                        @PathParam("itineraryid") String itineraryid, HotelInfo hotel)
    {
        StatusRepresentation statusRep = new StatusRepresentation();
        Itinerary itinerary = ItineraryPool.getItinerary(userid, itineraryid);
        //if itinerary is booked or cancelled
        itinerary.addHotelToItinerary(hotel);
        return Response.ok(statusRep).build();
    }
    
    
}
