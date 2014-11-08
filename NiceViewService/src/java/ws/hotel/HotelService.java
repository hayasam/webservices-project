/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel;

import javax.jws.WebService;

/**
 *
 * @author Caecilie
 */
@WebService(serviceName = "hotelsService", portName = "hotelsPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/NiceViewService/java/hotels", wsdlLocation = "WEB-INF/wsdl/HotelService/hotels.wsdl")
public class HotelService {

    public org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.HotelsInfoArray getHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.GetHotelsInputType getHotelsInput) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean bookHotelsOperation(org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookHotelInputType bookHotelInput) throws org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.BookOperationFault {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean cancelHotelsOperation(int cancelHotelInput) throws org.netbeans.j2ee.wsdl.niceviewservice.java.hotels.CancelHotelFault {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
