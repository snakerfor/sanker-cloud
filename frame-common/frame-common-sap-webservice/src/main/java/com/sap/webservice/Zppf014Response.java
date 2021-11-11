
package com.sap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡeBomlist���Ե�ֵ��
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
     * ����eBomlist���Ե�ֵ��
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
