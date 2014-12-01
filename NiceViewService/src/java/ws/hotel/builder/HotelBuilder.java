/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.builder;

import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.AddressType;
import ws.hotel.query.HotelType;

/**
 *
 * @author Paulina Bien
 * @author Cæcilie Bach Kjærulf
 */
public class HotelBuilder {
    
    private HotelType hotel;
    
    protected HotelBuilder() {
        hotel = new HotelType();
    }
    
    public HotelBuilder withReservationServiceName(String reservationServiceName) {
        hotel.setReservationService(reservationServiceName);
        return this;
    }
    
    public HotelBuilder withPrice(int price) {
        hotel.setPrice(price);
        return this;
    }
    
    public HotelBuilder withName(String name) {
        hotel.setName(name);
        return this;
    }
    
    public HotelBuilder withGuarantee(Boolean guarantee) {
        hotel.setGuarantee(guarantee);
        return this;
    }
    
    public HotelBuilder withAddress(AddressType address) {
        hotel.setAddress(address);
        return this;
    }
    
    public HotelType create() {
        return hotel;
    }
}
