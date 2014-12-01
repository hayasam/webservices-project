/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

/**
 *
 * @author Monica Coman
 */
public class StringUtils {
    
    public static final String ITINERARY_CREATED = "itinerary successfully created";
    public static final String ITINERARY_NOT_FOUND = "itinerary not found";
    public static final String ITINERARY_TERMINATED = "itinerary terminated";
    public static final String ITINERARY_BOOKED_ALREADY = "itinerary booked already";
    public static final String ITINERARY_CANCELLED_ALREADY = "itinerary cancelled already"; 
    public static final String ITINERARY_NOT_FULLY_CANCELLED = "Not all bookings were canceled";
    public static final String ITINERARY_NOT_FULLY_BOOKED = "Not all bookings were booked";
    public static final String ITINERARY_SUCCESSFULLY_BOOKED = "itinerary successfully booked";
    public static final String ITINERARY_SUCCESSFULLY_CANCELLED = "itinerary successfully cancelled";
    
    public static final String FLIGHT_ADDED = "flight added to itinerary";
    public static final String HOTEL_ADDED = "hotel added to itinerary";
    
    public static final String BASE_URI = "http://localhost:8080/TravelGoodREST/webresources";
    
    public static final String RELATION_BASE = "http://travelgood.ws/relations/";
    public static final String CANCEL_RELATION = RELATION_BASE + "cancel";
    public static final String STATUS_RELATION = RELATION_BASE + "status";
    public static final String BOOK_RELATION = RELATION_BASE + "book";
    public static final String GET_FLIGHTS_RELATION = RELATION_BASE + "getFlights";
    public static final String GET_HOTELS_RELATION = RELATION_BASE + "getHotels";
    public static final String ADD_FLIGHT_RELATION = RELATION_BASE + "addFlight";
    public static final String ADD_HOTEL_RELATION = RELATION_BASE + "addHotel";
    public static final String CREATE_ITINERARY = RELATION_BASE + "createItinerary";
    
}
