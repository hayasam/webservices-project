/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.xml.schema.itinerarydata.FlightInfoArray;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.*;

/**
 *
 * @author VAIO
 */
public class TravelServiceTest {
    
    @Test
    public void testGetFlights() {
        
    }
    
    private static FlightInfoArray getFlightsOperation(GetFlightsInputType getFlightInput) {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.getFlightsOperation(getFlightInput);
    }
}