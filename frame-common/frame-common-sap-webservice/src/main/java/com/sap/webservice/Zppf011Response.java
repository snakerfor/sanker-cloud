
package com.sap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EData" type="{urn:sap-com:document:sap:soap:functions:mc-style}string"/>
 *         &lt;element name="EMessage" type="{urn:sap-com:document:sap:soap:functions:mc-style}string"/>
 *         &lt;element name="EMsgty" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "eMessage",
    "eMsgty",
    "etVerinfo"
})
@XmlRootElement(name = "Zppf011Response")
public class Zppf011Response {

    @XmlElement(name = "EMessage", required = true)
    protected String eMessage;
    @XmlElement(name = "EMsgty", required = true)
    protected String eMsgty;
    @XmlElement(name = "EtVerinfo", required = true)
    /**
     * 获取eData属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    protected TableOfZstrRddsVeridInfo etVerinfo;
    /**
     * 设置eData属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */

    /**
     * 获取eMessage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMessage() {
        return eMessage;
    }

    /**
     * 设置eMessage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMessage(String value) {
        this.eMessage = value;
    }

    /**
     * 获取eMsgty属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMsgty() {
        return eMsgty;
    }

    /**
     * 设置eMsgty属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMsgty(String value) {
        this.eMsgty = value;
    }

    /**
     * 获取etVerinfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TableOfZstrRddsVeridInfo }
     *     
     */
    public TableOfZstrRddsVeridInfo getEtVerinfo() {
        return etVerinfo;
    }

    /**
     * 设置etVerinfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TableOfZstrRddsVeridInfo }
     *     
     */
    public void setEtVerinfo(TableOfZstrRddsVeridInfo value) {
        this.etVerinfo = value;
    }
}
