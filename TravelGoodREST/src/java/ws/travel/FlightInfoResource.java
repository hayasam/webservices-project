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
import ws.travel.data.FlightInfo;
import ws.travel.data.FlightInfos;
import ws.travel.data.Itinerary;
import ws.travel.services.FlightService;

/**
 *
 * @author VAIO
 */
@Path("users/{userid}/itinerary/{itineraryid}/flights")
public class FlightInfoResource {
    
    /**
     * @GET
     * [Caecilie]
     * Implement get possible flights with query string.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML) 
    public Response getFlights(@PathParam("userid") String userid,
                               @PathParam("itineraryid") String itineraryid,
                               @QueryParam("date") String date,
                               @QueryParam("startAirport") String startAirport,
                               @QueryParam("endAirport") String endAirport) {
        
        List<FlightInfo> flights = FlightService.getFlights(date, startAirport, endAirport);
        return Response.ok(new FlightInfos(flights)).build();
    }
    
    /**
     * @POST
     * @Path("add")
     * [Oguz]
     * Implement add to itinerary.
     */
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addFlightToItinerary(@PathParam("userid") String userid,
                                    @PathParam("itineraryId") String itineraryId,
                                    FlightInfo flight)
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
        
        itinerary.addFlightToItinerary(flight);
        return Response.ok("OK").build();
    }
}
