package com.snaker.system.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.snaker.common.constant.ServiceNameConstants;
import com.snaker.system.domain.SysDept;
import com.snaker.system.feign.factory.RemoteDeptFallbackFactory;

/**
 * 用户 Feign服务层
 * 
 * @author snaker
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService
{
    @GetMapping("dept/get/{deptId}")
    public SysDept selectSysDeptByDeptId(@PathVariable("deptId") long deptId);
}
