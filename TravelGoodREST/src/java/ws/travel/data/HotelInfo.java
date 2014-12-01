/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
    
package ws.travel.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Paulina Bien
 */

@XmlRootElement()
public class HotelInfo {
    
    
    private String reservationService;
    private String name;
    private Address address;
    private int bookingNr;
    private int price;
    private boolean guarantee;
    private String status;
    private XMLGregorianCalendar startDate;
    private XMLGregorianCalendar endDate;

    public HotelInfo () {
        
    }
    public HotelInfo(String reservationService, String name, Address address, int bookingNr, int price, boolean guarantee, String status, XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {
        this.reservationService = reservationService;
        this.name = name;
        this.address = address;
        this.bookingNr = bookingNr;
        this.price = price;
        this.guarantee = guarantee;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the reservationService
     */
    public String getReservationService() {
        return reservationService;
    }

    /**
     * @param reservationService the reservationService to set
     */
    public void setReservationService(String reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the bookingNr
     */
    public int getBookingNr() {
        return bookingNr;
    }

    /**
     * @param bookingNr the bookingNr to set
     */
    public void setBookingNr(int bookingNr) {
        this.bookingNr = bookingNr;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the guarantee
     */
    public boolean isGuarantee() {
        return guarantee;
    }

    /**
     * @param guarantee the guarantee to set
     */
    public void setGuarantee(boolean guarantee) {
        this.guarantee = guarantee;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the startDate
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }
}