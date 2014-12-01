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
import org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightFault;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightInputType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightFault;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightInputType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.CreditCardInfoType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.ExpirationDateType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoArray;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.GetFlightInputType;
import ws.travel.data.CreditCard;
import ws.travel.data.Flight;
import ws.travel.data.FlightInfo;


/**
 *
 * @author VAIO
 */
public class FlightService {

    public static boolean bookFlight(int bookingNum, CreditCard ccInfo) throws BookFlightFault {
        return bookFlight(bookingNum, createCreditCardInfoType(ccInfo));
    }
    
    public static boolean cancelFlight(int bookingNum, int price, CreditCard ccInfo) throws CancelFlightFault {
        return cancelFlight(bookingNum, price, createCreditCardInfoType(ccInfo));
    }
    
    public static List<FlightInfo> getFlights(String date, String startAirport, String endAirport) {
        return getFlights(toXMLGregorianCalendar(date), startAirport, endAirport);
    }
    
    private static List<FlightInfo> getFlights(XMLGregorianCalendar date, String startAirport, String endAirport) {
        GetFlightInputType getFlightsInput = new GetFlightInputType();
        getFlightsInput.setDate(date);
        getFlightsInput.setStartAirport(startAirport);
        getFlightsInput.setEndAirport(endAirport);
        List<FlightInfoType> flightInfos = getFlightsOperation(getFlightsInput).getFlightInfo();
        List<FlightInfo> output = new ArrayList<FlightInfo>();
        for(FlightInfoType flightInfo : flightInfos) {
            output.add(new FlightInfo(flightInfo.getBookingNr(),
                                      flightInfo.getPrice(),
                                      flightInfo.getNameOfReservService(),
                                      createFlight(flightInfo.getFlight()),
                                      flightInfo.getStatus())); 
        }
        return output;
    }
    
    private static boolean bookFlight(int bookingNum, CreditCardInfoType ccInfo) throws BookFlightFault {
        BookFlightInputType bookFlightInput = new BookFlightInputType();
        bookFlightInput.setBookingNr(bookingNum);
        bookFlightInput.setCreditCardInfo(ccInfo);
        return bookFlightOperation(bookFlightInput);
    }
    
    private static boolean cancelFlight(int bookingNum, int price, CreditCardInfoType ccInfo) throws CancelFlightFault {
        CancelFlightInputType cancelFlightInput = new CancelFlightInputType();
        cancelFlightInput.setBookingNr(bookingNum);
        cancelFlightInput.setPrice(price);
        cancelFlightInput.setCreditCardInfo(ccInfo);
        return cancelFlightOperation(cancelFlightInput);
    }
    
    private static Flight createFlight(FlightType flight) {
        return new Flight(flight.getStartAirport(), 
                          flight.getDestinationAirport(),
                          flight.getDateDeparture(),
                          flight.getDateArrival(),
                          flight.getCarrier());
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
    
    private static boolean bookFlightOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.BookFlightInputType bookFlightInput) throws BookFlightFault {
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService service = new org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService();
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.bookFlightOperation(bookFlightInput);
    }

    private static boolean cancelFlightOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.CancelFlightInputType cancelFlightInput) throws CancelFlightFault {
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService service = new org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService();
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.cancelFlightOperation(cancelFlightInput);
    }

    private static FlightInfoArray getFlightsOperation(org.netbeans.j2ee.wsdl.lameduck.java.flight.GetFlightInputType getFlightsInput) {
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService service = new org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightService();
        org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType port = service.getFlightPortTypeBindingPort();
        return port.getFlightsOperation(getFlightsInput);
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
}
