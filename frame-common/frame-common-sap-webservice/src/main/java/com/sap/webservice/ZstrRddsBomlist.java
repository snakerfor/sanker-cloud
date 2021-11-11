
package com.sap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZstrRddsBomlist complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ZstrRddsBomlist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Werks" type="{urn:sap-com:document:sap:soap:functions:mc-style}char4"/>
 *         &lt;element name="Stlnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Stlal" type="{urn:sap-com:document:sap:soap:functions:mc-style}char2"/>
 *         &lt;element name="Stlan" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Stlkn" type="{urn:sap-com:document:sap:soap:functions:mc-style}numeric8"/>
 *         &lt;element name="Matnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Maktx" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Idnrk" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Zjktx" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Stkkz" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Andat" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Aedat" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Datuv" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="ValidTo" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Loekz" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Zyjsc" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Ztbsj" type="{urn:sap-com:document:sap:soap:functions:mc-style}char30"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZstrRddsBomlist", propOrder = {
    "werks",
    "stlnr",
    "stlal",
    "stlan",
    "stlkn",
    "matnr",
    "maktx",
    "idnrk",
    "zjktx",
    "stkkz",
    "andat",
    "aedat",
    "datuv",
    "validTo",
    "loekz",
    "zyjsc",
    "ztbsj"
})
public class ZstrRddsBomlist {

    @XmlElement(name = "Werks", required = true)
    protected String werks;
    @XmlElement(name = "Stlnr", required = true)
    protected String stlnr;
    @XmlElement(name = "Stlal", required = true)
    protected String stlal;
    @XmlElement(name = "Stlan", required = true)
    protected String stlan;
    @XmlElement(name = "Stlkn", required = true)
    protected String stlkn;
    @XmlElement(name = "Matnr", required = true)
    protected String matnr;
    @XmlElement(name = "Maktx", required = true)
    protected String maktx;
    @XmlElement(name = "Idnrk", required = true)
    protected String idnrk;
    @XmlElement(name = "Zjktx", required = true)
    protected String zjktx;
    @XmlElement(name = "Stkkz", required = true)
    protected String stkkz;
    @XmlElement(name = "Andat", required = true)
    protected String andat;
    @XmlElement(name = "Aedat", required = true)
    protected String aedat;
    @XmlElement(name = "Datuv", required = true)
    protected String datuv;
    @XmlElement(name = "ValidTo", required = true)
    protected String validTo;
    @XmlElement(name = "Loekz", required = true)
    protected String loekz;
    @XmlElement(name = "Zyjsc", required = true)
    protected String zyjsc;
    @XmlElement(name = "Ztbsj", required = true)
    protected String ztbsj;

    /**
     * 获取werks属性的值。
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
     * 设置werks属性的值。
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
     * 获取stlnr属性的值。
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
     * 设置stlnr属性的值。
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
     * 获取stlal属性的值。
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
     * 设置stlal属性的值。
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
     * 获取stlan属性的值。
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
     * 设置stlan属性的值。
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
     * 获取stlkn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStlkn() {
        return stlkn;
    }

    /**
     * 设置stlkn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStlkn(String value) {
        this.stlkn = value;
    }

    /**
     * 获取matnr属性的值。
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
     * 设置matnr属性的值。
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
     * 获取maktx属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaktx() {
        return maktx;
    }

    /**
     * 设置maktx属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaktx(String value) {
        this.maktx = value;
    }

    /**
     * 获取idnrk属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdnrk() {
        return idnrk;
    }

    /**
     * 设置idnrk属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdnrk(String value) {
        this.idnrk = value;
    }

    /**
     * 获取zjktx属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZjktx() {
        return zjktx;
    }

    /**
     * 设置zjktx属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZjktx(String value) {
        this.zjktx = value;
    }

    /**
     * 获取stkkz属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStkkz() {
        return stkkz;
    }

    /**
     * 设置stkkz属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStkkz(String value) {
        this.stkkz = value;
    }

    /**
     * 获取andat属性的值。
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
     * 设置andat属性的值。
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
     * 获取aedat属性的值。
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
     * 设置aedat属性的值。
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
     * 获取datuv属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatuv() {
        return datuv;
    }

    /**
     * 设置datuv属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatuv(String value) {
        this.datuv = value;
    }

    /**
     * 获取validTo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * 设置validTo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTo(String value) {
        this.validTo = value;
    }

    /**
     * 获取loekz属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoekz() {
        return loekz;
    }

    /**
     * 设置loekz属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoekz(String value) {
        this.loekz = value;
    }

    /**
     * 获取zyjsc属性的值。
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
     * 设置zyjsc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZyjsc(String value) {
        this.zyjsc = value;
    }

    /**
     * 获取ztbsj属性的值。
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
     * 设置ztbsj属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZtbsj(String value) {
        this.ztbsj = value;
    }

}
