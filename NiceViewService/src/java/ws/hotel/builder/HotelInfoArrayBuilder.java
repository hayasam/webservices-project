/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.builder;

import java.util.List;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsInfoArray;

/**
 *
 * @author Paulina
 */
public class HotelInfoArrayBuilder {
    
    private HotelsInfoArray hotelsInfoArray;
    
    protected HotelInfoArrayBuilder() {
        hotelsInfoArray = new HotelsInfoArray();
    }
    
    public HotelInfoArrayBuilder withHotelInfos(List<HotelInfoType> hotelInfos) {
        hotelsInfoArray.getHotelInfo().clear();
        hotelsInfoArray.getHotelInfo().addAll(hotelInfos);
        return this;
    }
    
    public HotelsInfoArray create() {
        return hotelsInfoArray;
    }
    
}
