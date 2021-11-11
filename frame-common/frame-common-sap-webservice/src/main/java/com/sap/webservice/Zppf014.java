
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
 *         &lt;element name="IDate" type="{urn:sap-com:document:sap:soap:functions:mc-style}char10" minOccurs="0"/>
 *         &lt;element name="IMatnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40" minOccurs="0"/>
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
    "iDate",
    "iMatnr"
})
@XmlRootElement(name = "Zppf014")
public class Zppf014 {

    @XmlElement(name = "EBomlist", required = true)
    protected TableOfZstrRddsBomlist eBomlist;
    @XmlElement(name = "IDate")
    protected String iDate;
    @XmlElement(name = "IMatnr")
    protected String iMatnr;
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

    /**
     * ��ȡiDate���Ե�ֵ��
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
     * ����iDate���Ե�ֵ��
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
     * ��ȡiMatnr���Ե�ֵ��
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
     * ����iMatnr���Ե�ֵ��
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
