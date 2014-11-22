/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.expDates;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Audrius
 */
@XmlRootElement()
public class ExpirationDates {
    
private int month;
private int year;

    @XmlAttribute()
    public int getMonth() {
        return month;
    }
    @XmlAttribute()
    public void setMonth(int value) {
        this.month = value;
    }
    @XmlAttribute()
    public int getYear() {
        return year;
    }
    @XmlAttribute()
    public void setYear(int value) {
        this.year = value;
    }
}
