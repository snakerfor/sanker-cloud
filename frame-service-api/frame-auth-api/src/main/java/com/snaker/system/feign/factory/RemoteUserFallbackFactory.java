package com.snaker.system.feign.factory;

import com.snaker.common.core.domain.R;
import com.snaker.system.feign.RemoteOaWebService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteOaWebService> {
    @Override
    public RemoteOaWebService create(Throwable throwable) {
        log.error("[进入熔断了。。。]");

        return new RemoteOaWebService() {
            @Override
            public R getDataByReturnOrgType(String returnOrgType) throws Throwable {
                throw throwable;
            }
        };
    }
}
