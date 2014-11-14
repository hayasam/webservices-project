package ws.flight.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import ws.flight.*;

/**
 *
 * @author Monica
 */
public class FlightServiceTest {
     
    @Test
    public void testGetFlights() {
        GetFlightInputType getFlightsInput = new GetFlightInputType();
         
        getFlightsInput.setDate(TestUtils.createDate("07-11-2014 08:50"));
        getFlightsInput.setStartAirport("Copenhagen Lufthavnen");
        getFlightsInput.setEndAirport("Bucharest Otopeni");
        
        FlightInfoArray actualFlightInfos = getFlightsOperation(getFlightsInput);
    
        assertEquals(1, actualFlightInfos.getFlightInfo().size());
        assertEquals(111, actualFlightInfos.getFlightInfo().get(0).getBookingNr());
    }
    
    @Test
    public void testBookFlightsValid() throws BookFlightFault {
        
        BookFlightInputType bookFlightInput = new BookFlightInputType();
        
        bookFlightInput.setBookingNr(111);
        bookFlightInput.setCreditCardInfo(TestUtils.validCCInfo());
        
        boolean result = bookFlightOperation(bookFlightInput);
        
        assertTrue(result);
    }
    
    @Test
    public void testBookFlightsInvalid() {
        BookFlightInputType bookFlightInput = new BookFlightInputType();
        
        bookFlightInput.setBookingNr(111);
        bookFlightInput.setCreditCardInfo(TestUtils.invalidCCInfo());
    
        try {
            bookFlightOperation(bookFlightInput);
            fail("BookFlightOperation should have thrown an exception!");
        } catch (BookFlightFault ex) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testCancelFlight() throws CancelFlightFault {
        CancelFlightInputType cancelFlightInput = new CancelFlightInputType();
        
        cancelFlightInput.setBookingNr(111);
        cancelFlightInput.setCreditCardInfo(TestUtils.validCCInfo());
        cancelFlightInput.setPrice(100.0);
        
        boolean result = cancelFlightOperation(cancelFlightInput);
        
        assertTrue(result);
    }

    
    // This test is wrong.
    // Reason 1: result returns empty array.
    // Reason 2: assertEquals for object comparison should fail since two objects are seperately
    //           created objects and since equals method is not overriden on FlightInfoType class, 
    //           assertEquals(flightInfo1, flightInfo2) should fail.
     /* @Test
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
     } */

    private static boolean bookFlightOperation(ws.flight.BookFlightInputType bookFlightInput) throws BookFlightFault {
        ws.flight.FlightService service = new ws.flight.FlightService();
        ws.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.bookFlightOperation(bookFlightInput);
    }

    private static boolean cancelFlightOperation(ws.flight.CancelFlightInputType cancelFlightInput) throws CancelFlightFault {
        ws.flight.FlightService service = new ws.flight.FlightService();
        ws.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.cancelFlightOperation(cancelFlightInput);
    }

    private static FlightInfoArray getFlightsOperation(ws.flight.GetFlightInputType getFlightsInput) {
        ws.flight.FlightService service = new ws.flight.FlightService();
        ws.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.getFlightsOperation(getFlightsInput);
    }
}