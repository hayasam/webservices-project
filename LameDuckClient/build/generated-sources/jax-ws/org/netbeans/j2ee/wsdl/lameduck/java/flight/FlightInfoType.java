
package org.netbeans.j2ee.wsdl.lameduck.java.flight;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flightInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flightInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingNr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nameOfReservService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flight" type="{http://j2ee.netbeans.org/wsdl/LameDuck/java/flight}flightType"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flightInfoType", propOrder = {
    "bookingNr",
    "price",
    "nameOfReservService",
    "flight",
    "status"
})
public class FlightInfoType {

    protected int bookingNr;
    protected int price;
    @XmlElement(required = true)
    protected String nameOfReservService;
    @XmlElement(required = true)
    protected FlightType flight;
    @XmlElement(required = true)
    protected String status;

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
     * Gets the value of the flight property.
     * 
     * @return
     *     possible object is
     *     {@link FlightType }
     *     
     */
    public FlightType getFlight() {
        return flight;
    }

    /**
     * Sets the value of the flight property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlightType }
     *     
     */
    public void setFlight(FlightType value) {
        this.flight = value;
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

}
