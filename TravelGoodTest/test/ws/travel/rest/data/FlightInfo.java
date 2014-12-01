/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Monica Coman
 */
@XmlRootElement()
public class FlightInfo {
    
    private int bookingNr;
    private int price;
    private String nameOfReservService;
    private Flight flight;
    private String status;

    public FlightInfo() {
    }
 
    public FlightInfo(int bookingNr, int price, String nameOfReservService, Flight flight, String status) {
        this.bookingNr = bookingNr;
        this.price = price;
        this.nameOfReservService = nameOfReservService;
        this.flight = flight;
        this.status = status;
    }

    public int getBookingNr() {
        return bookingNr;
    }

    public void setBookingNr(int bookingNr) {
        this.bookingNr = bookingNr;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNameOfReservService() {
        return nameOfReservService;
    }

    public void setNameOfReservService(String nameOfReservService) {
        this.nameOfReservService = nameOfReservService;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }   
}
