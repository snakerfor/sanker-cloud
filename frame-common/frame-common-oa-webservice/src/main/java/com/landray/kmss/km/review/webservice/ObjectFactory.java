
package com.landray.kmss.km.review.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.landray.kmss.km.review.webservice package. 
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

    private final static QName _AddReview_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "addReview");
    private final static QName _AddReviewResponse_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "addReviewResponse");
    private final static QName _Exception_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "Exception");
    private final static QName _UpdateReviewInfoResponse_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "updateReviewInfoResponse");
    private final static QName _ApproveProcessResponse_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "approveProcessResponse");
    private final static QName _UpdateReviewInfo_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "updateReviewInfo");
    private final static QName _ApproveProcess_QNAME = new QName("http://webservice.review.km.kmss.landray.com/", "approveProcess");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.landray.kmss.km.review.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateReviewInfoResponse }
     * 
     */
    public UpdateReviewInfoResponse createUpdateReviewInfoResponse() {
        return new UpdateReviewInfoResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link AddReviewResponse }
     * 
     */
    public AddReviewResponse createAddReviewResponse() {
        return new AddReviewResponse();
    }

    /**
     * Create an instance of {@link AddReview }
     * 
     */
    public AddReview createAddReview() {
        return new AddReview();
    }

    /**
     * Create an instance of {@link ApproveProcess }
     * 
     */
    public ApproveProcess createApproveProcess() {
        return new ApproveProcess();
    }

    /**
     * Create an instance of {@link UpdateReviewInfo }
     * 
     */
    public UpdateReviewInfo createUpdateReviewInfo() {
        return new UpdateReviewInfo();
    }

    /**
     * Create an instance of {@link ApproveProcessResponse }
     * 
     */
    public ApproveProcessResponse createApproveProcessResponse() {
        return new ApproveProcessResponse();
    }

    /**
     * Create an instance of {@link KmReviewParamterForm }
     * 
     */
    public KmReviewParamterForm createKmReviewParamterForm() {
        return new KmReviewParamterForm();
    }

    /**
     * Create an instance of {@link AttachmentForm }
     * 
     */
    public AttachmentForm createAttachmentForm() {
        return new AttachmentForm();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddReview }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "addReview")
    public JAXBElement<AddReview> createAddReview(AddReview value) {
        return new JAXBElement<AddReview>(_AddReview_QNAME, AddReview.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddReviewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "addReviewResponse")
    public JAXBElement<AddReviewResponse> createAddReviewResponse(AddReviewResponse value) {
        return new JAXBElement<AddReviewResponse>(_AddReviewResponse_QNAME, AddReviewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateReviewInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "updateReviewInfoResponse")
    public JAXBElement<UpdateReviewInfoResponse> createUpdateReviewInfoResponse(UpdateReviewInfoResponse value) {
        return new JAXBElement<UpdateReviewInfoResponse>(_UpdateReviewInfoResponse_QNAME, UpdateReviewInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApproveProcessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "approveProcessResponse")
    public JAXBElement<ApproveProcessResponse> createApproveProcessResponse(ApproveProcessResponse value) {
        return new JAXBElement<ApproveProcessResponse>(_ApproveProcessResponse_QNAME, ApproveProcessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateReviewInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "updateReviewInfo")
    public JAXBElement<UpdateReviewInfo> createUpdateReviewInfo(UpdateReviewInfo value) {
        return new JAXBElement<UpdateReviewInfo>(_UpdateReviewInfo_QNAME, UpdateReviewInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApproveProcess }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.review.km.kmss.landray.com/", name = "approveProcess")
    public JAXBElement<ApproveProcess> createApproveProcess(ApproveProcess value) {
        return new JAXBElement<ApproveProcess>(_ApproveProcess_QNAME, ApproveProcess.class, null, value);
    }

}
