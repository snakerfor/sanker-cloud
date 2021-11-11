package com.snaker.dfs.feign.factory;

import com.snaker.common.core.domain.R;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;
import com.snaker.dfs.feign.RemoteCommonService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteCommonFallbackFactory implements FallbackFactory<RemoteCommonService> {/* (non-Javadoc)
 * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
 */

    @Override
    public RemoteCommonService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return new RemoteCommonService() {
            @Override
            public R remove(String ids) throws Throwable {
                throw throwable;
            }

            @Override
            public DfsFile get(String id) throws Throwable {
                throw throwable;
            }

            @Override
            public R getZipDfsFile(String id) throws Throwable {
                throw throwable;
            }

            @Override
            public R editSave(DfsFile dfsFile) throws Throwable {
                throw throwable;
            }

            @Override
            public R batchGetByIds(DfsFile dfsFile) throws Throwable {
                throw throwable;
            }

            @Override
            public R batchUpdateDfsFileDelFlagByOldDelFlags(DfsFile dfsFile) throws Throwable {
                throw throwable;
            }

            @Override
            public R addZipDfsFile(ZipDfsFile zipDfsFile) throws Throwable {
                throw throwable;
            }
        };
    }
}
