/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.query;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;
import ws.hotel.builder.AddressBuilder;
import ws.hotel.builder.Builders;
import ws.hotel.builder.HotelInfoBuilder;

/**
 *
 * @author Paulina
 */
public class Hotels {

    private static int daysBetween(long timeInMillis, long timeInMillis0) {
        long miliseconds = timeInMillis0 - timeInMillis;
        return (int)miliseconds / (1000*60*60*24);
    }
    
    public static enum Status {
       CONFIRMED ,
       UNCONFIRMED,
       CANCELLED
    }
    
    static final List<HotelInfoType> hotelInfos;
    
    static {
        HotelInfoType hotel1 = Builders.newBuilder(HotelInfoBuilder.class)
                .withBookingNumber(888)
                .withReservationServiceName("NiceView")
                .withPrice(100)
                .withName("Grand Hotel")
                .withGuarantee(true)
                .withStatus(Status.UNCONFIRMED.toString())
                .withAddress(Builders.newBuilder(AddressBuilder.class)
                    .withCity("Paris")
                    .withCountry("France")
                    .withZipCode("75001")
                    .withNumber("3A")
                    .withStreet("La Rue Grande")
                    .create())
                .create();
        
        HotelInfoType hotel2 = Builders.newBuilder(HotelInfoBuilder.class)
                .withBookingNumber(999)
                .withReservationServiceName("NiceView")
                .withPrice(120)
                .withName("Bucharest Hotel")
                .withGuarantee(false)
                .withStatus(Status.UNCONFIRMED.toString())
                .withAddress(Builders.newBuilder(AddressBuilder.class)
                    .withCity("Bucharest")
                    .withCountry("Romania")
                    .withZipCode("10067")
                    .withNumber("75")
                    .withStreet("Strada Gramont")
                    .create())
                .create();
        

        hotelInfos = new ArrayList<HotelInfoType>();
        hotelInfos.add(hotel1);
        hotelInfos.add(hotel2);
    }
    
    public static HotelQuery newQuery() {
        return new HotelQuery();
    }
    
    protected static List<HotelInfoType> getHotels(String city, XMLGregorianCalendar arrivalDate, XMLGregorianCalendar departureDate) {
        List<HotelInfoType> result = new ArrayList<HotelInfoType>();
        
        for(HotelInfoType hotelInfo : hotelInfos) {
            if (hotelInfo.getAddress().getCity().equals(city))
            {
                int days = daysBetween(arrivalDate.toGregorianCalendar().getTimeInMillis(), 
                        departureDate.toGregorianCalendar().getTimeInMillis());
                hotelInfo.setStayPrice(days * hotelInfo.getPrice());
                result.add(hotelInfo);
            }
        }
        
        return result;
    }
    
    public static HotelInfoType getHotelInfo(int bookingNumber) {
        for(HotelInfoType hotelInfo : hotelInfos) {
            if(hotelInfo.getBookingNr() == bookingNumber) {
                return hotelInfo;
            }
        }
        return null;
    }
    
}
