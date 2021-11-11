package com.snaker.activiti.controller;

import com.google.common.collect.Maps;
import com.snaker.activiti.consts.ActivitiConstant;
import com.snaker.activiti.domain.ActReProcdef;
import com.snaker.activiti.domain.BizBusiness;
import com.snaker.activiti.service.IActReProcdefService;
import com.snaker.activiti.service.IBizApproveService;
import com.snaker.activiti.service.IBizBusinessService;
import com.snaker.common.domain.BizApprove;
import com.snaker.system.domain.SysUser;
import com.snaker.system.feign.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snaker.common.core.domain.R;
import com.snaker.common.core.controller.BaseController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用审批 提供者
 *
 * @author snaker
 * @date 2020-11-13
 */
@RestController
@RequestMapping("approve")
public class BizApproveController extends BaseController
{

	@Autowired
	private IBizApproveService bizApproveService;

	@Autowired
	private IBizBusinessService bizBusinessService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private IActReProcdefService iActReProcdefService;

	/**
	 * 查询通用审批
	 */
	@GetMapping("get/{id}")
	public BizApprove get(@PathVariable("id") String id)
	{
		return bizApproveService.selectBizApproveById(id);

	}

	/**
	 * 查询通用审批列表
	 */
	@GetMapping("list")
	public R list(BizApprove bizApprove)
	{
		startPage();
        return result(bizApproveService.selectBizApproveList(bizApprove));
	}


	/**
	 * 新增保存通用审批
	 */
	@PostMapping("save")
	public BizApprove addSave(@RequestBody BizApprove bizApprove) throws Throwable {
		int index = bizApproveService.insertBizApprove(bizApprove);
		if (index == 1)
		{
			BizBusiness business = initBusiness(bizApprove);
			bizBusinessService.insertBizBusiness(business);
			Map<String, Object> variables = Maps.newHashMap();
			// 这里可以设置各个负责人，key跟模型的代理变量一致
			// variables.put("pm", 1l);
			// variables.put("sup", 1l);
			// variables.put("gm", 1l);
			// variables.put("hr", 1l);
			variables.put("duration", 24l);
			BizBusiness bizBusiness = bizBusinessService.startProcessRe(business, variables);
			bizApprove.setProcInstId(bizBusiness.getProcInstId());
			bizApprove.setStatus(business.getStatus().toString());
		}
		return bizApprove;
	}

	private BizBusiness initBusiness(BizApprove bizApprove)
	{
		BizBusiness business = new BizBusiness();
		business.setTableId(bizApprove.getId());
		business.setProcDefId(bizApprove.getProcDefId());
		business.setTitle(bizApprove.getTitle());
		business.setUserId(Long.valueOf(bizApprove.getCreateBy()));
		SysUser user = remoteUserService.selectSysUserByUserId(business.getUserId());
		business.setApplyer(user.getUserName());
		business.setStatus(ActivitiConstant.STATUS_DEALING);
		business.setResult(ActivitiConstant.RESULT_DEALING);
		business.setApplyTime(new Date());
		ActReProcdef procdef = new ActReProcdef();
		procdef.setId(bizApprove.getProcDefId());
		List<ActReProcdef> actReProcdefs = iActReProcdefService.selectList(procdef);
		if (actReProcdefs.size() > 0) {
		 	business.setProcName(actReProcdefs.get(0).getName());
		}
		return business;
	}

	/**
	 * 修改保存通用审批
	 */
	@PostMapping("update")
	public R editSave(@RequestBody BizApprove bizApprove)
	{
		return toAjax(bizApproveService.updateBizApprove(bizApprove));
	}

	/**
	 * 删除通用审批
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{
		return toAjax(bizApproveService.deleteBizApproveByIds(ids));
	}

	/**
	 * @Description 获取流程内容
	 * @Param [businessKey]
	 * @return com.snaker.common.core.domain.R
	 * @Author snaker
	 * @Date 2020/11/13 14:01
	**/
	@GetMapping("biz/{businessKey}")
	public R biz(@PathVariable("businessKey") String businessKey)
	{
		BizBusiness business = bizBusinessService.selectBizBusinessById(businessKey);
		if (null != business)
		{
			BizApprove bizApprove = bizApproveService.selectBizApproveById(business.getTableId());
			return R.data(bizApprove);
		}
		return R.error("no record");
	}

}
