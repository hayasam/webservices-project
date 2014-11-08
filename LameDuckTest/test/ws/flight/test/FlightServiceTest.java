package ws.flight.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import ws.flight.*;

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

    private static FlightInfoArray getFlightsOperation(ws.flight.GetFlightInputType getFlightsInput) {
        ws.flight.FlightService service = new ws.flight.FlightService();
        ws.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.getFlightsOperation(getFlightsInput);
    }

}