
package com.mine.product.szmtr.msgboard.message.webservice;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.0
 * 2018-12-21T16:36:10.524+08:00
 * Generated source version: 3.1.0
 * 
 */
public final class IMsgWebService_MsgWebServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://webservice.psp.szmtr.product.mine.com/", "MsgWebService");

    private IMsgWebService_MsgWebServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = MsgWebService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        MsgWebService ss = new MsgWebService(wsdlURL, SERVICE_NAME);
        IMsgWebService port = ss.getMsgWebServiceImplPort();  
        
        {
        System.out.println("Invoking msgHandler...");
        Map<String, String> _msgHandler_arg0 = null;
        try {
            java.lang.String _msgHandler__return = port.msgHandler(_msgHandler_arg0);
            System.out.println("msgHandler.result=" + _msgHandler__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}