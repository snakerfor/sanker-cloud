package com.landray.kmss.sys.organization.webservice.out;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2020-10-13T14:15:46.850+08:00
 * Generated source version: 2.4.2
 * 
 */
@WebService(targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", name = "ISysSynchroGetOrgWebService")
@XmlSeeAlso({ObjectFactory.class})
public interface ISysSynchroGetOrgWebService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUpdatedElementsByToken", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetUpdatedElementsByToken")
    @WebMethod
    @ResponseWrapper(localName = "getUpdatedElementsByTokenResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetUpdatedElementsByTokenResponse")
    public SysSynchroOrgTokenResult getUpdatedElementsByToken(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoTokenContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRoleConfCateInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleConfCateInfo")
    @WebMethod
    @ResponseWrapper(localName = "getRoleConfCateInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleConfCateInfoResponse")
    public SysSynchroOrgResult getRoleConfCateInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRoleConfInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleConfInfo")
    @WebMethod
    @ResponseWrapper(localName = "getRoleConfInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleConfInfoResponse")
    public SysSynchroOrgResult getRoleConfInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUpdatedElements", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetUpdatedElements")
    @WebMethod
    @ResponseWrapper(localName = "getUpdatedElementsResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetUpdatedElementsResponse")
    public SysSynchroOrgResult getUpdatedElements(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getElementsBaseInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetElementsBaseInfo")
    @WebMethod
    @ResponseWrapper(localName = "getElementsBaseInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetElementsBaseInfoResponse")
    public SysSynchroOrgResult getElementsBaseInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgBaseInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRoleInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleInfo")
    @WebMethod
    @ResponseWrapper(localName = "getRoleInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleInfoResponse")
    public SysSynchroOrgResult getRoleInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRoleLineDefaultRoleInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleLineDefaultRoleInfo")
    @WebMethod
    @ResponseWrapper(localName = "getRoleLineDefaultRoleInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleLineDefaultRoleInfoResponse")
    public SysSynchroOrgResult getRoleLineDefaultRoleInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getRoleLineInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleLineInfo")
    @WebMethod
    @ResponseWrapper(localName = "getRoleLineInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetRoleLineInfoResponse")
    public SysSynchroOrgResult getRoleLineInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getOrgGroupCateInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetOrgGroupCateInfo")
    @WebMethod
    @ResponseWrapper(localName = "getOrgGroupCateInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetOrgGroupCateInfoResponse")
    public SysSynchroOrgResult getOrgGroupCateInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getOrgStaffingLevelInfo", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetOrgStaffingLevelInfo")
    @WebMethod
    @ResponseWrapper(localName = "getOrgStaffingLevelInfoResponse", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/", className = "com.landray.kmss.sys.organization.webservice.out.GetOrgStaffingLevelInfoResponse")
    public SysSynchroOrgResult getOrgStaffingLevelInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        SysSynchroGetOrgInfoContext arg0
    ) throws Exception_Exception;
}
