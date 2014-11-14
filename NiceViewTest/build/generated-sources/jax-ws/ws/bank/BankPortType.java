
package ws.bank;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BankPortType", targetNamespace = "urn://fastmoney.imm.dtu.dk")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BankPortType {


    /**
     * 
     * @param amount
     * @param account
     * @param group
     * @param creditCardInfo
     * @return
     *     returns boolean
     * @throws CreditCardFaultMessage
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "chargeCreditCard", targetNamespace = "urn://types.fastmoney.imm.dtu.dk", className = "ws.bank.ChargeCreditCard")
    @ResponseWrapper(localName = "chargeCreditCardResponse", targetNamespace = "urn://types.fastmoney.imm.dtu.dk", className = "ws.bank.ChargeCreditCardResponse")
    public boolean chargeCreditCard(
        @WebParam(name = "group", targetNamespace = "")
        int group,
        @WebParam(name = "creditCardInfo", targetNamespace = "")
        CreditCardInfoType creditCardInfo,
        @WebParam(name = "amount", targetNamespace = "")
        int amount,
        @WebParam(name = "account", targetNamespace = "")
        AccountType account)
        throws CreditCardFaultMessage
    ;

    /**
     * 
     * @param amount
     * @param group
     * @param creditCardInfo
     * @return
     *     returns boolean
     * @throws CreditCardFaultMessage
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validateCreditCard", targetNamespace = "urn://types.fastmoney.imm.dtu.dk", className = "ws.bank.ValidateCreditCard")
    @ResponseWrapper(localName = "validateCreditCardResponse", targetNamespace = "urn://types.fastmoney.imm.dtu.dk", className = "ws.bank.ValidateCreditCardResponse")
    public boolean validateCreditCard(
        @WebParam(name = "group", targetNamespace = "")
        int group,
        @WebParam(name = "creditCardInfo", targetNamespace = "")
        CreditCardInfoType creditCardInfo,
        @WebParam(name = "amount", targetNamespace = "")
        int amount)
        throws CreditCardFaultMessage
    ;

    /**
     * 
     * @param amount
     * @param account
     * @param group
     * @param creditCardInfo
     * @return
     *     returns boolean
     * @throws CreditCardFaultMessage
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "refundCreditCard", targetNamespace = "urn://types.fastmoney.imm.dtu.dk", className = "ws.bank.RefundCreditCard")
    @ResponseWrapper(localName = "refundCreditCardResponse", targetNamespace = "urn://types.fastmoney.imm.dtu.dk", className = "ws.bank.RefundCreditCardResponse")
    public boolean refundCreditCard(
        @WebParam(name = "group", targetNamespace = "")
        int group,
        @WebParam(name = "creditCardInfo", targetNamespace = "")
        CreditCardInfoType creditCardInfo,
        @WebParam(name = "amount", targetNamespace = "")
        int amount,
        @WebParam(name = "account", targetNamespace = "")
        AccountType account)
        throws CreditCardFaultMessage
    ;

}
