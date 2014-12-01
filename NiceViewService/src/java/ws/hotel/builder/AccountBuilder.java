/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.builder;

import ws.bank.AccountType;
/**
 *
 * @author Oguz Demir
 */
public class AccountBuilder {
    
    private AccountType account;
    
    protected AccountBuilder() {
        account = new AccountType();
    }
    
    public AccountBuilder withName(String name) {
        account.setName(name);
        return this;
    }
    
    public AccountBuilder withNumber(String number) {
        account.setNumber(number);
        return this;
    }
    
    public AccountType create() {
        return account;
    }
}
