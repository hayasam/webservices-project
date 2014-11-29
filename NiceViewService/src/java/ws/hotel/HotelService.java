/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel;

import java.util.List;
import javax.jws.WebService;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookOperationFault;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelFault;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelInfoType;
import org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsInfoArray;
import ws.bank.CreditCardFaultMessage;
import ws.hotel.builder.BankCCInfoBuilder;
import ws.hotel.builder.Builders;
import ws.hotel.builder.ExpirationDateBuilder;
import ws.hotel.builder.HotelInfoArrayBuilder;
import ws.hotel.query.Hotels;

/**
 *
 * @author Paulina
 */
@WebService(serviceName = "hotelsService", portName = "hotelsPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", wsdlLocation = "WEB-INF/wsdl/HotelService/hotel.wsdl")
public class HotelService {

    public HotelsInfoArray getHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.GetHotelsInputType getHotelsInput) {
       List<HotelInfoType> hotelInfos = Hotels.newQuery()
                                                .to(getHotelsInput.getCity())
                                                .whenArrival(getHotelsInput.getArrival())
                                                .whenDeparture(getHotelsInput.getDeparture())
                                                .find();
        
        return Builders.newBuilder(HotelInfoArrayBuilder.class)
                            .withHotelInfos(hotelInfos)
                            .create();
    }

    public boolean bookHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookHotelInputType bookHotelInput) throws BookOperationFault, CreditCardFaultMessage {
        HotelInfoType hotelInfo = Hotels.getHotelInfo(bookHotelInput.getBookingNr());
        
        //throw an exception if doesn't exist or status is not equal confirmed
        if (hotelInfo == null)
            throw new BookOperationFault("Hotel booking failed!", "Booking number doesn't exist");
        
        if (hotelInfo.getStatus().equals(Hotels.Status.CONFIRMED.toString()))
            throw new BookOperationFault("Hotel booking failed!", "Hotel already booked");

        
        if (hotelInfo.isGuarantee() == false)
        {
            hotelInfo.setStatus(Hotels.Status.CONFIRMED.toString());
            return true;
        }
        else
        {
            ws.bank.CreditCardInfoType ccInfo = Builders.newBuilder(BankCCInfoBuilder.class)
                                                        .withName(bookHotelInput.getCreditCardInfo().getHolderName())
                                                        .withNumber(bookHotelInput.getCreditCardInfo().getCardNumber())
                                                        .withExpirationDate(Builders.newBuilder(ExpirationDateBuilder.class)
                                                                                        .withMonth(bookHotelInput.getCreditCardInfo().getExpirationDate().getMonth())
                                                                                        .withYear(bookHotelInput.getCreditCardInfo().getExpirationDate().getYear())
                                                                                        .create())
                                                        .create();

            try {
                boolean chargedCreditCard = BankService.validateCreditCard(ccInfo, hotelInfo.getStayPrice());

                if(chargedCreditCard) {
                    hotelInfo.setStatus(Hotels.Status.CONFIRMED.toString());
                }
                return chargedCreditCard;
            } catch (CreditCardFaultMessage ex) {
                throw new BookOperationFault("Hotel booking failed!", ex.getFaultInfo().getMessage());
            }  
        }
    }

    public boolean cancelHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelInputType cancelHotelInput) throws CancelHotelFault {
        HotelInfoType hotelInfo = Hotels.getHotelInfo(cancelHotelInput.getBookingNr());
        
        //throw an exception if doesn't exist or status is not equal confirmed
        if (hotelInfo == null)
            throw new CancelHotelFault("Hotel cancellation failed!", "Booking number doesn't exist");
        
        if (!hotelInfo.getStatus().equals(Hotels.Status.CONFIRMED.toString()))
            throw new CancelHotelFault("Hotel cancellation failed!", "Hotel is not booked");
        
        hotelInfo.setStatus(Hotels.Status.CANCELLED.toString());
        return true;
    }
    
}
