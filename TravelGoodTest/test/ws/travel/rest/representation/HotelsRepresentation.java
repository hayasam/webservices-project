/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.representation;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travel.rest.data.HotelInfo;

/**
 *
 * @author Paulina Bien
 */

@XmlRootElement()
public class HotelsRepresentation extends Representation {
    
    private List<HotelInfo> hotelInfo;

    public HotelsRepresentation() {
        
    }

    /**
     * @return the hotelInfo
     */
    public List<HotelInfo> getHotelInfo() {
        return hotelInfo;
    }

    /**
     * @param hotelInfo the hotelInfo to set
     */
    public void setHotelInfo(List<HotelInfo> hotelInfo) {
        this.hotelInfo = hotelInfo;
    }
    
}
