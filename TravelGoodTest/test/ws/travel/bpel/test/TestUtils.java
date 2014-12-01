/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.bpel.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.AddFlightToItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.AddHotelToItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.BookItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.CancelItineraryInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetFlightsInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.GetTravelHotelsInputType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelPortType;
import org.netbeans.j2ee.wsdl.travelgoodbpel.src.travel.TravelService;
import org.netbeans.xml.schema.itinerarydata.CreditCardInfoType;
import org.netbeans.xml.schema.itinerarydata.ExpirationDateType;
import org.netbeans.xml.schema.itinerarydata.FlightInfoArray;
import org.netbeans.xml.schema.itinerarydata.FlightInfoType;
import org.netbeans.xml.schema.itinerarydata.GetFlightInputType;
import org.netbeans.xml.schema.itinerarydata.GetHotelsInputType;
import org.netbeans.xml.schema.itinerarydata.HotelInfoType;
import org.netbeans.xml.schema.itinerarydata.HotelsInfoArray;
import org.netbeans.xml.schema.itinerarydata.ItineraryInfoType;



/**
 *
 * @author Monica Coman
 */
public class TestUtils {
    
    public static XMLGregorianCalendar createDate (String strDate) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
        Date date = null; 
        try {
            date = (Date)formatter.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(TestUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(date);
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(TestUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlDate;
    }
    
    public static CreditCardInfoType validCCInfo() {
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(5);
        expDate.setYear(9);
        
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber("50408816");
        ccInfo.setHolderName("Anne Strandberg");
        ccInfo.setExpirationDate(expDate);
        
        return ccInfo;
    }
    
    // Invalid for purchases more 1000
    public static CreditCardInfoType invalidCCInfo() {
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(7);
        expDate.setYear(9);
        
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber("50408822");
        ccInfo.setHolderName("Bech Camilla");
        ccInfo.setExpirationDate(expDate);
        
        return ccInfo;
    }
    
    // Stupid CC info
    public static CreditCardInfoType stupidCCInfo() {
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(20);
        expDate.setYear(15);
        
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber("123456789");
        ccInfo.setHolderName("Oguz Demir");
        ccInfo.setExpirationDate(expDate);
        
        return ccInfo;
    }
    
    
    public static AddHotelToItineraryInputType createAddHotelToItineraryInput(String itineraryId, HotelInfoType hotelInfo) {
        AddHotelToItineraryInputType input = new AddHotelToItineraryInputType();
        input.setItineraryId(itineraryId);
        input.setHotelInfo(hotelInfo);
        return input;
    }
    
    public static AddFlightToItineraryInputType createAddFlightToItineraryInput(String itineraryId, FlightInfoType flightInfo) {
        AddFlightToItineraryInputType input = new AddFlightToItineraryInputType();
        input.setItineraryId(itineraryId);
        input.setFlightInfo(flightInfo);
        return input;
    }
    
    public static GetFlightsInputType createGetFlightsInput(String itineraryId, String date, String startAirport, String endAirport) {
        GetFlightsInputType input = new GetFlightsInputType();
        input.setItineraryId(itineraryId);
        input.setGetFlightInput(createGetFlightInput(date, startAirport, endAirport));
        return input;
    }
    
    public static GetTravelHotelsInputType createGetTravelHotelsInput (String itineraryId, String city, String arrivalDate, String departureDate) {
        GetTravelHotelsInputType travelHotelsInputType = new GetTravelHotelsInputType();
        travelHotelsInputType.setGetHotelsInput(createGetHotelsInput(city, arrivalDate, departureDate));
        travelHotelsInputType.setItineraryId(itineraryId);       
        return travelHotelsInputType;
    }
    
    public static BookItineraryInputType createBookItineraryInput(String itineraryId, CreditCardInfoType ccInfo) {
        BookItineraryInputType input = new BookItineraryInputType();
        input.setItineraryId(itineraryId);
        input.setCreditCardInfo(ccInfo);
        return input;
    }
    
    public static CancelItineraryInputType createCancelItineraryInput(String itineraryId, CreditCardInfoType ccInfo) {
        CancelItineraryInputType input = new CancelItineraryInputType();
        input.setCreditCardInfo(ccInfo);
        input.setItineraryId(itineraryId);
        return input;
    }
    
    private static GetHotelsInputType createGetHotelsInput (String city, String arrivalDate, String departureDate) {
        GetHotelsInputType getHotelsInput = new GetHotelsInputType();
        getHotelsInput.setCity(city);
        getHotelsInput.setArrival(TestUtils.createDate(arrivalDate));
        getHotelsInput.setDeparture(TestUtils.createDate(departureDate)); 
        return getHotelsInput;
    }
    
    private static GetFlightInputType createGetFlightInput(String date, String startAirport, String endAirport) {
        GetFlightInputType getFlightsInput = new GetFlightInputType();
        getFlightsInput.setDate(TestUtils.createDate(date));
        getFlightsInput.setStartAirport(startAirport);
        getFlightsInput.setEndAirport(endAirport);
        return getFlightsInput;
    }
    
}
