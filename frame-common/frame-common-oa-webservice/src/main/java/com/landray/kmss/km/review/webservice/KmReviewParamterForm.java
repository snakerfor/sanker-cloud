
package com.landray.kmss.km.review.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kmReviewParamterForm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kmReviewParamterForm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachmentForms" type="{http://webservice.review.km.kmss.landray.com/}attachmentForm" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attachmentValues" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authAreaId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docCreator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docProperty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docSubject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fdId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fdKeyword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fdSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fdTemplateId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flowParam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="formValues" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kmReviewParamterForm", propOrder = {
    "attachmentForms",
    "attachmentValues",
    "authAreaId",
    "docContent",
    "docCreator",
    "docProperty",
    "docStatus",
    "docSubject",
    "fdId",
    "fdKeyword",
    "fdSource",
    "fdTemplateId",
    "flowParam",
    "formValues"
})
public class KmReviewParamterForm {

    @XmlElement(nillable = true)
    protected List<AttachmentForm> attachmentForms;
    protected String attachmentValues;
    protected String authAreaId;
    protected String docContent;
    protected String docCreator;
    protected String docProperty;
    protected String docStatus;
    protected String docSubject;
    protected String fdId;
    protected String fdKeyword;
    protected String fdSource;
    protected String fdTemplateId;
    protected String flowParam;
    protected String formValues;

    /**
     * Gets the value of the attachmentForms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachmentForms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachmentForms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentForm }
     * 
     * 
     */
    public List<AttachmentForm> getAttachmentForms() {
        if (attachmentForms == null) {
            attachmentForms = new ArrayList<AttachmentForm>();
        }
        return this.attachmentForms;
    }

    /**
     * Gets the value of the attachmentValues property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentValues() {
        return attachmentValues;
    }

    /**
     * Sets the value of the attachmentValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentValues(String value) {
        this.attachmentValues = value;
    }

    /**
     * Gets the value of the authAreaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthAreaId() {
        return authAreaId;
    }

    /**
     * Sets the value of the authAreaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthAreaId(String value) {
        this.authAreaId = value;
    }

    /**
     * Gets the value of the docContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocContent() {
        return docContent;
    }

    /**
     * Sets the value of the docContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocContent(String value) {
        this.docContent = value;
    }

    /**
     * Gets the value of the docCreator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocCreator() {
        return docCreator;
    }

    /**
     * Sets the value of the docCreator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocCreator(String value) {
        this.docCreator = value;
    }

    /**
     * Gets the value of the docProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocProperty() {
        return docProperty;
    }

    /**
     * Sets the value of the docProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocProperty(String value) {
        this.docProperty = value;
    }

    /**
     * Gets the value of the docStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocStatus() {
        return docStatus;
    }

    /**
     * Sets the value of the docStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocStatus(String value) {
        this.docStatus = value;
    }

    /**
     * Gets the value of the docSubject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocSubject() {
        return docSubject;
    }

    /**
     * Sets the value of the docSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocSubject(String value) {
        this.docSubject = value;
    }

    /**
     * Gets the value of the fdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdId() {
        return fdId;
    }

    /**
     * Sets the value of the fdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdId(String value) {
        this.fdId = value;
    }

    /**
     * Gets the value of the fdKeyword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdKeyword() {
        return fdKeyword;
    }

    /**
     * Sets the value of the fdKeyword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdKeyword(String value) {
        this.fdKeyword = value;
    }

    /**
     * Gets the value of the fdSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdSource() {
        return fdSource;
    }

    /**
     * Sets the value of the fdSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdSource(String value) {
        this.fdSource = value;
    }

    /**
     * Gets the value of the fdTemplateId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdTemplateId() {
        return fdTemplateId;
    }

    /**
     * Sets the value of the fdTemplateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdTemplateId(String value) {
        this.fdTemplateId = value;
    }

    /**
     * Gets the value of the flowParam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowParam() {
        return flowParam;
    }

    /**
     * Sets the value of the flowParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowParam(String value) {
        this.flowParam = value;
    }

    /**
     * Gets the value of the formValues property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormValues() {
        return formValues;
    }

    /**
     * Sets the value of the formValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormValues(String value) {
        this.formValues = value;
    }

}
