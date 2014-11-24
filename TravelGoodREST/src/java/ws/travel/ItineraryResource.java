/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
    
    private static final String ITINERARY_NOT_FOUND = "itinerary not found";
    
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
           }
       }
       return Response.ok("OK").build();
   }
    
    /**
     * @POST
     * [Monica]
     * @Path("cancel")
     * Implement cancel itinerary.
     */
    
    /**
     * @PUT
     * [Caecilie]
     * Implement create itinerary.
     */
}
