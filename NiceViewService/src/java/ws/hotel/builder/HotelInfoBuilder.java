/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.builder;

import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.AddressType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;

/**
 *
 * @author Paulina
 */
public class HotelInfoBuilder {
    
    private HotelInfoType hotelInfo;
    
    protected HotelInfoBuilder() {
        hotelInfo = new HotelInfoType();
    }
    
    public HotelInfoBuilder withBookingNumber(int bookingNumber) {
        hotelInfo.setBookingNr(bookingNumber);
        return this;
    }
    
    public HotelInfoBuilder withReservationServiceName(String reservationServiceName) {
        hotelInfo.setNameOfReservService(reservationServiceName);
        return this;
    }
    
    public HotelInfoBuilder withPrice(int price) {
        hotelInfo.setPrice(price);
        return this;
    }
    
   public HotelInfoBuilder withStayPrice(int price) {
        hotelInfo.setStayPrice(price);
        return this;
    }
    
    public HotelInfoBuilder withStatus(String status) {
        hotelInfo.setStatus(status);
        return this;
    }
    
    public HotelInfoBuilder withName(String name) {
        hotelInfo.setName(name);
        return this;
    }
    
    public HotelInfoBuilder withGuarantee(Boolean guarantee) {
        hotelInfo.setGuarantee(guarantee);
        return this;
    }
    
    public HotelInfoBuilder withAddress(AddressType address) {
        hotelInfo.setAddress(address);
        return this;
    }
    
   public HotelInfoType create() {
        return hotelInfo;
    }

    
}
