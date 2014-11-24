/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.travel.ItineraryResource.ItineraryStatus;
import ws.travel.data.HotelInfo;
import ws.travel.data.HotelInfos;
import ws.travel.data.Itinerary;
import ws.travel.services.HotelService;
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
    
    /**
     * @GET
     * [Audrius]
     * Implement get possible hotels with query string.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getHotels(@PathParam("userid") String userid, 
                              @PathParam("itineraryid") String itineraryid,
                              @QueryParam("city") String city,
                              @QueryParam("arrival") String arrival,
                              @QueryParam("departure") String departure) {
        List<HotelInfo> hotels = HotelService.getHotels(city, arrival, departure);
        return Response.ok(new HotelInfos(hotels)).build();
    }
        


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
        
        ItineraryResource.addCancelLink(userid, itineraryId, status);
        ItineraryResource.addBookLink(userid, itineraryId, status);
        ItineraryResource.addGetItineraryLink(userid, itineraryId, status);
        ItineraryResource.addGetFlightsLink(userid, itineraryId, status);
        ItineraryResource.addGetHotelsLink(userid, itineraryId, status);
        
        return Response.ok(status).build();
    }
}
