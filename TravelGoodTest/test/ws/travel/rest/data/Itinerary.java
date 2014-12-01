/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.data;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moni
 */
@XmlRootElement()
public class Itinerary {
 
    private String status;
    private List<FlightInfo> flightInfos; 

    private List<HotelInfo> hotelInfos;
  
    public Itinerary() {
        
    }

    public Itinerary(List<FlightInfo> flightInfos, List<HotelInfo> hotelInfos, String status) {
        this.flightInfos = flightInfos;
        this.hotelInfos = hotelInfos;
        this.status = status;
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
    
    public void addHotelToItinerary(HotelInfo hotelInfo)
    {
        this.hotelInfos.add(hotelInfo);
    }
    
    public void addFlightToItinerary(FlightInfo flightInfo)
    {
        this.flightInfos.add(flightInfo);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
