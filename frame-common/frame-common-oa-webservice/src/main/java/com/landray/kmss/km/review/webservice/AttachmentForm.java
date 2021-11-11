
package com.landray.kmss.km.review.webservice;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attachmentForm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attachmentForm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fdKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fdFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fdAttachment" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attachmentForm", propOrder = {
    "fdKey",
    "fdFileName",
    "fdAttachment"
})
public class AttachmentForm {

    protected String fdKey;
    protected String fdFileName;
    @XmlMimeType("application/octet-stream")
    protected DataHandler fdAttachment;

    /**
     * Gets the value of the fdKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdKey() {
        return fdKey;
    }

    /**
     * Sets the value of the fdKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdKey(String value) {
        this.fdKey = value;
    }

    /**
     * Gets the value of the fdFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdFileName() {
        return fdFileName;
    }

    /**
     * Sets the value of the fdFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdFileName(String value) {
        this.fdFileName = value;
    }

    /**
     * Gets the value of the fdAttachment property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getFdAttachment() {
        return fdAttachment;
    }

    /**
     * Sets the value of the fdAttachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setFdAttachment(DataHandler value) {
        this.fdAttachment = value;
    }

}
