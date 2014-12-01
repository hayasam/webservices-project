/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.AddressType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookHotelInputType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookOperationFault;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelFault;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelInputType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CreditCardInfoType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.ExpirationDateType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.GetHotelsInputType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsInfoArray;
import ws.travel.data.Address;
import ws.travel.data.CreditCard;
import ws.travel.data.HotelInfo;


/**
 *
 * @author Oguz Demir
 */
public class HotelService {

    public static boolean bookHotel(int bookingNum, CreditCard ccInfo) throws BookOperationFault {
        BookHotelInputType bookInput = new BookHotelInputType();
        bookInput.setBookingNr(bookingNum);
        bookInput.setCreditCardInfo(createCreditCardInfoType(ccInfo));
        return bookHotelsOperation(bookInput);
    }
    
    public static boolean cancelHotel(int bookingNum) throws CancelHotelFault {
        CancelHotelInputType cancelInput = new CancelHotelInputType();
        cancelInput.setBookingNr(bookingNum);
        return cancelHotelsOperation(cancelInput);
    }
    
    public static List<HotelInfo> getHotels(String city, String arrival, String departure) {
        return getHotels(city, toXMLGregorianCalendar(arrival), toXMLGregorianCalendar(departure));
    }
    
    private static List<HotelInfo> getHotels(String city, XMLGregorianCalendar arrivalDate, XMLGregorianCalendar departureDate) {
        GetHotelsInputType getInput = new GetHotelsInputType();
        getInput.setCity(city);
        getInput.setArrival(arrivalDate);
        getInput.setDeparture(departureDate);
        List<HotelInfoType> hotels = getHotelsOperation(getInput).getHotelInfo();
        List<HotelInfo> output = new ArrayList<HotelInfo>();
        for(HotelInfoType hotel : hotels) {
            output.add(new HotelInfo(hotel.getNameOfReservService(), 
                                     hotel.getName(),
                                     createAddress(hotel.getAddress()),
                                     hotel.getBookingNr(),
                                     hotel.getPrice(),
                                     hotel.isGuarantee(),
                                     hotel.getStatus(),
                                     hotel.getStartDate(),
                                     hotel.getEndDate()));
        }
        return output;
    }
    
    private static XMLGregorianCalendar toXMLGregorianCalendar (String strDate) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
        Date date = null; 
        try {
            date = (Date)formatter.parse(strDate);
        } catch (ParseException ex) {
            
        }
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(date);
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate);
        } catch (DatatypeConfigurationException ex) {
            
        }
        return xmlDate;
    }
    
    private static Address createAddress(AddressType address) {
        Address a = new Address();
        a.setCity(address.getCity());
        a.setCountry(address.getCountry());
        a.setNumber(address.getNumber());
        a.setStreet(address.getStreet());
        a.setZipcode(address.getZipcode());
        return a;
    }
    
    private static CreditCardInfoType createCreditCardInfoType(CreditCard cc) {
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber(cc.getCardnumber());
        ccInfo.setHolderName(cc.getName());
        ccInfo.setExpirationDate(createExpirationDateType(cc.getMonth(), cc.getYear()));
        return ccInfo;
    }
    
    private static ExpirationDateType createExpirationDateType(int month, int year) {
        ExpirationDateType date = new ExpirationDateType();
        date.setMonth(month);
        date.setYear(year);
        return date;
    }
    
    private static boolean bookHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookHotelInputType bookHotelInput) throws BookOperationFault {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.bookHotelsOperation(bookHotelInput);
    }

    private static boolean cancelHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelInputType cancelHotelInput) throws CancelHotelFault {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.cancelHotelsOperation(cancelHotelInput);
    }

    private static HotelsInfoArray getHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.GetHotelsInputType getHotelsInput) {
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService service = new org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsService();
        org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType port = service.getHotelsPortTypeBindingPort();
        return port.getHotelsOperation(getHotelsInput);
    }
    
}
