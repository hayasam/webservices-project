/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.*;


/**
 *
 * @author Paulina Bien
 */
public class HotelServiceTest {
    
    public HotelServiceTest() {
    }
    
    @Test
    public void getHotelsTest()
    {
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity("Paris");
        getHotelsInput.setArrival(TestUtils.createDate("07-11-2014 08:50"));
        getHotelsInput.setDeparture(TestUtils.createDate("10-11-2014 08:50"));
        HotelsInfoArray actualHotelInfos = getHotelsOperation(getHotelsInput);
        assertEquals(2, actualHotelInfos.getHotelInfo().size());
    }
    
    @Test
    public void testBookHotelValid() throws BookOperationFault
    {
        //get hotels
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity("Paris");
        getHotelsInput.setArrival(TestUtils.createDate("07-11-2014 08:50"));
        getHotelsInput.setDeparture(TestUtils.createDate("10-11-2014 08:50"));
        HotelsInfoArray actualHotelInfos = getHotelsOperation(getHotelsInput);
        assertEquals(2, actualHotelInfos.getHotelInfo().size());
        
        //book hotel
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        bookHotelInput.setBookingNr(actualHotelInfos.getHotelInfo().get(0).getBookingNr());
        bookHotelInput.setCreditCardInfo(TestUtils.validCCInfo());
        boolean result = bookHotelsOperation(bookHotelInput);
        assertTrue(result);
    }
    
    @Test
    public void testBookHotelNoGuarantee() throws BookOperationFault
    {
        //get hotels
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity("Bucharest");
        getHotelsInput.setArrival(TestUtils.createDate("07-11-2014 08:50"));
        getHotelsInput.setDeparture(TestUtils.createDate("10-11-2014 08:50"));
        HotelsInfoArray actualHotelInfos = getHotelsOperation(getHotelsInput);
        
        
        //book 
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        bookHotelInput.setBookingNr(actualHotelInfos.getHotelInfo().get(0).getBookingNr());
        boolean result = bookHotelsOperation(bookHotelInput);
        assertTrue(result);
    }
    
    @Test
    public void testBookHotelInvalid() throws BookOperationFault
    {
        //get hotels
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity("Paris");
        getHotelsInput.setArrival(TestUtils.createDate("07-11-2014 08:50"));
        getHotelsInput.setDeparture(TestUtils.createDate("10-11-2014 08:50"));
        HotelsInfoArray actualHotelInfos = getHotelsOperation(getHotelsInput);
        assertEquals(2, actualHotelInfos.getHotelInfo().size());
        
        //book hotel with invalid CC
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        bookHotelInput.setBookingNr(actualHotelInfos.getHotelInfo().get(0).getBookingNr());
        bookHotelInput.setCreditCardInfo(TestUtils.invalidCCInfo());
               
        try {
            bookHotelsOperation(bookHotelInput);
            fail("bookHotelsOperation should have thrown an exception!");
        } catch (BookOperationFault ex) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testCancelHotel() throws CancelHotelFault, BookOperationFault
    {
        //get hotels
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity("Bucharest");
        getHotelsInput.setArrival(TestUtils.createDate("07-11-2014 08:50"));
        getHotelsInput.setDeparture(TestUtils.createDate("10-11-2014 08:50"));
        HotelsInfoArray actualHotelInfos = getHotelsOperation(getHotelsInput);
        assertEquals(1, actualHotelInfos.getHotelInfo().size());
        
        //book hotel
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        bookHotelInput.setBookingNr(actualHotelInfos.getHotelInfo().get(0).getBookingNr());
        boolean result = bookHotelsOperation(bookHotelInput);
        assertTrue(result);
        
        //cancel booking
        CancelHotelInputType input = new CancelHotelInputType();
        input.setBookingNr(bookHotelInput.getBookingNr());
        result = cancelHotelsOperation(input);
        assertTrue(result);
    }
    
    @Test
    public void testCancelHotelFail() throws CancelHotelFault, BookOperationFault
    {
        //get hotels
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity("Paris");
        getHotelsInput.setArrival(TestUtils.createDate("07-11-2014 08:50"));
        getHotelsInput.setDeparture(TestUtils.createDate("10-11-2014 08:50"));
        HotelsInfoArray actualHotelInfos = getHotelsOperation(getHotelsInput);
        assertEquals(2, actualHotelInfos.getHotelInfo().size());
        
        //book hotel
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        bookHotelInput.setBookingNr(actualHotelInfos.getHotelInfo().get(0).getBookingNr());
        bookHotelInput.setCreditCardInfo(TestUtils.validCCInfo());
        boolean result = bookHotelsOperation(bookHotelInput);
        assertTrue(result);
        
        //cancel booking
        CancelHotelInputType input = new CancelHotelInputType();
        input.setBookingNr(bookHotelInput.getBookingNr());
        result = cancelHotelsOperation(input);
        assertTrue(result);
        
        //cancel the same booking
        try {
            cancelHotelsOperation(input);
            fail("cancelHotelsOperation should have thrown an exception!");
        } catch (CancelHotelFault ex) {
            assertTrue(true);
        }
    }

    private static boolean bookHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookHotelInputType bookHotelInput) throws BookOperationFault {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.bookHotelsOperation(bookHotelInput);
    }

    private static HotelsInfoArray getHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.GetHotelsInputType getHotelsInput) {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.getHotelsOperation(getHotelsInput);
    }

    private static boolean cancelHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelInputType cancelHotelInput) throws CancelHotelFault {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.cancelHotelsOperation(cancelHotelInput);
    }






}