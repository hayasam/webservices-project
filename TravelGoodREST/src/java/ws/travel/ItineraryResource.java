/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightFault;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightFault;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookOperationFault;
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
    

     public static enum ItineraryStatus {
       CONFIRMED ,
       UNCONFIRMED,
       CANCELLED
    }

    private static final String ITINERARY_NOT_FOUND = "itinerary not found";
    private static final String ITINERARY_BOOKED_ALREADY = "itinerary booked already";
    private static final String ITINERARY_CANCELLED_ALREADY = "itinerary cancelled already"; 

    private static final String CANNOT_CANCEL_ITINERAY = "Can't cancel an unbooked itinerary";
    private static final String ITINERARY_TERMINATED = "itinerary terminated";
    private static final String ITINERARY_NOT_FULLY_CANCELLED = "Not all bookings were canceled";
    
    /**
     * @GET
     * Implement get current itinerary.
     * [Johannes]
     */
    
    
    
    
    /**
     * @POST
     * [Oguz]
     * @Path("book")
     * Implement booking itinerary.
     */
     @POST
     @Path("book")
     @Consumes(MediaType.APPLICATION_XML)
     @Produces(MediaType.APPLICATION_XML)
     public Response bookItinerary(@PathParam("userid") String userid,
                                   @PathParam("itineraryid") String itineraryid,
                                   CreditCard ccInfo) {
        Itinerary itinerary = ItineraryPool.getItinerary(userid, itineraryid);
        if(itinerary == null) {
            return Response.status(Status.NOT_FOUND)
                           .entity(ITINERARY_NOT_FOUND)
                           .build();
        }
        if(itinerary.getStatus().equals(ItineraryStatus.CONFIRMED.toString())) {
            return Response.status(Status.NOT_ACCEPTABLE)
                           .entity(ITINERARY_BOOKED_ALREADY)
                           .build();
        }
        if(itinerary.getStatus().equals(ItineraryStatus.CANCELLED.toString())) {
            return Response.status(Status.NOT_ACCEPTABLE)
                           .entity(ITINERARY_CANCELLED_ALREADY)
                           .build();
        }
        
        List<FlightInfo> confirmedFlights = new ArrayList<FlightInfo>();
        for(FlightInfo flight : itinerary.getFlightInfos()) {
            try {
                boolean booked = FlightService.bookFlight(flight.getBookingNr(), ccInfo);
                if(booked) {
                    flight.setStatus("CONFIRMED");
                    confirmedFlights.add(flight);
                }
            } catch (BookFlightFault ex) { // rollback
                for(FlightInfo confirmedFlight : confirmedFlights) {
                    try {
                        boolean cancelled = FlightService.cancelFlight(confirmedFlight.getBookingNr(), confirmedFlight.getPrice() * 2, ccInfo);
                        if(cancelled) {
                            confirmedFlight.setStatus("CANCELLED");
                        }
                    } catch (CancelFlightFault ex1) {

                    }
                }
                return Response.ok(itinerary).build();
            }
       }

       List<HotelInfo> confirmedHotels = new ArrayList<HotelInfo>(); 
       for(HotelInfo hotel : itinerary.getHotelInfos()) {
           try {
               boolean booked = HotelService.bookHotel(hotel.getBookingNr(), ccInfo);
               if(booked) {
                   hotel.setStatus("CONFIRMED");
                   confirmedHotels.add(hotel);
               }
           } catch (BookOperationFault ex) { // rollback
                for(FlightInfo confirmedFlight : confirmedFlights) {
                    try {
                        boolean cancelled = FlightService.cancelFlight(confirmedFlight.getBookingNr(), confirmedFlight.getPrice() * 2, ccInfo);
                        if(cancelled) {
                            confirmedFlight.setStatus("CANCELLED");
                        }
                    } catch (CancelFlightFault ex1) {

                    }
                }
                for(HotelInfo confirmedHotel : confirmedHotels) {
                    try {
                        boolean cancelled = HotelService.cancelHotel(confirmedHotel.getBookingNr());
                        if(cancelled) {
                            confirmedHotel.setStatus("CANCELLED");
                        }
                    } catch (CancelHotelFault ex1) {
                        
                    }
                }
                return Response.ok(itinerary).build();
           }
       }
       
       // all went pretty well
       itinerary.setStatus(ItineraryStatus.CONFIRMED.toString());
       
       return Response.ok(itinerary).build();
   }
    
    /**
     * [Monica]
     * Implement cancel itinerary.
     */
     @Path("cancel")
     @POST
     @Consumes(MediaType.APPLICATION_XML)
     @Produces(MediaType.APPLICATION_XML)
     public Response cancelItinerary (@PathParam("userid") String userId,
     @PathParam("itineraryid") String itineraryId, CreditCard creditCard) {
         
        Itinerary itinerary = ItineraryPool.getItinerary(userId, itineraryId);
        if(itinerary == null) {
            return Response.status(Status.NOT_FOUND)
                           .entity(ITINERARY_NOT_FOUND)
                           .build();
        }
        boolean success = true;
        if(itinerary.getStatus().equals(ItineraryStatus.CONFIRMED.toString())) {
             for(FlightInfo flightInfo : itinerary.getFlightInfos())
                 if(flightInfo.getStatus().equals(ItineraryStatus.CONFIRMED.toString())) {
                 try {
                     FlightService.cancelFlight(flightInfo.getBookingNr(), flightInfo.getPrice(), creditCard);
                     flightInfo.setStatus(ItineraryStatus.CANCELLED.toString());
                 } catch (CancelFlightFault ex) {
                     success = false;
                     Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
                 }     
                }
             for(HotelInfo hotelInfo : itinerary.getHotelInfos())
                 if(hotelInfo.getStatus().equals(ItineraryStatus.CONFIRMED.toString())) {

                 try { 
                     HotelService.cancelHotel(hotelInfo.getBookingNr());
                     hotelInfo.setStatus(ItineraryStatus.CANCELLED.toString());
                 } catch (CancelHotelFault ex) {
                     success = false;
                     Logger.getLogger(ItineraryResource.class.getName()).log(Level.SEVERE, null, ex);
                 }  
               }
             if(success) {
                 itinerary.setStatus(ItineraryStatus.CANCELLED.toString());
                 return Response.ok(itinerary).build();
             }
             else return Response.ok(itinerary, ITINERARY_NOT_FULLY_CANCELLED).build();
                       
        }
        else {
            ItineraryPool.deleteItinerary(userId, itineraryId);
            return Response.ok(ITINERARY_TERMINATED).build();
        }
     }
    /**
     * @PUT
     * [Caecilie]
     * Implement create itinerary.
     */
    @PUT
    @Produces(MediaType.APPLICATION_XML)
    public Response createItinerary(@PathParam("userid") String userid,
                                   @PathParam("itineraryid") String itineraryid) {
        
        Itinerary itinerary = new Itinerary();
        itinerary.setStatus(ItineraryStatus.UNCONFIRMED.toString());
        
        ItineraryPool.addItinerary(userid, itineraryid, itinerary);
        
        return Response.ok("OK").build();
    } 
}
