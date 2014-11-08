/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.builder;

import ws.bank.ExpirationDateType;
import ws.bank.CreditCardInfoType;

/**
 *
 * @author VAIO
 */
public class BankCCInfoBuilder {
    
    private CreditCardInfoType ccInfo;
    
    protected BankCCInfoBuilder() {
        ccInfo = new CreditCardInfoType();
    }
    
    public BankCCInfoBuilder withName(String name) {
        ccInfo.setName(name);
        return this;
    }
    
    public BankCCInfoBuilder withNumber(String number) {
        ccInfo.setNumber(number);
        return this;
    }
    
    public BankCCInfoBuilder withExpirationDate(ExpirationDateType expirationDate) {
        ccInfo.setExpirationDate(expirationDate);
        return this;
    }
    
    public CreditCardInfoType create() {
        return ccInfo;
    }
    
}
