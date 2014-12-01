/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.builder;

import java.util.List;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoArray;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoType;

/**
 *
 * @author VAIO
 */
public class FlightInfoArrayBuilder {
    
    private FlightInfoArray flightInfoArray;
    
    protected FlightInfoArrayBuilder() {
        flightInfoArray = new FlightInfoArray();
    }
    
    public FlightInfoArrayBuilder withFlightInfos(List<FlightInfoType> flightInfos) {
        flightInfoArray.getFlightInfo().clear();
        flightInfoArray.getFlightInfo().addAll(flightInfos);
        return this;
    }
    
    public FlightInfoArray create() {
        return flightInfoArray;
    }
}
