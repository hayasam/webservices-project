/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.representation;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travel.rest.data.Itinerary;

/**
 *
 * @author Paulina
 */

@XmlRootElement()
public class ItineraryRepresentation extends Representation {
    
    private Itinerary itinerary;

    public ItineraryRepresentation() {
    }

    /**
     * @return the itinerary
     */
    public Itinerary getItinerary() {
        return itinerary;
    }

    /**
     * @param itinerary the itinerary to set
     */
    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
    
}
