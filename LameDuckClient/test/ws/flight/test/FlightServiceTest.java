package ws.flight.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoArray;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.GetFlightInputType;

/**
 *
 * @author Monica
 */
public class FlightServiceTest {
        
    public FlightServiceTest() {
    }
      
     @Test
     public void getFlightsTest() {
         GetFlightInputType getFlightsInput = new GetFlightInputType();
         
         getFlightsInput.setDate(TestUtils.createDate("18-12-2014 18:00"));
         getFlightsInput.setStartAirport("Copehagen Lufthavn");
         getFlightsInput.setEndAirport("Bucharest Otopeni");
         
         FlightInfoArray result = getFlightsOperation(getFlightsInput);
         TestUtils.createFlightInfosTest1();
         
         List<FlightInfoType> expected = TestUtils.flightInfos;
         
         int index = 0;
         for (FlightInfoType info : result.getFlightInfo())
                assertEquals(expected.get(index++), info);
     }

    private static FlightInfoArray getFlightsOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.GetFlightInputType getFlightsInput) {
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService service = new org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService();
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.getFlightsOperation(getFlightsInput);
    }
}