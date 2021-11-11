
package com.sap.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZstrRddsBomlist2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ZstrRddsBomlist2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Werks" type="{urn:sap-com:document:sap:soap:functions:mc-style}char4"/>
 *         &lt;element name="Bomno" type="{urn:sap-com:document:sap:soap:functions:mc-style}char11"/>
 *         &lt;element name="Bomid" type="{urn:sap-com:document:sap:soap:functions:mc-style}char25"/>
 *         &lt;element name="Stlnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Stlal" type="{urn:sap-com:document:sap:soap:functions:mc-style}char2"/>
 *         &lt;element name="Stlan" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Stlkn" type="{urn:sap-com:document:sap:soap:functions:mc-style}numeric8"/>
 *         &lt;element name="Matnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Maktx" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Idnrk" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Zjktx" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Stkkz" type="{urn:sap-com:document:sap:soap:functions:mc-style}char1"/>
 *         &lt;element name="Andat" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Aedat" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Datuv" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="ValidTo" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
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
@XmlType(name = "ZstrRddsBomlist2", propOrder = {
    "werks",
    "bomno",
    "bomid",
    "stlnr",
    "stlal",
    "stlan",
    "stlkn",
    "matnr",
    "maktx",
    "bmein",
    "bmeng",
    "idnrk",
    "zjktx",
    "kmpme",
    "kmpmg",
    "stkkz",
    "andat",
    "aedat",
    "datuv",
    "validTo",
    "loekz",
    "zyjsc",
    "ztbsj"
})
public class ZstrRddsBomlist2 {

    @XmlElement(name = "Werks", required = true)
    protected String werks;
    @XmlElement(name = "Bomno", required = true)
    protected String bomno;
    @XmlElement(name = "Bomid", required = true)
    protected String bomid;
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
    @XmlElement(name = "Bmein", required = true)
    protected String bmein;
    @XmlElement(name = "Bmeng", required = true)
    protected BigDecimal bmeng;
    @XmlElement(name = "Idnrk", required = true)
    protected String idnrk;
    @XmlElement(name = "Zjktx", required = true)
    protected String zjktx;
    @XmlElement(name = "Kmpme", required = true)
    protected String kmpme;
    @XmlElement(name = "Kmpmg", required = true)
    protected BigDecimal kmpmg;
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
     * ��ȡbomid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBomid() {
        return bomid;
    }

    /**
     * ����bomid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBomid(String value) {
        this.bomid = value;
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
     * ��ȡstlkn���Ե�ֵ��
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
     * ����stlkn���Ե�ֵ��
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
     * ��ȡmaktx���Ե�ֵ��
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
     * ����maktx���Ե�ֵ��
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
     * ��ȡbmein���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBmein() {
        return bmein;
    }

    /**
     * ����bmein���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBmein(String value) {
        this.bmein = value;
    }
    /**
     * ��ȡbmeng���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBmeng() {
        return bmeng;
    }

    /**
     * ����bmeng���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBmeng(BigDecimal value) {
        this.bmeng = value;
    }
    /**
     * ��ȡidnrk���Ե�ֵ��
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
     * ����idnrk���Ե�ֵ��
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
     * ��ȡzjktx���Ե�ֵ��
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
     * ����zjktx���Ե�ֵ��
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
     * ��ȡkmpme���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKmpme() {
        return kmpme;
    }

    /**
     * ����kmpme���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKmpme(String value) {
        this.kmpme = value;
    }

    /**
     * ��ȡkmpmg���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKmpmg() {
        return kmpmg;
    }

    /**
     * ����kmpmg���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKmpmg(BigDecimal value) {
        this.kmpmg = value;
    }

    /**
     * ��ȡstkkz���Ե�ֵ��
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
     * ����stkkz���Ե�ֵ��
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
     * ��ȡdatuv���Ե�ֵ��
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
     * ����datuv���Ե�ֵ��
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
     * ��ȡvalidTo���Ե�ֵ��
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
     * ����validTo���Ե�ֵ��
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
     * ��ȡloekz���Ե�ֵ��
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
     * ����loekz���Ե�ֵ��
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

}
