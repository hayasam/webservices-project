/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.AddFlightToItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.AddHotelToItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.BookItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.BookItineraryOperationFault;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.CancelItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetFlightsInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetTravelHotelsInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService;
import org.netbeans.xml.schema.itinerarydata.CreditCardInfoType;
import org.netbeans.xml.schema.itinerarydata.FlightInfoArray;
import org.netbeans.xml.schema.itinerarydata.FlightInfoType;
import org.netbeans.xml.schema.itinerarydata.GetFlightInputType;
import org.netbeans.xml.schema.itinerarydata.GetHotelsInputType;
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
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        
        assertEquals(1, actualFlightInfos.getFlightInfo().size());
        assertEquals("UNCONFIRMED", actualFlightInfos.getFlightInfo().get(0).getStatus());
    }
    
    @Test 
    public void testGetHotels () {
        String itineraryId = createItineraryOperation("123");
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        
        assertEquals(1, actualHotelInfos.getHotelInfo().size());
        assertEquals("UNCONFIRMED", actualHotelInfos.getHotelInfo().get(0).getStatus());
    }
    
    @Test
    public void testFlightAddToItinerary() {
        // create an itinerary
        String itineraryId = createItineraryOperation("123");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // add flight to itinerary
        ItineraryInfoType itineraryInfo = addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
    
        // assert that it is added to itinerary
        assertEquals(flightInfo.getBookingNr(), itineraryInfo.getFlightInfoArray().getFlightInfo().get(0).getBookingNr());
    }
    
    
    @Test
    public void testHotelAddToItinerary() {
        // create an itinerary
        String itineraryId = createItineraryOperation("444");
        
        // search for a hotel
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        
        // add hotel to itinerary
        ItineraryInfoType itineraryInfo = addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
    
        // assert that it is added to itinerary
        assertEquals(hotelInfo.getBookingNr(), itineraryInfo.getHotelsInfoArray().getHotelInfo().get(0).getBookingNr());
    }
    
    @Test
    public void testBookItinerary() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("444");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // add flight to itinerary
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
    
        // assert that booking is successful
        boolean res = bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
    }
    
    @Test
    public void testBookHotelItinerary() throws BookItineraryOperationFault {
        String itineraryId = createItineraryOperation("555");
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        
        ItineraryInfoType output = addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        boolean result = bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        assertTrue(result);
    }
    
    @Test
    public void testBookMixedItinerary() throws BookItineraryOperationFault
    {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        boolean result = bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
       
        assertTrue(result);
    }
    
     @Test
    public void testCancelMixedItineraryBeforeBooking() throws BookItineraryOperationFault
    {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        boolean result = cancelItineraryOperation(createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
       
        assertTrue(result);
    }
     
     @Test
    public void testCancelMixedItineraryAfterBooking() throws BookItineraryOperationFault
    {
        String itineraryId = createItineraryOperation("666");
        
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        boolean result = cancelItineraryOperation(createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
       
        assertTrue(result);
    }
     
    @Test
    public void testCancelFlightsBeforeBooking() {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a flight
       FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // add flight to itinerary
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
        
    }
    
    @Test
    public void testCancelHotelsBeforeBooking() {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a flight
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
        
        // add flight to itinerary
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
        
    }
    
    @Test
    public void testCancelFlightsAfterBooking() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // add flight to itinerary
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // book itinerary
        bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
    }
    
    @Test
    public void testCancelHotelsAfterBooking() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");
        
        
        // search for a flight
        HotelsInfoArray actualHotelInfos = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        HotelInfoType hotelInfo = actualHotelInfos.getHotelInfo().get(0);
                
        // add flight to itinerary
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo));
            
        // book itinerary
        bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        // cancel itinerary
        boolean res = cancelItineraryOperation(createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
    }
    
    @Test
    public void testGetItinerary() {
        // create an itinerary
        String itineraryId = createItineraryOperation("555");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo = actualFlightInfos.getFlightInfo().get(0);
        
        // add flight to itinerary
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // get itinerary
        ItineraryInfoType itineraryInfo = getItineraryOperation(itineraryId);
        
        // assert that flight with correct status is in the current itinerary
        assertEquals(flightInfo.getBookingNr(), itineraryInfo.getFlightInfoArray().getFlightInfo().get(0).getBookingNr());
        assertEquals(flightInfo.getStatus(), itineraryInfo.getFlightInfoArray().getFlightInfo().get(0).getStatus());
    }
    
    /**
     * @authors: Monica, Cæcilie
     * 
     * P1 test scenario from Project Definition document
     * 
     * @param itineraryId
     * @param ccInfo
     * @return 
     */
    @Test
    public void testPlanAndBookP1 () throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("555");
                       
        // get flights
        FlightInfoArray query1 = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        // add first flight
        FlightInfoType flightInfo = query1.getFlightInfo().get(0);
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // get hotels
        HotelsInfoArray hotelQuery1 = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        // add first hotel
        HotelInfoType hotelInfo1 = hotelQuery1.getHotelInfo().get(0);
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        // get flights
        FlightInfoArray query2 = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "18-12-2014 00:00", "Warsaw", "Madrid"));
        // add second flight
        FlightInfoType flightInfo2 = query2.getFlightInfo().get(0);
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // get flights
        FlightInfoArray query3 = 
                getFlightsOperation(
                    createGetFlightsInput(itineraryId, "21-01-2015 00:00", "Oslo", "Malmo"));
        // add third flight
        FlightInfoType flightInfo3 = query3.getFlightInfo().get(0);
        addFlightToItineraryOperation(createAddFlightToItineraryInput(itineraryId, flightInfo3));
        
         // get hotels
        HotelsInfoArray hotelQuery2 = getHotelsOperation(createGetTravelHotelsInput(itineraryId, "Bucharest", "07-11-2014 00:00", "10-11-2014 00:00" ));
        // add first hotel
        HotelInfoType hotelInfo2 = hotelQuery2.getHotelInfo().get(0);
        addHotelToItineraryOperation(createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        boolean result = bookItineraryOperation(createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        for (FlightInfoType flight : itinerary.getFlightInfoArray().getFlightInfo())
                assertEquals("CONFIRMED", flight.getStatus());
        for (HotelInfoType hotel : itinerary.getHotelsInfoArray().getHotelInfo())
                assertEquals("CONFIRMED", hotel.getStatus());
    }
    private CancelItineraryInputType createCancelItineraryInput(String itineraryId, CreditCardInfoType ccInfo) {
        CancelItineraryInputType input = new CancelItineraryInputType();
        input.setCreditCardInfo(ccInfo);
        input.setItineraryId(itineraryId);
        return input;
    }
    
    private BookItineraryInputType createBookItineraryInput(String itineraryId, CreditCardInfoType ccInfo) {
        BookItineraryInputType input = new BookItineraryInputType();
        input.setItineraryId(itineraryId);
        input.setCreditCardInfo(ccInfo);
        return input;
    }
    
    private GetFlightsInputType createGetFlightsInput(String itineraryId, String date, String startAirport, String endAirport) {
        GetFlightsInputType input = new GetFlightsInputType();
        input.setItineraryId(itineraryId);
        input.setGetFlightInput(createGetFlightInput(date, startAirport, endAirport));
        return input;
    }

    private AddFlightToItineraryInputType createAddFlightToItineraryInput(String itineraryId, FlightInfoType flightInfo) {
        AddFlightToItineraryInputType input = new AddFlightToItineraryInputType();
        input.setItineraryId(itineraryId);
        input.setFlightInfo(flightInfo);
        return input;
    }
    private AddHotelToItineraryInputType createAddHotelToItineraryInput(String itineraryId, HotelInfoType hotelInfo) {
        AddHotelToItineraryInputType input = new AddHotelToItineraryInputType();
        input.setItineraryId(itineraryId);
        input.setHotelInfo(hotelInfo);
        return input;
    }
    
    private GetFlightInputType createGetFlightInput(String date, String startAirport, String endAirport) {
        GetFlightInputType getFlightsInput = new GetFlightInputType();
        getFlightsInput.setDate(TestUtils.createDate(date));
        getFlightsInput.setStartAirport(startAirport);
        getFlightsInput.setEndAirport(endAirport);
        return getFlightsInput;
    }
        
    private GetTravelHotelsInputType createGetTravelHotelsInput (String itineraryId, String city, String arrivalDate, String departureDate) {
        GetTravelHotelsInputType travelHotelsInputType = new GetTravelHotelsInputType();
        travelHotelsInputType.setGetHotelsInput(createGetHotelsInput(city, arrivalDate, departureDate));
        travelHotelsInputType.setItineraryId(itineraryId);       
        return travelHotelsInputType;
    }
    
    private GetHotelsInputType createGetHotelsInput (String city, String arrivalDate, String departureDate) {
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity(city);
        getHotelsInput.setArrival(TestUtils.createDate(arrivalDate));
        getHotelsInput.setDeparture(TestUtils.createDate(departureDate)); 
        return getHotelsInput;
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

    private static ItineraryInfoType addFlightToItineraryOperation(AddFlightToItineraryInputType addFlightToItineraryInput) {
        TravelService service = new TravelService();
        TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.addFlightToItineraryOperation(addFlightToItineraryInput);
    }

    private static HotelsInfoArray getHotelsOperation(org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetTravelHotelsInputType getHotelsInput) {
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService service = new org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService();
        org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType port = service.getTravelPortTypeBindingPort();
        return port.getHotelsOperation(getHotelsInput);
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
}