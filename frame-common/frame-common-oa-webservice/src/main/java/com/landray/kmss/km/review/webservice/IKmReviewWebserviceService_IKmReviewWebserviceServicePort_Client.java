
package com.landray.kmss.km.review.webservice;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2021-04-14T11:05:29.088+08:00
 * Generated source version: 2.4.2
 * 
 */
public final class IKmReviewWebserviceService_IKmReviewWebserviceServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://webservice.review.km.kmss.landray.com/", "IKmReviewWebserviceServiceService");

    private IKmReviewWebserviceService_IKmReviewWebserviceServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = IKmReviewWebserviceServiceService.WSDL_LOCATION;
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
      
        IKmReviewWebserviceServiceService ss = new IKmReviewWebserviceServiceService(wsdlURL, SERVICE_NAME);
        IKmReviewWebserviceService port = ss.getIKmReviewWebserviceServicePort();  
        
        {
        System.out.println("Invoking approveProcess...");
        com.landray.kmss.km.review.webservice.KmReviewParamterForm _approveProcess_arg0 = new com.landray.kmss.km.review.webservice.KmReviewParamterForm();
        try {
            java.lang.String _approveProcess__return = port.approveProcess(_approveProcess_arg0);
            System.out.println("approveProcess.result=" + _approveProcess__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateReviewInfo...");
        com.landray.kmss.km.review.webservice.KmReviewParamterForm _updateReviewInfo_arg0 = new com.landray.kmss.km.review.webservice.KmReviewParamterForm();
        try {
            java.lang.String _updateReviewInfo__return = port.updateReviewInfo(_updateReviewInfo_arg0);
            System.out.println("updateReviewInfo.result=" + _updateReviewInfo__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking addReview...");
        com.landray.kmss.km.review.webservice.KmReviewParamterForm _addReview_arg0 = new com.landray.kmss.km.review.webservice.KmReviewParamterForm();
        try {
            java.lang.String _addReview__return = port.addReview(_addReview_arg0);
            System.out.println("addReview.result=" + _addReview__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
