
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
@WebServiceClient(name = "ZCS_ZPPF015", targetNamespace = "urn:sap-com:document:sap:soap:functions:mc-style", wsdlLocation = "doc/zcs_zppf014.wsdl")
public class ZCSZPPF015
    extends Service
{

    private final static URL ZCSZPPF015_WSDL_LOCATION;
    private final static WebServiceException ZCSZPPF015_EXCEPTION;
    private final static QName ZCSZPPF015_QNAME = new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF015");

    static {
        URL url = null;
        WebServiceException e = null;
        url = ZCSRDDS.class.getClassLoader().getResource("doc/zcs_zppf014.wsdl");
        ZCSZPPF015_WSDL_LOCATION = url;
        ZCSZPPF015_EXCEPTION = e;
    }

    public ZCSZPPF015() {
        super(__getWsdlLocation(), ZCSZPPF015_QNAME);
    }

    public ZCSZPPF015(WebServiceFeature... features) {
        super(__getWsdlLocation(), ZCSZPPF015_QNAME, features);
    }

    public ZCSZPPF015(URL wsdlLocation) {
        super(wsdlLocation, ZCSZPPF015_QNAME);
    }

    public ZCSZPPF015(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ZCSZPPF015_QNAME, features);
    }

    public ZCSZPPF015(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ZCSZPPF015(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF015")
    public ZCSRDDS getZCSZPPF015() {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF015"), ZCSRDDS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF015")
    public ZCSRDDS getZCSZPPF015(WebServiceFeature... features) {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF015"), ZCSRDDS.class, features);
    }

    /**
     * 
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF015_soap12")
    public ZCSRDDS getZCSZPPF015Soap12() {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF015_soap12"), ZCSRDDS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ZCSRDDS
     */
    @WebEndpoint(name = "ZCS_ZPPF015_soap12")
    public ZCSRDDS getZCSZPPF015Soap12(WebServiceFeature... features) {
        return super.getPort(new QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZCS_ZPPF015_soap12"), ZCSRDDS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ZCSZPPF015_EXCEPTION!= null) {
            throw ZCSZPPF015_EXCEPTION;
        }
        return ZCSZPPF015_WSDL_LOCATION;
    }

}
