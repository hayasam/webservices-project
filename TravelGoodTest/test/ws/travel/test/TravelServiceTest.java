/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.test;

import org.junit.Test;
import static org.junit.Assert.*;
import ws.flight.*;


/**
 *
 * @author VAIO
 */
public class TravelServiceTest {
    
    /**
     * Tests that createItinerary operation creates two different itineraryIds for 
     * different customers.
     */
    @Test
    public void testCreateItinerary() {
        String itineraryId1 = createItineraryOperation("123");
        assertNotNull(itineraryId1);
        String itineraryId2 = createItineraryOperation("234");
        assertFalse(itineraryId1.equals(itineraryId2));
    }

    @Test
    public void testGetFlights() {
        String itineraryId = createItineraryOperation("123");
        FlightInfoArray actualFlightInfos = getFlightsOperation(createGetFlightsInput(itineraryId));
        
        assertEquals(1, actualFlightInfos.getFlightInfo().size());
        assertEquals(111, actualFlightInfos.getFlightInfo().get(0).getBookingNr());
    }
    
    private GetFlightsInputType createGetFlightsInput(String itineraryId) {
        GetFlightsInputType input = new GetFlightsInputType();
        input.setItineraryId(itineraryId);
        input.setGetFlightInput(createGetFlightInput());
        return input;
    }
    
    private GetFlightInputType createGetFlightInput() {
        GetFlightInputType getFlightsInput = new GetFlightInputType();
        getFlightsInput.setDate(TestUtils.createDate("07-11-2014 08:50"));
        getFlightsInput.setStartAirport("Copenhagen Lufthavnen");
        getFlightsInput.setEndAirport("Bucharest Otopeni");
        return getFlightsInput;
    }
    
    private static FlightInfoArray getFlightsOperation(GetFlightsInputType getFlightInput) {
        TravelService service = new TravelService();
        TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.getFlightsOperation(getFlightInput);
    }

    private static String createItineraryOperation(java.lang.String createItineraryInput) {
        TravelService service = new TravelService();
        TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.createItineraryOperation(createItineraryInput);
    }
    
}