package com.snaker.system.feign.factory;

import com.snaker.common.core.domain.R;
import com.snaker.common.domain.BizApprove;
import com.snaker.system.domain.SysDept;
import com.snaker.system.feign.RemoteApproveHandleService;
import com.snaker.system.feign.RemoteDeptService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Component
public class RemoteApproveHandleFallbackFactory implements FallbackFactory<RemoteApproveHandleService>
{/* (non-Javadoc)
  * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
  */
    @Override
    public RemoteApproveHandleService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteApproveHandleService()
        {
            @Override
            public R handleApprove(@RequestBody BizApprove bizApprove) throws Throwable{
                return null;
            };
        };
    }
}
