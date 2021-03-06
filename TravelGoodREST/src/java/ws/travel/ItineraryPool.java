/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel;

import java.util.HashMap;
import java.util.Map;
import ws.travel.data.Itinerary;

/**
 *
 * @author Oguz Demir
 */
public class ItineraryPool {
    
    private static Map<String, Itinerary> itineraries = new HashMap<String, Itinerary>();
    
    public static Itinerary getItinerary(String userid, String itineraryid) {
        return getItinerary(encrypt(userid, itineraryid));
    }
    
    private static Itinerary getItinerary(String itineraryKey) {
        return itineraries.get(itineraryKey);
    }
    
    public static void addItinerary(String userid, String itineraryid, Itinerary itinerary) {
        addItinerary(encrypt(userid, itineraryid), itinerary);
    }
    
    private static void addItinerary(String itineraryKey, Itinerary itinerary) {
        itineraries.put(itineraryKey, itinerary);
    }
 
    public static void deleteItinerary(String userid, String itineraryid) {
        deleteItinerary(encrypt(userid, itineraryid));
    }
    
    private static void deleteItinerary(String itineraryKey) {
        itineraries.remove(itineraryKey);
    }
    
    private static String encrypt(String userid, String itineraryid) {
        return userid + "-" + itineraryid;
    }
    
    public static int size() {
        if(itineraries.isEmpty())
            return 0;
        else return itineraries.size();
    }
}
