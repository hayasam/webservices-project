
package org.netbeans.j2ee.wsdl.lameduck.java.flight;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.netbeans.j2ee.wsdl.lameduck.java.flight package. 
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

    private final static QName _BookFlightFault_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "bookFlightFault");
    private final static QName _CancelFlightOuput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "cancelFlightOuput");
    private final static QName _CancelFlightInput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "cancelFlightInput");
    private final static QName _BookFlightOutput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "bookFlightOutput");
    private final static QName _GetFlightInput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "getFlightInput");
    private final static QName _BookFlightInput_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "bookFlightInput");
    private final static QName _CancelFlightFault_QNAME = new QName("http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", "cancelFlightFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.netbeans.j2ee.wsdl.lameduck.java.flight
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelFlightInputType }
     * 
     */
    public CancelFlightInputType createCancelFlightInputType() {
        return new CancelFlightInputType();
    }

    /**
     * Create an instance of {@link BookFlightInputType }
     * 
     */
    public BookFlightInputType createBookFlightInputType() {
        return new BookFlightInputType();
    }

    /**
     * Create an instance of {@link GetFlightInputType }
     * 
     */
    public GetFlightInputType createGetFlightInputType() {
        return new GetFlightInputType();
    }

    /**
     * Create an instance of {@link FlightInfoArray }
     * 
     */
    public FlightInfoArray createFlightInfoArray() {
        return new FlightInfoArray();
    }

    /**
     * Create an instance of {@link FlightInfoType }
     * 
     */
    public FlightInfoType createFlightInfoType() {
        return new FlightInfoType();
    }

    /**
     * Create an instance of {@link FlightType }
     * 
     */
    public FlightType createFlightType() {
        return new FlightType();
    }

    /**
     * Create an instance of {@link CreditCardInfoType }
     * 
     */
    public CreditCardInfoType createCreditCardInfoType() {
        return new CreditCardInfoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "bookFlightFault")
    public JAXBElement<String> createBookFlightFault(String value) {
        return new JAXBElement<String>(_BookFlightFault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "cancelFlightOuput")
    public JAXBElement<Boolean> createCancelFlightOuput(Boolean value) {
        return new JAXBElement<Boolean>(_CancelFlightOuput_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelFlightInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "cancelFlightInput")
    public JAXBElement<CancelFlightInputType> createCancelFlightInput(CancelFlightInputType value) {
        return new JAXBElement<CancelFlightInputType>(_CancelFlightInput_QNAME, CancelFlightInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "bookFlightOutput")
    public JAXBElement<Boolean> createBookFlightOutput(Boolean value) {
        return new JAXBElement<Boolean>(_BookFlightOutput_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlightInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "getFlightInput")
    public JAXBElement<GetFlightInputType> createGetFlightInput(GetFlightInputType value) {
        return new JAXBElement<GetFlightInputType>(_GetFlightInput_QNAME, GetFlightInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookFlightInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "bookFlightInput")
    public JAXBElement<BookFlightInputType> createBookFlightInput(BookFlightInputType value) {
        return new JAXBElement<BookFlightInputType>(_BookFlightInput_QNAME, BookFlightInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", name = "cancelFlightFault")
    public JAXBElement<String> createCancelFlightFault(String value) {
        return new JAXBElement<String>(_CancelFlightFault_QNAME, String.class, null, value);
    }

}
