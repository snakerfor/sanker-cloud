
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
 *         &lt;element name="IDate" type="{urn:sap-com:document:sap:soap:functions:mc-style}char10" minOccurs="0"/>
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
    "etVerinfo",
    "iDate",
    "iMatnr"
})
@XmlRootElement(name = "Zppf011")
public class Zppf011 {

    @XmlElement(name = "EtVerinfo", required = true)
    protected TableOfZstrRddsVeridInfo etVerinfo;
    @XmlElement(name = "IDate")
    protected String iDate;
    @XmlElement(name = "IMatnr")
    protected String iMatnr;

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

    /**
     * 获取iDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDate() {
        return iDate;
    }

    /**
     * 设置iDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDate(String value) {
        this.iDate = value;
    }

    /**
     * 获取iMatnr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMatnr() {
        return iMatnr;
    }

    /**
     * 设置iMatnr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMatnr(String value) {
        this.iMatnr = value;
    }
}
