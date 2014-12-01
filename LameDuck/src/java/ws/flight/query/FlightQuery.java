/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.query;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightInfoType;

/**
 *
 * @author Oguz Demir
 */
public class FlightQuery {
    
    private String fromAirport = null;
    private String toAirport = null;
    private XMLGregorianCalendar departureDate = null;
    
    protected FlightQuery() {
        
    }
    
    public FlightQuery from(String fromAirport) {
        this.fromAirport = fromAirport;
        return this;
    }
    
    public FlightQuery to(String toAirport) {
        this.toAirport = toAirport;
        return this;
    }
    
    public FlightQuery when(XMLGregorianCalendar departureDate) {
        this.departureDate = departureDate;
        return this;
    }
    
    public List<FlightInfoType> find() {
        return Flights.getFlights(fromAirport, toAirport, departureDate);
    }
    
}
