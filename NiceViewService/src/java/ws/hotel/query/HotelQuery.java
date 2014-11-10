/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.query;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;

/**
 *
 * @author Paulina
 */
public class HotelQuery {
    
    private String city = null;
    private XMLGregorianCalendar arrivalDate = null;
    private XMLGregorianCalendar departureDate = null;
    
    protected HotelQuery() {
        
    }
    
    public HotelQuery to(String city) {
        this.city = city;
        return this;
    }
    
    public HotelQuery whenArrival(XMLGregorianCalendar arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }
    
    public HotelQuery whenDeparture(XMLGregorianCalendar departureDate) {
        this.departureDate = departureDate;
        return this;
    }
    
    public List<HotelInfoType> find() {
        return Hotels.getHotels(city, arrivalDate, departureDate);
    }
    
}
