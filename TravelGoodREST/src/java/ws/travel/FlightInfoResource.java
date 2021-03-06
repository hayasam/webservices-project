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
import ws.travel.data.Itinerary;
import ws.travel.representation.FlightsRepresentation;
import ws.travel.representation.StatusRepresentation;
import ws.travel.services.FlightService;

/**
 *
 * @author Oguz Demir
 */
@Path("users/{userid}/itinerary/{itineraryid}/flights")
public class FlightInfoResource {
    
      
    
     /**
     * @author: Cæcilie Bach Kjærulf
     */
    @GET
    @Produces(MediaType.APPLICATION_XML) 
    public Response getFlights(@PathParam("userid") String userid,
                               @PathParam("itineraryid") String itineraryId,
                               @QueryParam("date") String date,
                               @QueryParam("startAirport") String startAirport,
                               @QueryParam("endAirport") String endAirport) {
        
        Itinerary itinerary = ItineraryPool.getItinerary(userid, itineraryId);
        if(itinerary == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(StringUtils.ITINERARY_NOT_FOUND)
                           .build();
        }
        if(itinerary.getStatus().equals("CONFIRMED")) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(StringUtils.ITINERARY_BOOKED_ALREADY)
                            .build();
        }
        if(itinerary.getStatus().equals("CANCELLED")) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(StringUtils.ITINERARY_CANCELLED_ALREADY)
                            .build();
        }
        
        List<FlightInfo> flights = FlightService.getFlights(date, startAirport, endAirport);
        
        FlightsRepresentation flightsRepresentation = new FlightsRepresentation();
        
        flightsRepresentation.setFlightInfo(flights);
        
        ItineraryResource.addCancelLink(userid, itineraryId, flightsRepresentation);
        ItineraryResource.addBookLink(userid, itineraryId, flightsRepresentation);
        ItineraryResource.addGetItineraryLink(userid, itineraryId, flightsRepresentation);
        ItineraryResource.addGetFlightsLink(userid, itineraryId, flightsRepresentation);
        ItineraryResource.addGetHotelsLink(userid, itineraryId, flightsRepresentation);
        ItineraryResource.addAddFlightLink(userid, itineraryId, flightsRepresentation);
        
        return Response.ok(flightsRepresentation).build();
    }
    
    /**
     * @author: Paulina Bien
     * @param userid
     * @param itineraryId
     * @param flight
     * @return 
     */
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addFlightToItinerary(@PathParam("userid") String userid,
                                    @PathParam("itineraryid") String itineraryId,
                                    FlightInfo flight)
    {
        StatusRepresentation status = new StatusRepresentation();
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
        if (itinerary.getStatus().equals(ItineraryStatus.CONFIRMED.toString()))
        {
                return Response.
                    status(Response.Status.NOT_ACCEPTABLE).
                    entity(StringUtils.ITINERARY_BOOKED_ALREADY).
                    build();
        }
        
        if (itinerary.getStatus().equals(ItineraryStatus.CANCELLED.toString()))
        {
                return Response.
                    status(Response.Status.NOT_ACCEPTABLE).
                    entity(StringUtils.ITINERARY_CANCELLED_ALREADY).
                    build();
        }
        
        itinerary.addFlightToItinerary(flight);
        status.setStatus(StringUtils.FLIGHT_ADDED);

        ItineraryResource.addCancelLink(userid, itineraryId, status);
        ItineraryResource.addBookLink(userid, itineraryId, status);
        ItineraryResource.addGetItineraryLink(userid, itineraryId, status);
        ItineraryResource.addGetFlightsLink(userid, itineraryId, status);
        ItineraryResource.addGetHotelsLink(userid, itineraryId, status);
        
        return Response.ok(status).build();
    }
   
}
