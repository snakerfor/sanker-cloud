package com.snaker.activiti.feign.factory;

import com.snaker.activiti.feign.RemoteApproveService;
import com.snaker.common.core.domain.R;
import com.snaker.common.core.page.PageDomain;
import com.snaker.common.domain.BizApprove;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Component
public class RemoteApproveFallbackFactory implements FallbackFactory<RemoteApproveService>
{/* (non-Javadoc)
  * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
  */
    @Override
    public RemoteApproveService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteApproveService()
        {
            @Override
            public BizApprove sendApprove(@RequestBody BizApprove bizApprove) throws Throwable{
                throw new Throwable();
            };
            @Override
            public R getTaskList(@RequestParam Long userId) throws Throwable{
                throw new Throwable();
            };
        };
    }
}
