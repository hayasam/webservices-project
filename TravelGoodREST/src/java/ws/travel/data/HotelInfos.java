/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.data;

import java.util.List;

/**
 *
 * @author VAIO
 */
public class HotelInfos {
    
    private List<HotelInfo> hotelInfos;
    
    public HotelInfos(List<HotelInfo> hotelInfos) {
        this.hotelInfos = hotelInfos;
    }
    
    public HotelInfos() {
        
    }
    
    public void setHotelInfos(List<HotelInfo> hotelInfos) {
        this.hotelInfos = hotelInfos;
    }
    
    public List<HotelInfo> getHotelInfos() {
        return this.hotelInfos;
    }
}
