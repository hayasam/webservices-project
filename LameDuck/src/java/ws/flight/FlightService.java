/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight;

import java.util.List;
import javax.jws.WebService;
import org.netbeans.j2ee.wsdl.lameduck.java.flight.*;
import ws.bank.AccountType;
import ws.bank.CreditCardFaultMessage;
import ws.flight.builder.AccountBuilder;
import ws.flight.builder.BankCCInfoBuilder;
import ws.flight.builder.Builders;
import ws.flight.builder.ExpirationDateBuilder;
import ws.flight.builder.FlightInfoArrayBuilder;
import ws.flight.debugger.FlightDebugger;
import ws.flight.query.Flights;

/**
 *
 * @author VAIO
 */
@WebService(serviceName = "flightService", portName = "flightPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.lameduck.java.flight.FlightPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/LameDuck/java/flight", wsdlLocation = "WEB-INF/wsdl/FlightService/flight.wsdl")
public class FlightService {

    public FlightInfoArray getFlightsOperation(GetFlightInputType getFlightsInput) {
        FlightDebugger.log(System.out, "getFlightsOperation", getFlightsInput);
        List<FlightInfoType> flightInfos = Flights.newQuery()
                                                .from(getFlightsInput.getStartAirport())
                                                .to(getFlightsInput.getEndAirport())
                                                .when(getFlightsInput.getDate())
                                                .find();
        
        return Builders.newBuilder(FlightInfoArrayBuilder.class)
                            .withFlightInfos(flightInfos)
                            .create();
    }

    public boolean bookFlightOperation(BookFlightInputType bookFlightInput) throws BookFlightFault {
        FlightDebugger.log(System.out, "bookFlightOperation", bookFlightInput);
        ws.bank.CreditCardInfoType ccInfo = Builders.newBuilder(BankCCInfoBuilder.class)
                                                    .withName(bookFlightInput.getCreditCardInfo().getHolderName())
                                                    .withNumber(bookFlightInput.getCreditCardInfo().getCardNumber())
                                                    .withExpirationDate(Builders.newBuilder(ExpirationDateBuilder.class)
                                                                                    .withMonth(bookFlightInput.getCreditCardInfo().getExpirationDate().getMonth())
                                                                                    .withYear(bookFlightInput.getCreditCardInfo().getExpirationDate().getYear())
                                                                                    .create())
                                                    .create();
        
        AccountType account = Builders.newBuilder(AccountBuilder.class)
                                            .withName("LameDuck")
                                            .withNumber("50208812")
                                            .create();
        
        FlightInfoType flightInfo = Flights.getFlightInfo(bookFlightInput.getBookingNr());
        
        try {
            boolean chargedCreditCard = BankService.chargeCreditCard(ccInfo, flightInfo.getPrice(), account);
            
            if(chargedCreditCard) {
                flightInfo.setStatus(Flights.Status.CONFIRMED.toString());
            }
            return chargedCreditCard;
        } catch (CreditCardFaultMessage ex) {
            throw new BookFlightFault("Flight cancellation failed!", ex.getFaultInfo().getMessage());
        }   
    }

    public boolean cancelFlightOperation(CancelFlightInputType cancelFlightInput) throws CancelFlightFault {
        FlightDebugger.log(System.out, "cancelFlightOperation", cancelFlightInput);
        ws.bank.CreditCardInfoType ccInfo = Builders.newBuilder(BankCCInfoBuilder.class)
                                                    .withName(cancelFlightInput.getCreditCardInfo().getHolderName())
                                                    .withNumber(cancelFlightInput.getCreditCardInfo().getCardNumber())
                                                    .withExpirationDate(Builders.newBuilder(ExpirationDateBuilder.class)
                                                                                    .withMonth(cancelFlightInput.getCreditCardInfo().getExpirationDate().getMonth())
                                                                                    .withYear(cancelFlightInput.getCreditCardInfo().getExpirationDate().getYear())
                                                                                    .create())
                                                    .create();
        
        AccountType account = Builders.newBuilder(AccountBuilder.class)
                                            .withName("LameDuck")
                                            .withNumber("50208812")
                                            .create();
        
        FlightInfoType flightInfo = Flights.getFlightInfo(cancelFlightInput.getBookingNr());
        
        try {
            boolean refundedCreditCard = BankService.refundCreditCard(ccInfo, (int) cancelFlightInput.getPrice() / 2, account);
            
            if(refundedCreditCard) {
                flightInfo.setStatus(Flights.Status.CANCELLED.toString());
            }
            return refundedCreditCard;
        } catch (CreditCardFaultMessage ex) {
            throw new CancelFlightFault("Flight cancellation failed!", ex.getFaultInfo().getMessage());
        }   
    }
    
}
