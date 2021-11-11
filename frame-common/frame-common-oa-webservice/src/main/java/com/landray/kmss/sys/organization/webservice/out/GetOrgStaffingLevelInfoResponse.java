
package com.landray.kmss.sys.organization.webservice.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getOrgStaffingLevelInfoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getOrgStaffingLevelInfoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroOrgResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgStaffingLevelInfoResponse", propOrder = {
    "_return"
})
public class GetOrgStaffingLevelInfoResponse {

    @XmlElement(name = "return")
    protected SysSynchroOrgResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link SysSynchroOrgResult }
     *     
     */
    public SysSynchroOrgResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link SysSynchroOrgResult }
     *     
     */
    public void setReturn(SysSynchroOrgResult value) {
        this._return = value;
    }

}
