/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.query;

import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.AddressType;

/**
 *
 * @author Paulina Bien
 * @author Cæcilie Bach Kjærulf
 */
public class HotelType {
    private String reservationService;
    private String name;
    private Boolean guarantee;
    private AddressType address;
    private int price;

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
     * @return the guarantee
     */
    public Boolean getGuarantee() {
        return guarantee;
    }

    /**
     * @param guarantee the guarantee to set
     */
    public void setGuarantee(Boolean guarantee) {
        this.guarantee = guarantee;
    }

    /**
     * @return the address
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(AddressType address) {
        this.address = address;
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
    
}
