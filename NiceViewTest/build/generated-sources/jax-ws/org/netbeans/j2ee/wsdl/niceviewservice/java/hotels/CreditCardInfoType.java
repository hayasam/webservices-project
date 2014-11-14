
package org.netbeans.j2ee.wsdl.niceviewservice.java.hotels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creditCardInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="creditCardInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="holderName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expirationDate" type="{http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels}expirationDateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creditCardInfoType", propOrder = {
    "holderName",
    "cardNumber",
    "expirationDate"
})
public class CreditCardInfoType {

    @XmlElement(required = true)
    protected String holderName;
    @XmlElement(required = true)
    protected String cardNumber;
    @XmlElement(required = true)
    protected ExpirationDateType expirationDate;

    /**
     * Gets the value of the holderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * Sets the value of the holderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolderName(String value) {
        this.holderName = value;
    }

    /**
     * Gets the value of the cardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the value of the cardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNumber(String value) {
        this.cardNumber = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link ExpirationDateType }
     *     
     */
    public ExpirationDateType getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpirationDateType }
     *     
     */
    public void setExpirationDate(ExpirationDateType value) {
        this.expirationDate = value;
    }

}
