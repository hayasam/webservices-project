
package org.netbeans.j2ee.wsdl.niceviewservice.java.hotels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hotelInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hotelInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nameOfReservService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address" type="{http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels}addressType"/>
 *         &lt;element name="bookingNr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="guarantee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stayPrice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hotelInfoType", propOrder = {
    "nameOfReservService",
    "name",
    "address",
    "bookingNr",
    "price",
    "guarantee",
    "status",
    "stayPrice"
})
public class HotelInfoType {

    @XmlElement(required = true)
    protected String nameOfReservService;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected AddressType address;
    protected int bookingNr;
    protected int price;
    protected boolean guarantee;
    @XmlElement(required = true)
    protected String status;
    protected int stayPrice;

    /**
     * Gets the value of the nameOfReservService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOfReservService() {
        return nameOfReservService;
    }

    /**
     * Sets the value of the nameOfReservService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOfReservService(String value) {
        this.nameOfReservService = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the bookingNr property.
     * 
     */
    public int getBookingNr() {
        return bookingNr;
    }

    /**
     * Sets the value of the bookingNr property.
     * 
     */
    public void setBookingNr(int value) {
        this.bookingNr = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * Gets the value of the guarantee property.
     * 
     */
    public boolean isGuarantee() {
        return guarantee;
    }

    /**
     * Sets the value of the guarantee property.
     * 
     */
    public void setGuarantee(boolean value) {
        this.guarantee = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the stayPrice property.
     * 
     */
    public int getStayPrice() {
        return stayPrice;
    }

    /**
     * Sets the value of the stayPrice property.
     * 
     */
    public void setStayPrice(int value) {
        this.stayPrice = value;
    }

}
