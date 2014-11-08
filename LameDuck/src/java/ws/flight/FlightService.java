/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight;


import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightFault;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightFault;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoArray;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoType;

/**
 *
 * @author Audrius
 */
@WebService(serviceName = "flightService", portName = "flightPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", wsdlLocation = "WEB-INF/wsdl/FlightService/flight.wsdl")
public class FlightService {
    
    public org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoArray getFlightsOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.GetFlightInputType getFlightsInput) {
        XMLGregorianCalendar date = getFlightsInput.getDate();
        String startAirport = getFlightsInput.getStartAirport();
        String destinationAirport = getFlightsInput.getEndAirport();
       
        //createFlightInfos();
        
        FlightInfoArray array = new FlightInfoArray();
                
        for(FlightInfoType flightInfo : FlightUtils.flightInfos)
            if(flightInfo.getFlight().getDateDeparture().equals(date) &&
                    flightInfo.getFlight().getStartAirport().equals(startAirport) &&
                    flightInfo.getFlight().getDestinationAirport().equals(destinationAirport))
                array.getFlightInfo().add(flightInfo);
                
        return array;
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
