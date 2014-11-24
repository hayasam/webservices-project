/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.representation;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travel.rest.data.FlightInfo;

/**
 *
 * @author Paulina
 */
@XmlRootElement()
public class FlightsRepresentation extends Representation {
    
    private List<FlightInfo> flightInfo;

    public FlightsRepresentation() {
    }

    /**
     * @return the flightInfo
     */
    public List<FlightInfo> getFlightInfo() {
        return flightInfo;
    }

    /**
     * @param flightInfo the flightInfo to set
     */
    public void setFlightInfo(List<FlightInfo> flightInfo) {
        this.flightInfo = flightInfo;
    }
    
}
