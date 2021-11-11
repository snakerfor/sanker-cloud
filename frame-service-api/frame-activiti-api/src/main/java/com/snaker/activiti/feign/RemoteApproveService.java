package com.snaker.activiti.feign;

import com.snaker.activiti.feign.factory.RemoteApproveFallbackFactory;
import com.snaker.common.constant.ServiceNameConstants;
import com.snaker.common.core.domain.R;
import com.snaker.common.core.page.PageDomain;
import com.snaker.common.domain.BizApprove;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 Feign服务层
 * 
 * @author snaker
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.ACT_SERVICE, fallbackFactory = RemoteApproveFallbackFactory.class)
public interface RemoteApproveService
{
    /**
     * 流程发起
     */
    @PostMapping("approve/save")
    public BizApprove sendApprove(@RequestBody BizApprove bizApprove) throws Throwable;    /**
     * 流程发起
     */
    @PostMapping("task/getTasks")
    public R getTaskList(@RequestParam("userId") Long userId) throws Throwable;

}
