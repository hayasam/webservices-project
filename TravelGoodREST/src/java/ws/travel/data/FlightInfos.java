/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.data;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VAIO
 */
@XmlRootElement()
public class FlightInfos {
    
    private List<FlightInfo> flightInfos;
    
    public FlightInfos() {
        
    }
    
    public FlightInfos(List<FlightInfo> flightInfos) {
        this.flightInfos = flightInfos;
    }
    
    public List<FlightInfo> getFlightInfos() {
        return flightInfos;
    }
    
    public void setFlightInfos(List<FlightInfo> flightInfos) {
        this.flightInfos = flightInfos;
    }
}
