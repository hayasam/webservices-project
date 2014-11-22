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
import ws.hotel.builder.HotelBuilder;
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
    static int hotelInfoCounter;
    
    static final List<HotelType> hotels;
    
    static {
        
        HotelType hotel1 = Builders.newBuilder((HotelBuilder.class))
                .withReservationServiceName("NiceView") 
                .withPrice(100) 
                .withName("Grand Hotel") 
                .withGuarantee(true) 
                .withAddress(Builders.newBuilder(AddressBuilder.class) 
                    .withCity("Paris")
                    .withCountry("France")
                    .withZipCode("75001")
                    .withNumber("3A")
                    .withStreet("La Rue Grande")
                    .create())
                .create();
        
        HotelType hotel2 = Builders.newBuilder((HotelBuilder.class))
                .withReservationServiceName("NiceView")
                .withPrice(120)
                .withName("Bucharest Hotel")
                .withGuarantee(false)
                .withAddress(Builders.newBuilder(AddressBuilder.class)
                    .withCity("Bucharest")
                    .withCountry("Romania")
                    .withZipCode("10067")
                    .withNumber("75")
                    .withStreet("Strada Gramont")
                    .create())
                .create();

        HotelType hotel3 = Builders.newBuilder((HotelBuilder.class))
                .withReservationServiceName("NiceView") 
                .withPrice(100) 
                .withName("Grand Hotel 2") 
                .withGuarantee(true) 
                .withAddress(Builders.newBuilder(AddressBuilder.class) 
                    .withCity("Paris")
                    .withCountry("France")
                    .withZipCode("75001")
                    .withNumber("3A")
                    .withStreet("La Rue Grande")
                    .create())
                .create();
        
        hotels = new ArrayList<HotelType>();
        hotels.add(hotel1);
        hotels.add(hotel2);
        hotels.add(hotel3);
        
        hotelInfos = new ArrayList<HotelInfoType>();
        hotelInfoCounter = 0;
    }
    
    public static HotelQuery newQuery() {
        return new HotelQuery();
    }
    
   private static int getHotelInfoId () {
        hotelInfoCounter++;
        return hotelInfoCounter*100+hotelInfoCounter*10+hotelInfoCounter;
    }
    
    protected static List<HotelInfoType> getHotels(String city, XMLGregorianCalendar arrivalDate, XMLGregorianCalendar departureDate) {
        List<HotelInfoType> result = new ArrayList<HotelInfoType>();
        
        for(HotelType hotel : hotels) {
            if (hotel.getAddress().getCity().equals(city))
            {
                int days = daysBetween(arrivalDate.toGregorianCalendar().getTimeInMillis(), 
                        departureDate.toGregorianCalendar().getTimeInMillis());
                
                HotelInfoType hotelInfo = Builders.newBuilder(HotelInfoBuilder.class)
                        .withBookingNumber(getHotelInfoId())
                        .withStatus(Status.UNCONFIRMED.toString())
                        .withStayPrice(days * hotel.getPrice())
                        .withHotel(hotel)
                        .create();
                
                result.add(hotelInfo);
                hotelInfos.add(hotelInfo);
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
