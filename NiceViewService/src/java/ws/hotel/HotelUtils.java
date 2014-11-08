/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel;


import java.text.*;
import java.util.*;
import java.util.logging.*;
import javax.xml.datatype.*;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.AddressType;

/**
 *
 * @author Monica
 */
public class HotelUtils {
    public static List<HotelInfoType> HotelInfos = new ArrayList<HotelInfoType>();
    
    public static enum Status{
       CONFIRMED ,
       UNCONFIRMED,
       CANCELLED
    }
    
    public static XMLGregorianCalendar createDate (String strDate) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
        Date date = null; 
        try {
            date = (Date)formatter.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(HotelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(date);
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(HotelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlDate;
    }
    
    public static void createFlightInfos () {
        HotelInfoType hotelinfo1 = new HotelInfoType();
        
        hotelinfo1.setBookingNr(888);
        hotelinfo1.setNameOfReservService("NiceView");
        hotelinfo1.setPrice(100);
        hotelinfo1.setName("Grand Hotel");
        hotelinfo1.setGuarantee(true);
        hotelinfo1.setStatus(HotelUtils.Status.UNCONFIRMED.toString());
        
        AddressType address = new AddressType();
        address.setCountry("France");
        address.setZipcode("75001");
        address.setCity("Paris");
        address.setStreet("La Rue Grande");
        address.setNumber("3A");
        
        hotelinfo1.setAddress(address);
        HotelInfos.add(hotelinfo1);
        
        HotelInfoType hotelinfo2 = new HotelInfoType();
        
        hotelinfo2.setBookingNr(999);
        hotelinfo2.setNameOfReservService("NiceView");
        hotelinfo2.setPrice(120);
        hotelinfo2.setName("Bucharest Hotel");
        hotelinfo2.setGuarantee(true);
        hotelinfo2.setStatus(HotelUtils.Status.UNCONFIRMED.toString());
        
        AddressType address1 = new AddressType();
        address1.setCountry("Romania");
        address1.setZipcode("10067");
        address1.setCity("Bucharest");
        address1.setStreet("Strada Gramont");
        address1.setNumber("75");
        
        hotelinfo2.setAddress(address1);
        HotelInfos.add(hotelinfo2);
    }
}
