
package org.netbeans.j2ee.wsdl.niceviewservice.java.hotels;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.netbeans.j2ee.wsdl.niceviewservice.java.hotels package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BookHotelOutput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "bookHotelOutput");
    private final static QName _BookHotelInput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "bookHotelInput");
    private final static QName _CancelHotelOutput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "cancelHotelOutput");
    private final static QName _BookHotelFault_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "bookHotelFault");
    private final static QName _GetHotelsInput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "getHotelsInput");
    private final static QName _CancelHotelInput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "cancelHotelInput");
    private final static QName _CancelHotelFault_QNAME = new QName("http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", "cancelHotelFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.netbeans.j2ee.wsdl.niceviewservice.java.hotels
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetHotelsInputType }
     * 
     */
    public GetHotelsInputType createGetHotelsInputType() {
        return new GetHotelsInputType();
    }

    /**
     * Create an instance of {@link CancelHotelInputType }
     * 
     */
    public CancelHotelInputType createCancelHotelInputType() {
        return new CancelHotelInputType();
    }

    /**
     * Create an instance of {@link BookHotelInputType }
     * 
     */
    public BookHotelInputType createBookHotelInputType() {
        return new BookHotelInputType();
    }

    /**
     * Create an instance of {@link HotelsInfoArray }
     * 
     */
    public HotelsInfoArray createHotelsInfoArray() {
        return new HotelsInfoArray();
    }

    /**
     * Create an instance of {@link HotelInfoType }
     * 
     */
    public HotelInfoType createHotelInfoType() {
        return new HotelInfoType();
    }

    /**
     * Create an instance of {@link ExpirationDateType }
     * 
     */
    public ExpirationDateType createExpirationDateType() {
        return new ExpirationDateType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link CreditCardInfoType }
     * 
     */
    public CreditCardInfoType createCreditCardInfoType() {
        return new CreditCardInfoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "bookHotelOutput")
    public JAXBElement<Boolean> createBookHotelOutput(Boolean value) {
        return new JAXBElement<Boolean>(_BookHotelOutput_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookHotelInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "bookHotelInput")
    public JAXBElement<BookHotelInputType> createBookHotelInput(BookHotelInputType value) {
        return new JAXBElement<BookHotelInputType>(_BookHotelInput_QNAME, BookHotelInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "cancelHotelOutput")
    public JAXBElement<Boolean> createCancelHotelOutput(Boolean value) {
        return new JAXBElement<Boolean>(_CancelHotelOutput_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "bookHotelFault")
    public JAXBElement<String> createBookHotelFault(String value) {
        return new JAXBElement<String>(_BookHotelFault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelsInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "getHotelsInput")
    public JAXBElement<GetHotelsInputType> createGetHotelsInput(GetHotelsInputType value) {
        return new JAXBElement<GetHotelsInputType>(_GetHotelsInput_QNAME, GetHotelsInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHotelInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "cancelHotelInput")
    public JAXBElement<CancelHotelInputType> createCancelHotelInput(CancelHotelInputType value) {
        return new JAXBElement<CancelHotelInputType>(_CancelHotelInput_QNAME, CancelHotelInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", name = "cancelHotelFault")
    public JAXBElement<String> createCancelHotelFault(String value) {
        return new JAXBElement<String>(_CancelHotelFault_QNAME, String.class, null, value);
    }

}
