/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight;

import javax.jws.WebService;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightFault;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightFault;

/**
 *
 * @author Audrius
 */
@WebService(serviceName = "flightService", portName = "flightPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", wsdlLocation = "WEB-INF/wsdl/FlightService/flight.wsdl")
public class FlightService {

    public org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoArray getFlightsOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.GetFlightInputType getFlightsInput) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean bookFlightOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightInputType bookFlightInput) throws BookFlightFault {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean cancelFlightOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightInputType cancelFlightInput) throws CancelFlightFault {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
