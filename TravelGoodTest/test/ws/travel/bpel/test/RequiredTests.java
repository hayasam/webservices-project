/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.bpel.test;

import static org.junit.Assert.*;
import org.junit.Test;
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

public class RequiredTests {
 
    @Test
    public void testP1 () throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("5555");
                       
        // get flights
        FlightInfoArray query1 = getFlightsOperation(TestUtils.createGetFlightsInput(itineraryId, "07-12-2014 08:50", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        // add first flight
        FlightInfoType flightInfo = query1.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // get hotels
        HotelsInfoArray hotelQuery1 = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-12-2014 00:00", "10-12-2014 00:00" ));
        // add first hotel
        HotelInfoType hotelInfo1 = hotelQuery1.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        // get flights
        FlightInfoArray query2 = getFlightsOperation(TestUtils.createGetFlightsInput(itineraryId, "18-12-2014 14:50", "Warsaw", "Madrid"));
        // add second flight
        FlightInfoType flightInfo2 = query2.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // get flights
        FlightInfoArray query3 = getFlightsOperation(TestUtils.createGetFlightsInput(itineraryId, "21-01-2015 08:50", "Oslo", "Malmo"));
        // add third flight
        FlightInfoType flightInfo3 = query3.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo3));
        
        // get hotels
        HotelsInfoArray hotelQuery2 = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Bucharest", "12-12-2014 00:00", "27-12-2014 00:00" ));
        // add second hotel
        HotelInfoType hotelInfo2 = hotelQuery2.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        // Check if itinerary objects are unconfirmed
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        for (FlightInfoType flight : itinerary.getFlightInfoArray().getFlightInfo())
            assertEquals("UNCONFIRMED", flight.getStatus());
        for (HotelInfoType hotel : itinerary.getHotelsInfoArray().getHotelInfo())
            assertEquals("UNCONFIRMED", hotel.getStatus());
        
