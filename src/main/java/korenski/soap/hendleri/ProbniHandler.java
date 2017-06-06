package korenski.soap.hendleri;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProbniHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return new HashSet<QName>();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
    	
    	System.out.println("HandleMessage okinut");
    	
        Boolean isOutBound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        try {
            if (isOutBound){
                System.out.println("Intercepting outbound message:");
            } else {
                System.out.println("Intercepting inbound message:");
            }
            context.getMessage().writeTo(System.out);
            System.out.println();
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {
        System.out.println("Do some cleanup handling .. ");
    }
}