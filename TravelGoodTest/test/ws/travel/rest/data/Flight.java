/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Moni
 */
@XmlRootElement()
public class Flight {
    
    private String startAirport;
    private String destinationAirport;
    private XMLGregorianCalendar dateDeparture;
    private XMLGregorianCalendar dateArrival;
    private String carrier;

    public Flight() {
    }
    
    public Flight(String startAirport, String destinationAirport, XMLGregorianCalendar dateDeparture, XMLGregorianCalendar dateArrival, String carrier) {
        this.startAirport = startAirport;
        this.destinationAirport = destinationAirport;
        this.dateDeparture = dateDeparture;
        this.dateArrival = dateArrival;
        this.carrier = carrier;
    }

    public String getStartAirport() {
        return startAirport;
    }

    public void setStartAirport(String startAirport) {
        this.startAirport = startAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public XMLGregorianCalendar getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(XMLGregorianCalendar dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public XMLGregorianCalendar getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(XMLGregorianCalendar dateArrival) {
        this.dateArrival = dateArrival;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }   
}
