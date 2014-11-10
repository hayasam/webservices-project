/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.test;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.*;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CreditCardInfoType;
import ws.hotel.*;
import ws.bank.*;


/**
 *
 * @author Paulina
 */
public class HotelServiceTest {
    
    public HotelServiceTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testBookHotelValid() throws BookOperationFault, DatatypeConfigurationException 
    {
        BookHotelInputType input = new BookHotelInputType();
        CreditCardInfoType cc = new CreditCardInfoType();
        GregorianCalendar c = new GregorianCalendar();
        Date date = new Date();
        date.setMonth(6);
        date.setYear(2008);
        //dd/MM/yyyy
        c.setTime(date);
        XMLGregorianCalendar dateXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        
        cc.setCardNumber("50408817");
        cc.setHolderName("Klinkby Poul");
        cc.setDateExpiration(dateXML);
        
        input.setBookingNr(888);
        input.setCreditCardInfo(cc);
        
        boolean result = bookHotelsOperation(input);
        
        assertTrue(result);
    }

    private static boolean bookHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookHotelInputType bookHotelInput) throws BookOperationFault {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.bookHotelsOperation(bookHotelInput);
    }


}