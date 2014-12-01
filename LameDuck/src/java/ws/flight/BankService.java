/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight;

import ws.bank.*;

/**
 *
 * @author VAIO
 */
public class BankService {
    
    private static final int GROUP_NUM = 11;

    public static boolean chargeCreditCard(ws.bank.CreditCardInfoType creditCardInfo, int amount, ws.bank.AccountType account) throws CreditCardFaultMessage {
        ws.bank.BankService service = new ws.bank.BankService();
        ws.bank.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(GROUP_NUM, creditCardInfo, amount, account);
    }

    public static boolean refundCreditCard(ws.bank.CreditCardInfoType creditCardInfo, int amount, ws.bank.AccountType account) throws CreditCardFaultMessage {
        ws.bank.BankService service = new ws.bank.BankService();
        ws.bank.BankPortType port = service.getBankPort();
        return port.refundCreditCard(GROUP_NUM, creditCardInfo, amount, account);
    }

    public static boolean validateCreditCard(ws.bank.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        ws.bank.BankService service = new ws.bank.BankService();
        ws.bank.BankPortType port = service.getBankPort();
        return port.validateCreditCard(GROUP_NUM, creditCardInfo, amount);
    }
    
}
