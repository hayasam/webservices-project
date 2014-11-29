/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.*;
import javax.xml.datatype.*;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.*;
import ws.flight.builder.*;

/**
 *
 * @author Monica
 */
public class Flights {
    
    public static enum Status {
       CONFIRMED ,
       UNCONFIRMED,
       CANCELLED
    }
    
    static final List<FlightInfoType> flightInfos;
    static int flightInfoCounter;
    
    static final List<FlightType> flights;
    static final Random randomGenerator;
    
    
    /*
     * Initializes flight info array with hardcoded data. 
     */
    static {
        FlightType flight1 = Builders.newBuilder(FlightBuilder.class)
                        .withCarrier("Lufthansa")
                        .withDateDeparture(createDate("07-11-2014 08:50"))
                        .withDateArrival(createDate("07-11-2014 10:50"))
                        .withStartAirport("Copenhagen Lufthavnen")
                        .withDestinationAirport("Bucharest Otopeni")
                        .create();
        FlightType flight2 = Builders.newBuilder(FlightBuilder.class)
                            .withCarrier("SAS")
                            .withDateDeparture(createDate("18-12-2014 14:50"))
                            .withDateArrival(createDate("18-12-2014 16:50"))
                            .withStartAirport("Warsaw")
                            .withDestinationAirport("Madrid")
                            .create();
        FlightType flight3 = Builders.newBuilder(FlightBuilder.class)
                            .withCarrier("Norwegian")
                            .withDateDeparture(createDate("21-01-2015 08:50"))
                            .withDateArrival(createDate("21-01-2015 10:10"))
                            .withStartAirport("Oslo")
                            .withDestinationAirport("Malmo")
                            .create();
        FlightType flight4 = Builders.newBuilder(FlightBuilder.class)
                        .withCarrier("Turkish Airlines")
                        .withDateDeparture(createDate("07-11-2014 08:50"))
                        .withDateArrival(createDate("07-11-2014 20:50"))
                        .withStartAirport("Copenhagen Lufthavnen")
                        .withDestinationAirport("Bucharest Otopeni")
                        .create();
       
        FlightType flight5 = Builders.newBuilder(FlightBuilder.class)
                        .withCarrier("SAS")
                        .withDateDeparture(createDate("10-11-2014 08:50"))
                        .withDateArrival(createDate("10-11-2014 20:50"))
                        .withStartAirport("Bucharest Otopeni")
                        .withDestinationAirport("Copenhagen Lufthavnen")
                        .create();
        FlightType flight6 = Builders.newBuilder(FlightBuilder.class)
                        .withCarrier("KLM")
                        .withDateDeparture(createDate("21-11-2014 19:40"))
                        .withDateArrival(createDate("21-11-2014 21:10"))
                        .withStartAirport("Copenhagen Lufthavnen")
                        .withDestinationAirport("Amsterdam Schiphol")
                        .create();

        flights = new ArrayList<FlightType>();
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);
        flights.add(flight5);
        flights.add(flight6);
        
        flightInfos = new ArrayList<FlightInfoType>();
        flightInfoCounter = 0;
        
        randomGenerator = new Random();
    }
  
    private static XMLGregorianCalendar createDate (String strDate) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
        Date date = null; 
        try {
            date = (Date)formatter.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
        }
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(date);
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlDate;
    }
    
    public static FlightQuery newQuery() {
        return new FlightQuery();
    }
    
    protected static List<FlightInfoType> getFlights(String departureAirport, String arrivalAirport, XMLGregorianCalendar departureDate) {
        List<FlightInfoType> result = new ArrayList<FlightInfoType>();
        
        boolean expensive = false;
        for(FlightType flight : flights)
            if(flight.getStartAirport().equals(departureAirport) 
                    && flight.getDestinationAirport().equals(arrivalAirport)
                    && equalDates(flight.getDateDeparture(), departureDate)) {
                FlightInfoType flightInfo = Builders.newBuilder(FlightInfoBuilder.class)
                                                .withBookingNumber(getFlightInfoId())
                                                .withReservationServiceName("LameDuck")
                                                .withStatus(Status.UNCONFIRMED.toString())
                                                .withPrice(expensive ? randomGenerator.nextInt(10000) + 1000 : randomGenerator.nextInt(100))
                                                .withFlight(flight)
                                                .create();
                result.add(flightInfo);
                flightInfos.add(flightInfo);
                expensive = !expensive;
            }
                
        return result;
    }
    
    private static int getFlightInfoId () {
        flightInfoCounter++;
        return flightInfoCounter*100+flightInfoCounter*10+flightInfoCounter;
    }
    private static boolean equalDates(XMLGregorianCalendar date1, XMLGregorianCalendar date2) {
        return date1.getDay()   == date2.getDay()   &&
               date1.getMonth() == date2.getMonth() &&
               date1.getYear()  == date2.getYear();
    }
    
    public static FlightInfoType getFlightInfo(int bookingNumber) {
        for(FlightInfoType flightInfo : flightInfos) {
            if(flightInfo.getBookingNr() == bookingNumber) {
                return flightInfo;
            }
        }
        return null;
    }
}
