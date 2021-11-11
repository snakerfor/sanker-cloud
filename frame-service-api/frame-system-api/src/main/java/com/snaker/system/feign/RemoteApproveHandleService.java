package com.snaker.system.feign;

import com.snaker.common.constant.ServiceNameConstants;
import com.snaker.common.core.domain.R;
import com.snaker.common.domain.BizApprove;
import com.snaker.system.domain.SysDept;
import com.snaker.system.feign.factory.RemoteApproveHandleFallbackFactory;
import com.snaker.system.feign.factory.RemoteDeptFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户 Feign服务层
 * 
 * @author snaker
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteApproveHandleFallbackFactory.class)
public interface RemoteApproveHandleService
{
    @PostMapping("approve/handle")
    public R handleApprove(@RequestBody BizApprove bizApprove) throws Throwable;
}
