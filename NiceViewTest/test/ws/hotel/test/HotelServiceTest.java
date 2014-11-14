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
 * @author Paulina
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
    
        assertEquals(1, actualHotelInfos.getHotelInfo().size());
        assertEquals(888, actualHotelInfos.getHotelInfo().get(0).getBookingNr());
    }
    
    @Test
    public void testBookHotelValid() throws BookOperationFault
    {
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        
        bookHotelInput.setBookingNr(888);
        bookHotelInput.setCreditCardInfo(TestUtils.validCCInfo());
        
        boolean result = bookHotelsOperation(bookHotelInput);
        
        assertTrue(result);
    }
    
    @Test
    public void testBookHotelNoGuarantee() throws BookOperationFault
    {
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        
        bookHotelInput.setBookingNr(999);
        
        boolean result = bookHotelsOperation(bookHotelInput);
        
        assertTrue(result);
    }
    
    @Test
    public void testBookHotelInvalid() throws BookOperationFault
    {
        BookHotelInputType bookHotelInput = new BookHotelInputType();
        
        bookHotelInput.setBookingNr(888);
        bookHotelInput.setCreditCardInfo(TestUtils.invalidCCInfo());
        
        try {
            bookHotelsOperation(bookHotelInput);
            fail("bookHotelsOperation should have thrown an exception!");
        } catch (BookOperationFault ex) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testCancelHotel() throws CancelHotelFault
    {
        CancelHotelInputType input = new CancelHotelInputType();
        input.setBookingNr(999);

        boolean result = cancelHotelsOperation(input);
    }
    
    @Test
    public void testCancelHotelFail() throws CancelHotelFault
    {
        CancelHotelInputType input = new CancelHotelInputType();
        input.setBookingNr(123);

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