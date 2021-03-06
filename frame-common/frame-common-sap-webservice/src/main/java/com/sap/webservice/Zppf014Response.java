
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
    "eBomlist"
})
@XmlRootElement(name = "Zppf014Response")
public class Zppf014Response {

    @XmlElement(name = "EBomlist", required = true)
    protected TableOfZstrRddsBomlist eBomlist;

    /**
     * 获取eBomlist属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TableOfZstrRddsBomlist }
     *     
     */
    public TableOfZstrRddsBomlist getEBomlist() {
        return eBomlist;
    }

    /**
     * 设置eBomlist属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TableOfZstrRddsBomlist }
     *     
     */
    public void setEBomlist(TableOfZstrRddsBomlist value) {
        this.eBomlist = value;
    }

}
