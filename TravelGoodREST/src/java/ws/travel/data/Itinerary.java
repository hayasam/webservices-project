/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.data;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moni
 */
@XmlRootElement()
public class Itinerary {
 
    private List<FlightInfo> flightInfos; 

    private List<HotelInfo> hotelInfos;
    
    public Itinerary() {
        
    }

    public Itinerary(List<FlightInfo> flightInfos, List<HotelInfo> hotelInfos) {
        this.flightInfos = flightInfos;
        this.hotelInfos = hotelInfos;
    }

    public List<FlightInfo> getFlightInfos() {
        return flightInfos;
    }

    public void setFlightInfos(List<FlightInfo> flightInfos) {
        this.flightInfos = flightInfos;
    }    

    /**
     * @return the hotelInfos
     */
    public List<HotelInfo> getHotelInfos() {
        return hotelInfos;
    }

    /**
     * @param hotelInfos the hotelInfos to set
     */
    public void setHotelInfos(List<HotelInfo> hotelInfos) {
        this.hotelInfos = hotelInfos;
    }
}
