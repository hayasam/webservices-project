/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.bank.creditcard;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Audrius
 */
@XmlRootElement()
public class CreditCard {
    private String name;
    private String cardNumber;
    private int expYear;
    private int expMonth;
    
    @XmlAttribute()
    public String getName()
    {
        return this.name;
    }
    
    @XmlAttribute()
    public void setName(String name)
    {
        this.name = name;
    }
    
    @XmlAttribute()
    public String getCardnumber()
    {
        return this.cardNumber;
    }
    
    @XmlAttribute()
    public void setCardnumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    
    @XmlAttribute()
    public int getYear()
    {
        return this.expYear;
    }
    
    @XmlAttribute()
    public void setYear(int expYear)
    {
        this.expYear = expYear;
    }
    
    @XmlAttribute()
    public int getMonth()
    {
        return this.expMonth;
    }
    
    @XmlAttribute()
    public void setMonth(int expMonth)
    {
        this.expMonth = expMonth;
    }
}
