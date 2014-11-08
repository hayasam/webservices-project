/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.flight.*;

/**
 *
 * @author Monica
 */
public class TestUtils {
    public static List<FlightInfoType> flightInfos = new ArrayList<FlightInfoType>();
    
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
    
    public static void createFlightInfosTest1 () {        
        FlightInfoType info2 = new FlightInfoType();
        info2.setBookingNr(222);
        info2.setNameOfReservService("LameDuck");
        info2.setPrice(100);
        info2.setStatus(Status.UNCONFIRMED.toString());
        
        FlightType flight2 = new FlightType();
        flight2.setCarrier("SAS");
        flight2.setDateDeparture(createDate("18-12-2014 18:00"));
        flight2.setDateArrival(createDate("04-01-2015 09:00"));
        flight2.setStartAirport("Copenhagen Lufthavn");
        flight2.setDestinationAirport("Bucharest Otopeni");
        
        info2.setFlight(flight2);
        flightInfos.add(info2);
    }
}