        // Book the itinerary
        boolean result = bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        // Check if everything is confirmed
        itinerary = getItineraryOperation(itineraryId);
        for (FlightInfoType flight : itinerary.getFlightInfoArray().getFlightInfo())
                assertEquals("CONFIRMED", flight.getStatus());
        for (HotelInfoType hotel : itinerary.getHotelsInfoArray().getHotelInfo())
                assertEquals("CONFIRMED", hotel.getStatus());
    }
    
    @Test
    public void testP2()
    {
        // create an itinerary
        String itineraryId = createItineraryOperation("987");

        // get flight
        FlightInfoArray query1 = getFlightsOperation(TestUtils.createGetFlightsInput(itineraryId, "18-12-2014 14:50", "Warsaw", "Madrid"));
        // add second flight
        FlightInfoType flightInfo1 = query1.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        
        // get flight
        FlightInfoArray query2 = getFlightsOperation(TestUtils.createGetFlightsInput(itineraryId, "21-01-2015 08:50", "Oslo", "Malmo"));
        // add third flight
        FlightInfoType flightInfo2 = query2.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
                
        // cancel itinerary
        boolean res = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
    
        assertTrue(res);
    }
    
    @Test
    public void testC1() throws BookItineraryOperationFault
    {
        // create an itinerary
        String itineraryId = createItineraryOperation("666");
        
        // get flights
        FlightInfoArray actualFlightInfos = getFlightsOperation(TestUtils.createGetFlightsInput(itineraryId, "07-12-2014 08:50", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        // add flights
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // get hotel
        HotelsInfoArray actualHotelInfos = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-12-2014 00:00", "10-12-2014 00:00" ));
        // add hotel
        HotelInfoType hotelInfo1 = actualHotelInfos.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        // Book itinerary
        bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        // cancel itinerary
        boolean result = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.validCCInfo()));
        assertTrue(result);
        
        // check status of itinerary items
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        for(FlightInfoType flightInfo : itinerary.getFlightInfoArray().getFlightInfo()) {
            assertEquals("CANCELLED", flightInfo.getStatus());
        }
        for(HotelInfoType hotelInfo : itinerary.getHotelsInfoArray().getHotelInfo()) {
            assertEquals("CANCELLED", hotelInfo.getStatus());
        } 
    }
    
    @Test
    public void testB() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("111");
        
        // search for a flight
        FlightInfoArray actualFlightInfos = getFlightsOperation( TestUtils.createGetFlightsInput(itineraryId, "07-12-2014 08:50", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        FlightInfoType flightInfo2 = actualFlightInfos.getFlightInfo().get(1);
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // get hotels
        HotelsInfoArray hotelQuery1 = getHotelsOperation(
                TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-12-2014 00:00", "10-12-2014 00:00"));
        // add hotel
        HotelInfoType hotelInfo1 = hotelQuery1.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        // get itinerary status
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        
        assertEquals("UNCONFIRMED", itinerary.getFlightInfoArray().getFlightInfo().get(0).getStatus());
        assertEquals("UNCONFIRMED", itinerary.getFlightInfoArray().getFlightInfo().get(1).getStatus());
        assertEquals("UNCONFIRMED", itinerary.getHotelsInfoArray().getHotelInfo().get(0).getStatus());
        
        // second booking for flight fails
        try {
            bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.invalidCCInfo()));
            fail("Book itinerary should have failed!");
        } catch(BookItineraryOperationFault ex) {
            String faultInfo = ex.getFaultInfo();
            assertEquals("Booking itinerary failed!", faultInfo);
        }
        
        itinerary = getItineraryOperation(itineraryId);
        assertEquals("CANCELLED", itinerary.getFlightInfoArray().getFlightInfo().get(0).getStatus());
        assertEquals("UNCONFIRMED", itinerary.getFlightInfoArray().getFlightInfo().get(1).getStatus());
        assertFalse("CONFIRMED".equals(itinerary.getHotelsInfoArray().getHotelInfo().get(0).getStatus()));
        
        // since we are using flows in the BPEL implementation, the hotel bookings and flight bookings are running in parallel,
        // that means we cannot know if the flight booking failed before hotel booking done or not. 
        // In either case, the hotel booking status shouldn't be confirmed.
    }
    
    @Test
    public void testC2() throws BookItineraryOperationFault {
        // create an itinerary
        String itineraryId = createItineraryOperation("124526");
        
        // get hotels
        HotelsInfoArray hotelQuery = getHotelsOperation(
                TestUtils.createGetTravelHotelsInput(itineraryId, "Bucharest", "07-12-2014 00:00", "10-12-2014 00:00"));
        // add a hotel
        HotelInfoType hotelInfo0 = hotelQuery.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo0));
        
        // search for a flight
        FlightInfoArray actualFlightInfos = getFlightsOperation( 
                TestUtils.createGetFlightsInput(itineraryId, "07-12-2014 08:50", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        FlightInfoType flightInfo1 = actualFlightInfos.getFlightInfo().get(0);
        // add flight to itinerary
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo1));
         
        // get hotels
        HotelsInfoArray hotelQuery1 = getHotelsOperation(
                TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-12-2014 00:00", "10-12-2014 00:00"));
        // add a hotel
        HotelInfoType hotelInfo1 = hotelQuery1.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        // Book a hotel
        boolean result = bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        assertTrue(result);
        
        // Check if confirmed
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        for(FlightInfoType flightInfo : itinerary.getFlightInfoArray().getFlightInfo()) {
            assertEquals("CONFIRMED", flightInfo.getStatus());
        }
        for(HotelInfoType hotelInfo : itinerary.getHotelsInfoArray().getHotelInfo()) {
            assertEquals("CONFIRMED", hotelInfo.getStatus());
        } 
        
        // cancel itinerary but due to stupid cc info it should fail
        boolean cancelItineraryOutput= false;
        try {
            cancelItineraryOutput = cancelItineraryOperation(TestUtils.createCancelItineraryInput(itineraryId, TestUtils.stupidCCInfo()));
            fail("Book itinerary should have failed!");
        } catch(Error ex) {
        }

        // check status of itinerary items
        itinerary = getItineraryOperation(itineraryId);
        assertEquals("CANCELLED", itinerary.getHotelsInfoArray().getHotelInfo().get(0).getStatus());
        assertEquals("CONFIRMED", itinerary.getFlightInfoArray().getFlightInfo().get(0).getStatus());
        assertEquals("CANCELLED", itinerary.getHotelsInfoArray().getHotelInfo().get(1).getStatus());
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
