
package com.mine.product.szmtr.msgboard.message.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MsgHandler_QNAME = new QName("http://webservice.psp.szmtr.product.mine.com/", "msgHandler");
    private final static QName _MsgHandlerResponse_QNAME = new QName("http://webservice.psp.szmtr.product.mine.com/", "msgHandlerResponse");
    private final static QName _Exception_QNAME = new QName("http://webservice.psp.szmtr.product.mine.com/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MsgHandler }
     * 
     */
    public MsgHandler createMsgHandler() {
        return new MsgHandler();
    }

    /**
     * Create an instance of {@link MsgHandler.Arg0 }
     * 
     */
    public MsgHandler.Arg0 createMsgHandlerArg0() {
        return new MsgHandler.Arg0();
    }

    /**
     * Create an instance of {@link MsgHandlerResponse }
     * 
     */
    public MsgHandlerResponse createMsgHandlerResponse() {
        return new MsgHandlerResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link MsgHandler.Arg0 .Entry }
     * 
     */
    public MsgHandler.Arg0 .Entry createMsgHandlerArg0Entry() {
        return new MsgHandler.Arg0 .Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MsgHandler }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.psp.szmtr.product.mine.com/", name = "msgHandler")
    public JAXBElement<MsgHandler> createMsgHandler(MsgHandler value) {
        return new JAXBElement<MsgHandler>(_MsgHandler_QNAME, MsgHandler.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MsgHandlerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.psp.szmtr.product.mine.com/", name = "msgHandlerResponse")
    public JAXBElement<MsgHandlerResponse> createMsgHandlerResponse(MsgHandlerResponse value) {
        return new JAXBElement<MsgHandlerResponse>(_MsgHandlerResponse_QNAME, MsgHandlerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.psp.szmtr.product.mine.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
