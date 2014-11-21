/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travel.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.xml.schema.itinerarydata.CreditCardInfoType;
import org.netbeans.xml.schema.itinerarydata.ExpirationDateType;



/**
 *
 * @author Monica
 */
public class TestUtils {
    
    public static XMLGregorianCalendar createDate (String strDate) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
        Date date = null; 
        try {
            date = (Date)formatter.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(TestUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(date);
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(TestUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlDate;
    }
    
    public static CreditCardInfoType validCCInfo() {
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(5);
        expDate.setYear(9);
        
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber("50408816");
        ccInfo.setHolderName("Anne Strandberg");
        ccInfo.setExpirationDate(expDate);
        
        return ccInfo;
    }
    
    // Invalid for purchases more 1000
    public static CreditCardInfoType invalidCCInfo() {
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(7);
        expDate.setYear(9);
        
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber("50408822");
        ccInfo.setHolderName("Bech Camilla");
        ccInfo.setExpirationDate(expDate);
        
        return ccInfo;
    }
    
    // Stupid CC info
    public static CreditCardInfoType stupidCCInfo() {
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(20);
        expDate.setYear(15);
        
        CreditCardInfoType ccInfo = new CreditCardInfoType();
        ccInfo.setCardNumber("123456789");
        ccInfo.setHolderName("Oguz Demir");
        ccInfo.setExpirationDate(expDate);
        
        return ccInfo;
    }
}
