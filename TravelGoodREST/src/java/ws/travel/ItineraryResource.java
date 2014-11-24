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
import javax.ws.rs.GET;
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
import ws.travel.data.CreditCard;
import ws.travel.data.FlightInfo;
import ws.travel.data.HotelInfo;
import ws.travel.data.Itinerary;
import ws.travel.representation.ItineraryRepresentation;
import ws.travel.representation.Link;
import ws.travel.representation.Representation;
import ws.travel.representation.StatusRepresentation;
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

    private static final String ITINERARY_CREATED = "itinerary successfully created";
    private static final String ITINERARY_NOT_FOUND = "itinerary not found";
    private static final String ITINERARY_BOOKED_ALREADY = "itinerary booked already";
    private static final String ITINERARY_CANCELLED_ALREADY = "itinerary cancelled already"; 
    private static final String ITINERARY_TERMINATED = "itinerary terminated";
    private static final String ITINERARY_NOT_FULLY_CANCELLED = "Not all bookings were canceled";
    private static final String ITINERARY_NOT_FULLY_BOOKED = "Not all bookings were booked";
    private static final String ITINERARY_SUCCESSFULLY_BOOKED = "itinerary successfully booked";
    private static final String BASE_URI = "http://localhost:8080/TravelGoodREST/webresources";
    
    private static final String RELATION_BASE = "http://travelgood.ws/relations/";
    private static final String CANCEL_RELATION = RELATION_BASE + "cancel";
    private static final String STATUS_RELATION = RELATION_BASE + "status";
    private static final String BOOK_RELATION = RELATION_BASE + "book";
    private static final String GET_FLGHTS_RELATION = RELATION_BASE + "getFlights";
    private static final String GET_HOTELS_RELATION = RELATION_BASE + "getHotels";
    private static final String ADD_FLIGHT_RELATION = RELATION_BASE + "addFlight";
    private static final String ADD_HOTEL_RELATION = RELATION_BASE + "addHotel";
    private static final String CREATE_ITINERARY = RELATION_BASE + "createItinerary";
    
    /**
     * Implement get current itinerary.
     * [Johannes]
     */
     @GET
     @Produces(MediaType.APPLICATION_XML)
     public Response getItinerary (@PathParam("userid") String userId,
                        @PathParam("itineraryid") String itineraryId) {
         Itinerary itinerary = ItineraryPool.getItinerary(userId, itineraryId);
         ItineraryRepresentation itineraryRep = new ItineraryRepresentation();
         
         if(itinerary == null) {
            return Response.status(Status.NOT_FOUND)
                           .entity(ITINERARY_NOT_FOUND)
                           .build();
        }
         
         itineraryRep.setItinerary(itinerary);
         
         addBookLink(userId, itineraryId, itineraryRep);
         addCancelLink(userId, itineraryId, itineraryRep);
         addGetFlightsLink(userId, itineraryId, itineraryRep);
         addGetHotelsLink(userId, itineraryId, itineraryRep);
         
         return Response.ok(itineraryRep).build();
     }
    
    
    
    
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
        StatusRepresentation statusRep = new StatusRepresentation();
        
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
                
                statusRep.setStatus(ITINERARY_NOT_FULLY_BOOKED);
                addGetItineraryLink(userid, itineraryid, statusRep);
                addGetFlightsLink(userid, itineraryid, statusRep);
                addGetHotelsLink(userid, itineraryid, statusRep);
                addCancelLink(userid, itineraryid, statusRep);
                
                return Response.ok(statusRep).build();
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
                statusRep.setStatus(ITINERARY_NOT_FULLY_BOOKED);
                addGetItineraryLink(userid, itineraryid, statusRep);
                addGetFlightsLink(userid, itineraryid, statusRep);
                addGetHotelsLink(userid, itineraryid, statusRep);
                addCancelLink(userid, itineraryid, statusRep);
                
                return Response.ok(statusRep).build();
           }
       }
       
       // all went pretty well
       itinerary.setStatus(ItineraryStatus.CONFIRMED.toString());
       
       statusRep.setStatus(ITINERARY_SUCCESSFULLY_BOOKED);
       addCancelLink(userid, itineraryid, statusRep);
       addGetItineraryLink(userid, itineraryid, statusRep);
       
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
         /*
          * Get requested itinerary
          */
        Itinerary itinerary = ItineraryPool.getItinerary(userId, itineraryId);
        
        if(itinerary == null) { // itinerary not found
            return Response.status(Status.NOT_FOUND)
                           .entity(ITINERARY_NOT_FOUND)
                           .build();
        }
        
        boolean success = true; // assume the cancellation will work
        
        if(itinerary.getStatus().equals(ItineraryStatus.CONFIRMED.toString())) { // itinerary previously booked
            /*
             * Cancel all flights
             */
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
             /*
              * Cancel all hotels
              */
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
             if(success) { // all cancelations succeded
                 /*
                  * Set itinerary status to cancelled
                  */
                 itinerary.setStatus(ItineraryStatus.CANCELLED.toString());
                 
                 ItineraryRepresentation itineraryRep = new ItineraryRepresentation();
                 itineraryRep.setItinerary(itinerary);
                 addGetItineraryLink(userId, itineraryId, itineraryRep);
                 
                 return Response.ok(itineraryRep).build();
             }
             else // some cancelations did not succeed 
             {
                 ItineraryRepresentation itineraryRep = new ItineraryRepresentation();
                 itineraryRep.setItinerary(itinerary);
                 addGetItineraryLink(userId, itineraryId, itineraryRep);
                 
                 return Response.ok(itineraryRep, ITINERARY_NOT_FULLY_CANCELLED).build();
             }
        }
        else { // itinerary in planning phase
            /*
             * Remove itinerary from the pool
             */
            ItineraryPool.deleteItinerary(userId, itineraryId);
            StatusRepresentation statusRep = new StatusRepresentation();
            statusRep.setStatus(ITINERARY_TERMINATED);
            
            addCreateItinerary(userId, itineraryId, statusRep);
            
            return Response.ok(statusRep).build();
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
        
        StatusRepresentation statusRep = new StatusRepresentation();
        statusRep.setStatus(ITINERARY_CREATED);
        
        addGetFlightsLink(userid, itineraryid, statusRep);
        addGetHotelsLink(userid, itineraryid, statusRep);
        addGetItineraryLink(userid, itineraryid, statusRep);
        
        return Response.ok(statusRep).build();
    } 
    
        //add cancel link
    static void addCancelLink(String userId, String itineraryId, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s/cancel", BASE_URI, userId, itineraryId));
        link.setRel(CANCEL_RELATION);
        response.getLinks().add(link);
    }
    
    //add book link
    static void addBookLink(String userId, String itineraryId, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s/book", BASE_URI, userId, itineraryId));
        link.setRel(BOOK_RELATION);
        response.getLinks().add(link);
    }
   
    //add get itinerary link
    static void addGetItineraryLink(String userId, String itineraryId, Representation response) {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s", BASE_URI, userId, itineraryId));
        link.setRel(STATUS_RELATION);
        response.getLinks().add(link);
    }
    
    //get flights
    static void addGetFlightsLink(String userId, String itineraryId, Representation response)
    {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s/flights", BASE_URI, userId, itineraryId));
        link.setRel(GET_FLGHTS_RELATION);
        response.getLinks().add(link);
    }
    
    //get hotels
    static void addGetHotelsLink(String userId, String itineraryId, Representation response)
    {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s/hotels", BASE_URI, userId, itineraryId));
        link.setRel(GET_HOTELS_RELATION);
        response.getLinks().add(link);
    }
    
    //add hotel to itinerary
    static void addAddHotelLink(String userId, String itineraryId, Representation response)
    {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s/hotels/add", BASE_URI, userId, itineraryId));
        link.setRel(ADD_HOTEL_RELATION);
        response.getLinks().add(link);
    }
    
    //add flight to itinerary
    static void addAddFlightLink(String userId, String itineraryId, Representation response)
    {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s/flights/add", BASE_URI, userId, itineraryId));
        link.setRel(ADD_FLIGHT_RELATION);
        response.getLinks().add(link);
    }
    
    static void addCreateItinerary(String userId, String itineraryId, Representation response)
    {
        Link link = new Link();
        link.setUri(String.format("%s/users/%s/itinerary/%s", BASE_URI, userId, itineraryId));
        link.setRel(CREATE_ITINERARY);
        response.getLinks().add(link);
    }
}
