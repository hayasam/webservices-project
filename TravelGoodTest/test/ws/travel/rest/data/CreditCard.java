/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.rest.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Audrius
 */
@XmlRootElement
public class CreditCard {
    private String name;
    private String cardNumber;
    private int expYear;
    private int expMonth;
    
    public CreditCard() {
        
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCardnumber()
    {
        return this.cardNumber;
    }
    
    public void setCardnumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    
    public int getYear()
    {
        return this.expYear;
    }
    
    public void setYear(int expYear)
    {
        this.expYear = expYear;
    }
    
    public int getMonth()
    {
        return this.expMonth;
    }
    
    public void setMonth(int expMonth)
    {
        this.expMonth = expMonth;
    }
}
