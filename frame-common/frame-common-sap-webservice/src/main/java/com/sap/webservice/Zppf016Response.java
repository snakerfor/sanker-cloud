
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
 *         &lt;element name="EBomlist" type="{urn:sap-com:document:sap:soap:functions:mc-style}TableOfZstrRddsBomlist2"/>
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
    "eBomlist",
    "eMessage",
    "eMsgty"
})
@XmlRootElement(name = "Zppf016Response")
public class Zppf016Response {

    @XmlElement(name = "EBomlist", required = true)
    protected TableOfZstrRddsBomlist2 eBomlist;
    @XmlElement(name = "EMessage", required = true)
    protected String eMessage;
    @XmlElement(name = "EMsgty", required = true)
    protected String eMsgty;

    /**
     * ��ȡeBomlist���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TableOfZstrRddsBomlist2 }
     *     
     */
    public TableOfZstrRddsBomlist2 getEBomlist() {
        return eBomlist;
    }

    /**
     * ����eBomlist���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TableOfZstrRddsBomlist2 }
     *     
     */
    public void setEBomlist(TableOfZstrRddsBomlist2 value) {
        this.eBomlist = value;
    }

    /**
     * ��ȡeMessage���Ե�ֵ��
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
     * ����eMessage���Ե�ֵ��
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
     * ��ȡeMsgty���Ե�ֵ��
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
     * ����eMsgty���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMsgty(String value) {
        this.eMsgty = value;
    }

}
