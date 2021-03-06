package com.mine.product.szmtr.msgboard.message.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.0
 * 2018-12-21T16:36:10.535+08:00
 * Generated source version: 3.1.0
 * 
 */
@WebServiceClient(name = "MsgWebService", 
                  wsdlLocation = "http://localhost:8080/services/msgservice?wsdl",
                  targetNamespace = "http://webservice.psp.szmtr.product.mine.com/") 
public class MsgWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservice.psp.szmtr.product.mine.com/", "MsgWebService");
    public final static QName MsgWebServiceImplPort = new QName("http://webservice.psp.szmtr.product.mine.com/", "MsgWebServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/services/msgservice?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(MsgWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/services/msgservice?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public MsgWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MsgWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MsgWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public MsgWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public MsgWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public MsgWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns IMsgWebService
     */
    @WebEndpoint(name = "MsgWebServiceImplPort")
    public IMsgWebService getMsgWebServiceImplPort() {
        return super.getPort(MsgWebServiceImplPort, IMsgWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IMsgWebService
     */
    @WebEndpoint(name = "MsgWebServiceImplPort")
    public IMsgWebService getMsgWebServiceImplPort(WebServiceFeature... features) {
        return super.getPort(MsgWebServiceImplPort, IMsgWebService.class, features);
    }

}
