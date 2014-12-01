/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.bpel.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.AddFlightToItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.BookItineraryOperationFault;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetFlightsInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService;
import org.netbeans.xml.schema.itinerarydata.FlightInfoArray;
import org.netbeans.xml.schema.itinerarydata.FlightInfoType;
import org.netbeans.xml.schema.itinerarydata.HotelInfoType;
import org.netbeans.xml.schema.itinerarydata.HotelsInfoArray;
import org.netbeans.xml.schema.itinerarydata.ItineraryInfoType;



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

    /**
     * Tests that all flights are correctly retrieved using
     * LameDuck's getFlightsOperation
     */
    @Test
    public void testGetFlights() {
        String itineraryId = createItineraryOperation("123");
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        
        assertEquals(2, actualFlightInfos.getFlightInfo().size());
        assertEquals("UNCONFIRMED", actualFlightInfos.getFlightInfo().get(0).getStatus());
        assertEquals("UNCONFIRMED", actualFlightInfos.getFlightInfo().get(1).getStatus());
    }
    
    @Test 
    public void testGetHotels () {
        String itineraryId = createItineraryOperation("123");
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        
        assertEquals(2, actualHotelInfos.getHotelInfo().size());
        assertEquals("UNCONFIRMED", actualHotelInfos.getHotelInfo().get(0).getStatus());
        assertEquals("UNCONFIRMED", actualHotelInfos.getHotelInfo().get(1).getStatus());
    }
    
    @Test
    public void testFlightAddToItinerary() {
        // create an itinerary
        String itineraryId = createItineraryOperation("123");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        ItineraryInfoType itineraryInfo = getItineraryOperation(itineraryId);
        
        // assert that it is added to itinerary
        assertEquals(2, itineraryInfo.getFlightInfoArray().getFlightInfo().size());
        for(FlightInfoType flightInfo : itineraryInfo.getFlightInfoArray().getFlightInfo()) {
            assertEquals("UNCONFIRMED", flightInfo.getStatus());
        }
    }
    
    
    @Test
    public void testHotelAddToItinerary() {
        // create an itinerary
        String itineraryId = createItineraryOperation("444");
        
        // search for a hotel
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        HotelInfoType hotelInfo2 = actualHotelInfos.getHotelInfo().get(1);
        
        // add hotel to itinerary
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        ItineraryInfoType itineraryInfo = getItineraryOperation(itineraryId);
        
        // assert that it is added to itinerary
        assertEquals(2, itineraryInfo.getHotelsInfoArray().getHotelInfo().size());
        
        for(HotelInfoType hotelInfo : itineraryInfo.getHotelsInfoArray().getHotelInfo()) {
            assertEquals("UNCONFIRMED", hotelInfo.getStatus());
        }
    }
    
    @Test
    public void testBookItineraryWithFlights() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("444");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
 
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
    
        
        // assert that booking is successful
        boolean res = bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
        
        // get itinerary and check status
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        
        String status1 = itinerary.getFlightInfoArray().getFlightInfo().get(0).getStatus();
        String status2 = itinerary.getFlightInfoArray().getFlightInfo().get(1).getStatus();
        
        assertEquals("CONFIRMED", status1);
        assertEquals("CONFIRMED", status2);
    }
    
    @Test
    public void testBookItineraryWithHotels() throws BookItineraryOperationFault {
        String itineraryId = createItineraryOperation("555");

        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
       
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        HotelInfoType hotelInfo2 = actualHotelInfos.getHotelInfo().get(1);

        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        boolean result = bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        assertTrue(result);
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        
        String status1 = itinerary.getHotelsInfoArray().getHotelInfo().get(0).getStatus();
        String status2 = itinerary.getHotelsInfoArray().getHotelInfo().get(1).getStatus();
        
        assertEquals("CONFIRMED", status1);
        assertEquals("CONFIRMED", status2);
    }
    
    @Test
    public void testBookItineraryMixed() throws BookItineraryOperationFault
    {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        boolean result = bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
       
        assertTrue(result);
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        
        assertEquals(2, itinerary.getFlightInfoArray().getFlightInfo().size() + 
                        itinerary.getHotelsInfoArray().getHotelInfo().size());
        
        for(FlightInfoType flightInfo : itinerary.getFlightInfoArray().getFlightInfo())
            assertEquals("CONFIRMED", flightInfo.getStatus());
        for(HotelInfoType hotelInfo : itinerary.getHotelsInfoArray().getHotelInfo()) {
            assertEquals("CONFIRMED", hotelInfo.getStatus());
        }
    }
    
    /**
     * First flight should be compensated in this test when second flight booking throws the exception.  
     */
    @Test
    public void testBookItineraryWithInvalidCC() {
        // create an itinerary
        String itineraryId = createItineraryOperation("555");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = getFlightsOperation( TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // book with invalid credit card
        try {
            bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.invalidCCInfo()));
   
            fail("Book itinerary should have failed!");
        } catch(BookItineraryOperationFault ex) {
            String faultInfo = ex.getFaultInfo();
            assertEquals("Booking itinerary failed!", faultInfo);
        }
        //TODO: Verify that process is terminated. 
    }
    
    @Test
    public void testCancelMixedItineraryBeforeBooking() throws BookItineraryOperationFault
    {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        boolean result = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
       
        assertTrue(result);
        
        //TODO: Verify that process is terminated.
    }
     
     @Test
    public void testCancelMixedItineraryAfterBooking() throws BookItineraryOperationFault
    {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = getFlightsOperation(
                TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        HotelInfoType hotelInfo2 = actualHotelInfos.getHotelInfo().get(1);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));

        
        bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        boolean result = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
       
        assertTrue(result);
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        
        for(FlightInfoType flightInfo : itinerary.getFlightInfoArray().getFlightInfo()) {
            assertEquals("CANCELLED", flightInfo.getStatus());
        }
        for(HotelInfoType hotelInfo : itinerary.getHotelsInfoArray().getHotelInfo()) {
            assertEquals("CANCELLED", hotelInfo.getStatus());
        } 
    }
  
    @Test
    public void testCancelFlightsBeforeBooking() {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a flight
       FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
    }
    
    @Test
    public void testCancelHotelsBeforeBooking() {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a flight
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        
        // add flight to itinerary
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
        
    }
    
    @Test
    public void testCancelFlightsAfterBooking() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a flight

        FlightInfoArray actualFlightInfos = getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // book itinerary
        bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        String status1 = itinerary.getFlightInfoArray().getFlightInfo().get(0).getStatus();
        String status2 = itinerary.getFlightInfoArray().getFlightInfo().get(1).getStatus();
        
        assertEquals("CANCELLED", status1);
        assertEquals("CANCELLED", status2);
    }
    
    @Test
    public void testCancelHotelsAfterBooking() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a hotel
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        HotelInfoType hotelInfo2 = actualHotelInfos.getHotelInfo().get(1);
        
        // add hotel to itinerary
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        // book itinerary
        bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        String status1 = itinerary.getHotelsInfoArray().getHotelInfo().get(0).getStatus();
        String status2 = itinerary.getHotelsInfoArray().getHotelInfo().get(1).getStatus();
        
        assertTrue(res);
        assertEquals("CANCELLED", status1);
        assertEquals("CANCELLED", status2);
    }
    
    @Test
    public void testGetItineraryMixed() {
        // create an itinerary
        String itineraryId = createItineraryOperation("555");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // search for a hotel
        HotelInfoType hotelInfo = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ))
                                    .getHotelInfo()
                                    .get(0);
        
        // add flight & hotel to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo));
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        // get itinerary
        ItineraryInfoType itineraryInfo = getItineraryOperation(itineraryId);
        
        // assert that flight with correct status is in the current itinerary
        assertEquals(1, itineraryInfo.getFlightInfoArray().getFlightInfo().size());
        assertEquals("UNCONFIRMED", itineraryInfo.getFlightInfoArray().getFlightInfo().get(0).getStatus());
        
        assertEquals(1, itineraryInfo.getHotelsInfoArray().getHotelInfo().size());
        assertEquals("UNCONFIRMED", itineraryInfo.getHotelsInfoArray().getHotelInfo().get(0).getStatus());
    }
    
    @Test
    public void testCancelItineraryWithInvalidCC() throws BookItineraryOperationFault {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = getFlightsOperation( 
                TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        HotelInfoType hotelInfo2 = actualHotelInfos.getHotelInfo().get(1);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        boolean result = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.stupidCCInfo()));
       
        assertFalse(result);
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        
        // only flights should be in confirmed state
        for(FlightInfoType flightInfo : itinerary.getFlightInfoArray().getFlightInfo()) {
            assertEquals("CONFIRMED", flightInfo.getStatus());
        }
        // hotels should be cancelled
        for(HotelInfoType hotelInfo : itinerary.getHotelsInfoArray().getHotelInfo()) {
            assertEquals("CANCELLED", hotelInfo.getStatus());
        } 
    }
    
    private static boolean bookItineraryOperation(org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.BookItineraryInputType bookItineraryInput) throws BookItineraryOperationFault {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.bookItineraryOperation(bookItineraryInput);
    }

    private static boolean cancelItineraryOperation(org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.CancelItineraryInputType cancelItineraryInput) {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.cancelItineraryOperation(cancelItineraryInput);
    }

    private static ItineraryInfoType getItineraryOperation(java.lang.String getItineraryInput) {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.getItineraryOperation(getItineraryInput);
    }

    private static ItineraryInfoType addHotelToItineraryOperation(org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.AddHotelToItineraryInputType addHotelToItineraryInput) {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.addHotelToItineraryOperation(addHotelToItineraryInput);
    }
    
    private static String createItineraryOperation(java.lang.String createItineraryInput) {
        TravelService service = new TravelService();
        TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.createItineraryOperation(createItineraryInput);
    }
    
    private static FlightInfoArray getFlightsOperation(GetFlightsInputType getFlightInput) {
        TravelService service = new TravelService();
        TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.getFlightsOperation(getFlightInput);
    }
    
    private static HotelsInfoArray getHotelsOperation(org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetTravelHotelsInputType getHotelsInput) {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.getHotelsOperation(getHotelsInput);
    }

    private static ItineraryInfoType addFlightToItineraryOperation(AddFlightToItineraryInputType addFlightToItineraryInput) {
        TravelService service = new TravelService();
        TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.addFlightToItineraryOperation(addFlightToItineraryInput);
    }
}