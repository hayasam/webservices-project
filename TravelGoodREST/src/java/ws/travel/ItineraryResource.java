/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightFault;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelFault;
import ws.bank.creditcard.CreditCard;
import ws.travel.data.FlightInfo;
import ws.travel.data.HotelInfo;
import ws.travel.data.Itinerary;
import ws.travel.services.FlightService;
import ws.travel.services.HotelService;

/**
 *
 * @author VAIO
 */
@Path("users/{userid}/itinerary/{itineraryid}")
public class ItineraryResource {
    
     public static enum Status {
       CONFIRMED ,
       UNCONFIRMED,
       CANCELLED
    }
    
    /**
     * @GET
     * Implement get current itinerary.
     * [Johannes]
     */
    
    /**
     * @GET
     * @Path("flights")
     * [Audrius]
     * Implement get possible flights with query string.
     */
    
    /**
     * @GET
     * [Audrius]
     * @Path("hotels")
     * Implement get possible hotels with query string.
     */
    
    /**
     * @POST
     * [Oguz]
     * @Path("book")
     * Implement booking itinerary.
     */
    
    /**
     * [Monica]
     * @Path("cancel")
     * Implement cancel itinerary.
     */
     @POST
     @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
     @Produces(MediaType.APPLICATION_XML)
     public Response cancelItinerary (@PathParam("userid") String userId,
     @PathParam("itineraryid") String itineraryId, CreditCard creditCard) {
         
         Itinerary itinerary = ItineraryPool.getItinerary(itineraryId);
         
         if(itinerary.getStatus().equals(Status.CONFIRMED.toString())) {
              for(FlightInfo flightInfo : itinerary.getFlightInfos())
                  if(flightInfo.getStatus().equals(Status.CONFIRMED.toString())) {
                  try {
                      FlightService.cancelFlight(flightInfo.getBookingNr(), flightInfo.getPrice(), creditCard);
                      flightInfo.setStatus(Status.CANCELLED.toString());
                  } catch (CancelFlightFault ex) {
                      Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
                  }     
                 }
              for(HotelInfo hotelInfo : itinerary.getHotelInfos())
                  if(hotelInfo.getStatus().equals(Status.CONFIRMED.toString())) {
                     
                  try { 
                      HotelService.cancelHotel(hotelInfo.getBookingNr());
                      hotelInfo.setStatus(Status.CANCELLED.toString());
                  } catch (CancelHotelFault ex) {
                      Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
                  }  
                }
              itinerary.setStatus(Status.CANCELLED.toString());
              return Response.ok(itinerary).build();
         }
         else return Response.
                        status(Response.Status.BAD_REQUEST).
                        entity("Can't cancel an unbooked itinerary").
                        build();
     }
    /**
     * @PUT
     * [Caecilie]
     * Implement create itinerary.
     */
}
