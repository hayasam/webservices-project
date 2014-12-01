/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.data;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VAIO
 */
@XmlRootElement()
public class FlightInfos {
    
    private List<FlightInfo> flightInfo;
    
    public FlightInfos() {
        
    }
    
    public FlightInfos(List<FlightInfo> flightInfo) {
        this.flightInfo = flightInfo;
    }
    
    public List<FlightInfo> getFlightInfo() {
        return flightInfo;
    }
    
    public void setFlightInfo(List<FlightInfo> flightInfo) {
        this.flightInfo = flightInfo;
    }
}
