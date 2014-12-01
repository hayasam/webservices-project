/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.builder;

import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightType;

/**
 *
 * @author Oguz Demir
 */
public class FlightBuilder {
    
    private FlightType flight;
    
    protected FlightBuilder() {
        flight = new FlightType();
    }
    
    public FlightBuilder withCarrier(String carrier) {
        flight.setCarrier(carrier);
        return this;
    }
    
    public FlightBuilder withDateDeparture(XMLGregorianCalendar dateDeparture) {
        flight.setDateDeparture(dateDeparture);
        return this;
    }
    
    public FlightBuilder withDateArrival(XMLGregorianCalendar dateArrival) {
        flight.setDateArrival(dateArrival);
        return this;
    }
    
    public FlightBuilder withStartAirport(String startAirport) {
        flight.setStartAirport(startAirport);
        return this;
    }
    
    public FlightBuilder withDestinationAirport(String destinationAirport) {
        flight.setDestinationAirport(destinationAirport);
        return this;
    }
    
    public FlightType create() {
        return flight;
    }
}
