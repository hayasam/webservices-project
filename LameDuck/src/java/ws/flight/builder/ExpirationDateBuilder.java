/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.builder;

import ws.bank.ExpirationDateType;

/**
 *
 * @author Oguz Demir
 */
public class ExpirationDateBuilder {
    
    private ExpirationDateType expirationDate;
    
    protected ExpirationDateBuilder() {
        expirationDate = new ExpirationDateType();
    }
    
    public ExpirationDateBuilder withMonth(int month) {
        expirationDate.setMonth(month);
        return this;
    }
    
    public ExpirationDateBuilder withYear(int year) {
        expirationDate.setYear(year);
        return this;
    }
    
    public ExpirationDateType create() {
        return expirationDate;
    }
}
