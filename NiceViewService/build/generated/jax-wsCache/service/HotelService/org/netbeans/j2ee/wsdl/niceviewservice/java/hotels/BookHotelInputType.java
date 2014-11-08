
package org.netbeans.j2ee.wsdl.niceviewservice.java.hotels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bookHotelInputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bookHotelInputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingNr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="creditCardInfo" type="{http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels}creditCardInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookHotelInputType", propOrder = {
    "bookingNr",
    "creditCardInfo"
})
public class BookHotelInputType {

    protected int bookingNr;
    @XmlElement(required = true)
    protected CreditCardInfoType creditCardInfo;

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
     * Gets the value of the creditCardInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardInfoType }
     *     
     */
    public CreditCardInfoType getCreditCardInfo() {
        return creditCardInfo;
    }

    /**
     * Sets the value of the creditCardInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardInfoType }
     *     
     */
    public void setCreditCardInfo(CreditCardInfoType value) {
        this.creditCardInfo = value;
    }

}
