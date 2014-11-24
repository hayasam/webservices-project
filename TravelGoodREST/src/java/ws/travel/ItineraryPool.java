/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import java.util.HashMap;
import java.util.Map;
import ws.travel.data.HotelInfo;
import ws.travel.data.Itinerary;

/**
 *
 * @author VAIO
 */
public class ItineraryPool {
    
    private static Map<String, Itinerary> itineraries = new HashMap<String, Itinerary>();
    
    public static Itinerary getItinerary(String itineraryKey) {
        return itineraries.get(itineraryKey);
    }
    
    public static void addItinerary(String itineraryKey, Itinerary itinerary) {
        itineraries.put(itineraryKey, itinerary);
    }
    
    public static void deleteItinerary(String itineraryKey) {
        itineraries.remove(itineraryKey);
    }
}
