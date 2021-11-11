
package com.sap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZstrRddsVeridInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ZstrRddsVeridInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Werks" type="{urn:sap-com:document:sap:soap:functions:mc-style}char4"/>
 *         &lt;element name="Matnr" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="Verid" type="{urn:sap-com:document:sap:soap:functions:mc-style}char4"/>
 *         &lt;element name="Text1" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Andat" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Aedat" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Adatu" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Bdatu" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Plnnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Plnal" type="{urn:sap-com:document:sap:soap:functions:mc-style}char2"/>
 *         &lt;element name="Stlnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Stlal" type="{urn:sap-com:document:sap:soap:functions:mc-style}char2"/>
 *         &lt;element name="Stlan" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Ztbsj" type="{urn:sap-com:document:sap:soap:functions:mc-style}char30"/>
 *         &lt;element name="Zyjsc" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZstrRddsVeridInfo", propOrder = {
    "scbbh",
    "werks",
    "matnr",
    "verid",
    "text1",
    "andat",
    "aedat",
    "adatu",
    "bdatu",
    "plnnr",
    "plnal",
    "gylxBh",
    "bomno",
    "stlnr",
    "stlal",
    "stlan",
    "ztbsj",
    "zyjsc"
})
public class ZstrRddsVeridInfo {

    @XmlElement(name = "Scbbh", required = true)
    protected String scbbh;
    @XmlElement(name = "Werks", required = true)
    protected String werks;
    @XmlElement(name = "Matnr", required = true)
    protected String matnr;
    @XmlElement(name = "Verid", required = true)
    protected String verid;
    @XmlElement(name = "Text1", required = true)
    protected String text1;
    @XmlElement(name = "Andat", required = true)
    protected String andat;
    @XmlElement(name = "Aedat", required = true)
    protected String aedat;
    @XmlElement(name = "Adatu", required = true)
    protected String adatu;
    @XmlElement(name = "Bdatu", required = true)
    protected String bdatu;
    @XmlElement(name = "Plnnr", required = true)
    protected String plnnr;
    @XmlElement(name = "Plnal", required = true)
    protected String plnal;
    @XmlElement(name = "GylxBh", required = true)
    protected String gylxBh;
    @XmlElement(name = "Bomno", required = true)
    protected String bomno;
    @XmlElement(name = "Stlnr", required = true)
    protected String stlnr;
    @XmlElement(name = "Stlal", required = true)
    protected String stlal;
    @XmlElement(name = "Stlan", required = true)
    protected String stlan;
    @XmlElement(name = "Ztbsj", required = true)
    protected String ztbsj;
    @XmlElement(name = "Zyjsc", required = true)
    protected String zyjsc;

    /**
     * ��ȡscbbh���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScbbh() {
        return scbbh;
    }

    /**
     * ����scbbh���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScbbh(String value) {
        this.scbbh = value;
    }

    /**
     * ��ȡwerks���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWerks() {
        return werks;
    }

    /**
     * ����werks���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWerks(String value) {
        this.werks = value;
    }

    /**
     * ��ȡmatnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatnr() {
        return matnr;
    }

    /**
     * ����matnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatnr(String value) {
        this.matnr = value;
    }

    /**
     * ��ȡverid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerid() {
        return verid;
    }

    /**
     * ����verid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerid(String value) {
        this.verid = value;
    }

    /**
     * ��ȡtext1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText1() {
        return text1;
    }

    /**
     * ����text1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText1(String value) {
        this.text1 = value;
    }

    /**
     * ��ȡandat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAndat() {
        return andat;
    }

    /**
     * ����andat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAndat(String value) {
        this.andat = value;
    }

    /**
     * ��ȡaedat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAedat() {
        return aedat;
    }

    /**
     * ����aedat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAedat(String value) {
        this.aedat = value;
    }

    /**
     * ��ȡadatu���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdatu() {
        return adatu;
    }

    /**
     * ����adatu���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdatu(String value) {
        this.adatu = value;
    }

    /**
     * ��ȡbdatu���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBdatu() {
        return bdatu;
    }

    /**
     * ����bdatu���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBdatu(String value) {
        this.bdatu = value;
    }

    /**
     * ��ȡplnnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlnnr() {
        return plnnr;
    }

    /**
     * ����plnnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlnnr(String value) {
        this.plnnr = value;
    }

    /**
     * ��ȡplnal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlnal() {
        return plnal;
    }

    /**
     * ����plnal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlnal(String value) {
        this.plnal = value;
    }

    /**
     * ��ȡgylxBh���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGylxBh() {
        return gylxBh;
    }

    /**
     * ����gylxBh���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGylxBh(String value) {
        this.gylxBh = value;
    }

    /**
     * ��ȡbomno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBomno() {
        return bomno;
    }

    /**
     * ����bomno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBomno(String value) {
        this.bomno = value;
    }

    /**
     * ��ȡstlnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStlnr() {
        return stlnr;
    }

    /**
     * ����stlnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStlnr(String value) {
        this.stlnr = value;
    }

    /**
     * ��ȡstlal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStlal() {
        return stlal;
    }

    /**
     * ����stlal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStlal(String value) {
        this.stlal = value;
    }

    /**
     * ��ȡstlan���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStlan() {
        return stlan;
    }

    /**
     * ����stlan���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStlan(String value) {
        this.stlan = value;
    }

    /**
     * ��ȡztbsj���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZtbsj() {
        return ztbsj;
    }

    /**
     * ����ztbsj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZtbsj(String value) {
        this.ztbsj = value;
    }

    /**
     * ��ȡzyjsc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZyjsc() {
        return zyjsc;
    }

    /**
     * ����zyjsc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZyjsc(String value) {
        this.zyjsc = value;
    }

}
