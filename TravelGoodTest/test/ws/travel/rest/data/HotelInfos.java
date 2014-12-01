/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.data;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VAIO
 */
@XmlRootElement()
public class HotelInfos {
    
    private List<HotelInfo> hotelInfo;
    
    public HotelInfos(List<HotelInfo> hotelInfo) {
        this.hotelInfo = hotelInfo;
    }
    
    public HotelInfos() {
        
    }
    
    public void setHotelInfo(List<HotelInfo> hotelInfo) {
        this.hotelInfo = hotelInfo;
    }
    
    public List<HotelInfo> getHotelInfo() {
        return this.hotelInfo;
    }
}
