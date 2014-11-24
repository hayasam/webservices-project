/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.test;

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

/**
 *
 * @author Paulina
 */
public class TravelServiceRequiredTests {
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
                    TestUtils.createGetFlightsInput(itineraryId, "07-11-2014 00:00", "Copenhagen Lufthavnen", "Bucharest Otopeni"));
        // add first flight
        FlightInfoType flightInfo = query1.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo));
        
        // get hotels
        HotelsInfoArray hotelQuery1 = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Paris", "07-11-2014 00:00", "10-11-2014 00:00" ));
        // add first hotel
        HotelInfoType hotelInfo1 = hotelQuery1.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo1));
        
        // get flights
        FlightInfoArray query2 = 
                    getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "18-12-2014 00:00", "Warsaw", "Madrid"));
        // add second flight
        FlightInfoType flightInfo2 = query2.getFlightInfo().get(0);
        addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo2));
        
        // get flights
        FlightInfoArray query3 = 
                getFlightsOperation(
                    TestUtils.createGetFlightsInput(itineraryId, "21-01-2015 00:00", "Oslo", "Malmo"));
        // add third flight
        FlightInfoType flightInfo3 = query3.getFlightInfo().get(0);
            addFlightToItineraryOperation(TestUtils.createAddFlightToItineraryInput(itineraryId, flightInfo3));
        
         // get hotels
        HotelsInfoArray hotelQuery2 = getHotelsOperation(TestUtils.createGetTravelHotelsInput(itineraryId, "Bucharest", "07-11-2014 00:00", "10-11-2014 00:00" ));
        // add first hotel
        HotelInfoType hotelInfo2 = hotelQuery2.getHotelInfo().get(0);
        addHotelToItineraryOperation(TestUtils.createAddHotelToItineraryInput(itineraryId, hotelInfo2));
        
        boolean result = bookItineraryOperation(TestUtils.createBookItineraryInput(itineraryId, TestUtils.validCCInfo()));
        
        ItineraryInfoType itinerary = getItineraryOperation(itineraryId);
        for (FlightInfoType flight : itinerary.getFlightInfoArray().getFlightInfo())
                assertEquals("CONFIRMED", flight.getStatus());
        for (HotelInfoType hotel : itinerary.getHotelsInfoArray().getHotelInfo())
                assertEquals("CONFIRMED", hotel.getStatus());
    }
    
    @Test
    public void testPlanAndCancelP2()
    {
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
