
package com.sap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ZstrRddsGylxInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ZstrRddsGylxInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Werks" type="{urn:sap-com:document:sap:soap:functions:mc-style}char4"/>
 *         &lt;element name="Matnr" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="Plnnr" type="{urn:sap-com:document:sap:soap:functions:mc-style}char8"/>
 *         &lt;element name="Plnal" type="{urn:sap-com:document:sap:soap:functions:mc-style}char2"/>
 *         &lt;element name="Plnkn" type="{urn:sap-com:document:sap:soap:functions:mc-style}numeric8"/>
 *         &lt;element name="Zgxbh" type="{urn:sap-com:document:sap:soap:functions:mc-style}char4"/>
 *         &lt;element name="Ltxa1" type="{urn:sap-com:document:sap:soap:functions:mc-style}char40"/>
 *         &lt;element name="Andat" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Aedat" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Datuv" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
 *         &lt;element name="Datub" type="{urn:sap-com:document:sap:soap:functions:mc-style}date10"/>
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
@XmlType(name = "ZstrRddsGylxInfo", propOrder = {
    "gylxId",
    "gylxBh",
    "werks",
    "matnr",
    "plnnr",
    "plnal",
    "plnkn",
    "zgxbh",
    "ltxa1",
    "annam",
    "andat",
    "aedat",
    "datuv",
    "datub",
    "loekz",
    "zyjsc",
    "ztbsj"
})
public class ZstrRddsGylxInfo {

    @XmlElement(name = "GylxId", required = true)
    protected String gylxId;
    @XmlElement(name = "GylxBh", required = true)
    protected String gylxBh;
    @XmlElement(name = "Werks", required = true)
    protected String werks;
    @XmlElement(name = "Matnr", required = true)
    protected String matnr;
    @XmlElement(name = "Plnnr", required = true)
    protected String plnnr;
    @XmlElement(name = "Plnal", required = true)
    protected String plnal;
    @XmlElement(name = "Plnkn", required = true)
    protected String plnkn;
    @XmlElement(name = "Zgxbh", required = true)
    protected String zgxbh;
    @XmlElement(name = "Ltxa1", required = true)
    protected String ltxa1;
    @XmlElement(name = "Annam", required = true)
    protected String annam;
    @XmlElement(name = "Andat", required = true)
    protected String andat;
    @XmlElement(name = "Aedat", required = true)
    protected String aedat;
    @XmlElement(name = "Datuv", required = true)
    protected String datuv;
    @XmlElement(name = "Datub", required = true)
    protected String datub;
    @XmlElement(name = "Loekz", required = true)
    protected String loekz;
    @XmlElement(name = "Zyjsc", required = true)
    protected String zyjsc;
    @XmlElement(name = "Ztbsj", required = true)
    protected String ztbsj;

    /**
     * ��ȡgylxId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGylxId() {
        return gylxId;
    }

    /**
     * ����gylxId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGylxId(String value) {
        this.gylxId = value;
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
     * ��ȡplnkn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlnkn() {
        return plnkn;
    }

    /**
     * ����plnkn���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlnkn(String value) {
        this.plnkn = value;
    }

    /**
     * ��ȡzgxbh���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZgxbh() {
        return zgxbh;
    }

    /**
     * ����zgxbh���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZgxbh(String value) {
        this.zgxbh = value;
    }

    /**
     * ��ȡltxa1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLtxa1() {
        return ltxa1;
    }

    /**
     * ����ltxa1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLtxa1(String value) {
        this.ltxa1 = value;
    }

    /**
     * ��ȡannam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnam() {
        return annam;
    }

    /**
     * ����annam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnam(String value) {
        this.annam = value;
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
     * ��ȡdatub���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatub() {
        return datub;
    }

    /**
     * ����datub���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatub(String value) {
        this.datub = value;
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
