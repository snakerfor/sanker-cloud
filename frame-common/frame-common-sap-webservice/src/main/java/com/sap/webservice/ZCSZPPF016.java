
package com.sap.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ZCS_ZPPF016", targetNamespace = "urn:sap-com:document:sap:soap:functions:mc-style", wsdlLocation = "doc/zcs_zppf014.wsdl")
public class ZCSZPPF016
    extends Service
{

    private final static URL ZCSZPPF016_WSDL_LOCATION;
    private final static WebServiceException ZCSZPPF016_EXCEPTION;
    private final static QName ZCSZPPF016_QNAME = new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF016");

    static {
        WebServiceException e = null;
        URL url = ZCSRDDS.class.getClassLoader().getResource("doc/zcs_zppf014.wsdl");
        ZCSZPPF016_WSDL_LOCATION = url;
        ZCSZPPF016_EXCEPTION = e;
    }

    public ZCSZPPF016() {
        super(__getWsdlLocation(), ZCSZPPF016_QNAME);
    }

    public ZCSZPPF016(WebServiceFeature... features) {
        super(__getWsdlLocation(), ZCSZPPF016_QNAME, features);
    }

    public ZCSZPPF016(URL wsdlLocation) {
        super(wsdlLocation, ZCSZPPF016_QNAME);
    }

    public ZCSZPPF016(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ZCSZPPF016_QNAME, features);
    }

    public ZCSZPPF016(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ZCSZPPF016(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF016")
    public ZCSRDDS getZCSZPPF016() {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF016"), ZCSRDDS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF016")
    public ZCSRDDS getZCSZPPF016(WebServiceFeature... features) {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF016"), ZCSRDDS.class, features);
    }

    /**
     * 
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF016_soap12")
    public ZCSRDDS getZCSZPPF016Soap12() {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF016_soap12"), ZCSRDDS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF016_soap12")
    public ZCSRDDS getZCSZPPF016Soap12(WebServiceFeature... features) {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF016_soap12"), ZCSRDDS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ZCSZPPF016_EXCEPTION!= null) {
            throw ZCSZPPF016_EXCEPTION;
        }
        return ZCSZPPF016_WSDL_LOCATION;
    }

}