/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.builder;

import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.AddressType;

/**
 *
 * @author Paulina Bien
 * @author Cæcilie Bach Kjærulf
 */
public class AddressBuilder {
    
    private AddressType address;
    
    protected AddressBuilder() {
        address = new AddressType();
    }
    
    public AddressBuilder withCountry(String country) {
        address.setCountry(country);
        return this;
    }
    
    public AddressBuilder withCity(String city) {
        address.setCity(city);
        return this;
    }
    
    public AddressBuilder withStreet(String street) {
        address.setStreet(street);
        return this;
    }
    
    public AddressBuilder withZipCode(String zipCode) {
        address.setZipcode(zipCode);
        return this;
    }
    
    public AddressBuilder withNumber(String number) {
        address.setNumber(number);
        return this;
    }
    
    public AddressType create() {
        return address;
    }
    
}
