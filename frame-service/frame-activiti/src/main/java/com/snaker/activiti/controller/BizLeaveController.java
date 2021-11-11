package com.snaker.activiti.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.snaker.activiti.consts.ActivitiConstant;
import com.snaker.activiti.domain.BizBusiness;
import com.snaker.activiti.domain.BizLeave;
import com.snaker.activiti.service.IBizBusinessService;
import com.snaker.activiti.service.IBizLeaveService;
import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;
import com.snaker.system.domain.SysUser;
import com.snaker.system.feign.RemoteUserService;

/**
 * 请假 提供者
 *
 * @author sfd
 * @date 2020-01-07
 */
@RestController
@RequestMapping("leave")
public class BizLeaveController extends BaseController
{
    @Autowired
    private IBizLeaveService    leaveService;

    @Autowired
    private IBizBusinessService bizBusinessService;

    @Autowired
    private RemoteUserService   remoteUserService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public BizLeave get(@PathVariable("id") String id)
    {
        return leaveService.selectBizLeaveById(id);
    }

    @GetMapping("biz/{businessKey}")
    public R biz(@PathVariable("businessKey") String businessKey)
    {
        BizBusiness business = bizBusinessService.selectBizBusinessById(businessKey);
        if (null != business)
        {
            BizLeave leave = leaveService.selectBizLeaveById(business.getTableId());
            return R.data(leave);
        }
        return R.error("no record");
    }

    /**
     * 查询请假列表
     */
    @GetMapping("list")
    public R list(BizLeave leave)
    {
        startPage();
        return result(leaveService.selectBizLeaveList(leave));
    }

    /**
     * 新增保存请假
     */
    @PostMapping("save")
    public R addSave(@RequestBody BizLeave leave) throws Throwable {
        int index = leaveService.insertBizLeave(leave);
        if (index == 1)
        {
            BizBusiness business = initBusiness(leave);
            bizBusinessService.insertBizBusiness(business);
            Map<String, Object> variables = Maps.newHashMap();
            // 这里可以设置各个负责人，key跟模型的代理变量一致
            // variables.put("pm", 1l);
            // variables.put("sup", 1l);
            // variables.put("gm", 1l);
            // variables.put("hr", 1l);
            variables.put("duration", leave.getDuration());
            bizBusinessService.startProcess(business, variables);
        }
        return toAjax(index);
    }

    /**
     *
     * @param leave
     * @return
     * @author snaker
     */
    private BizBusiness initBusiness(BizLeave leave)
    {
        BizBusiness business = new BizBusiness();
        business.setTableId(leave.getId().toString());
        business.setProcDefId(leave.getProcDefId());
        business.setTitle(leave.getTitle());
        business.setProcName(leave.getProcName());
        long userId = getCurrentUserId();
        business.setUserId(userId);
        SysUser user = remoteUserService.selectSysUserByUserId(userId);
        business.setApplyer(user.getUserName());
        business.setStatus(ActivitiConstant.STATUS_DEALING);
        business.setResult(ActivitiConstant.RESULT_DEALING);
        business.setApplyTime(new Date());
        return business;
    }

    /**
     * 修改保存请假
     */
    @PostMapping("update")
    public R editSave(@RequestBody BizLeave leave)
    {
        return toAjax(leaveService.updateBizLeave(leave));
    }

    /**
     * 删除
     */
    @PostMapping("remove")
    public R remove(String ids)
    {
        return toAjax(leaveService.deleteBizLeaveByIds(ids));
    }
}
