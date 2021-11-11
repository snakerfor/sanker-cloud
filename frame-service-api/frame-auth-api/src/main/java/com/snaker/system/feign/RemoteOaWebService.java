package com.snaker.system.feign;

import com.snaker.common.constant.ServiceNameConstants;
import com.snaker.common.core.domain.R;
import com.snaker.system.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = ServiceNameConstants.AUTH_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteOaWebService {

    @GetMapping("getDataByReturnOrgType/{returnOrgType}")
    public R getDataByReturnOrgType(@PathVariable("returnOrgType") String returnOrgType) throws Throwable;


}
