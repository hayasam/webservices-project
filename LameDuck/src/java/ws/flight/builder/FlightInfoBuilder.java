/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.builder;

import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoType;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightType;

/**
 *
 * @author VAIO
 */
public class FlightInfoBuilder {
    
    private FlightInfoType flightInfo; 
    
    protected FlightInfoBuilder() {
        flightInfo = new FlightInfoType();
    }
    
    public FlightInfoBuilder withBookingNumber(int bookingNumber) {
        flightInfo.setBookingNr(bookingNumber);
        return this;
    }
    
    public FlightInfoBuilder withReservationServiceName(String reservationServiceName) {
        flightInfo.setNameOfReservService(reservationServiceName);
        return this;
    }
    
    public FlightInfoBuilder withPrice(int price) {
        flightInfo.setPrice(price);
        return this;
    }
    
    public FlightInfoBuilder withStatus(String status) {
        flightInfo.setStatus(status);
        return this;
    }
    
    public FlightInfoBuilder withFlight(FlightType flight) {
        flightInfo.setFlight(flight);
        return this;
    }
    
    public FlightInfoType create() {
        return flightInfo;
    }
}
