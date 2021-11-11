package com.snaker.dfs.feign.factory;

import com.snaker.common.core.domain.R;
import com.snaker.dfs.feign.RemoteFtpService;
import feign.Response;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class RemoteFtpFallbackFactory implements FallbackFactory<RemoteFtpService> {/* (non-Javadoc)
 * @see feign.hystrix.FallbackFactory#create(java.lang.Throwable)
 */

    @Override
    public RemoteFtpService create(Throwable throwable) {
        log.error("调用文件服务失败{}",throwable.getMessage());
        return new RemoteFtpService() {
            @Override
            public R upload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Long userId, @RequestParam("delFlag") String delFlag, @RequestParam("md5") String md5, @RequestParam("id") String id) throws Throwable {
                throw throwable;
            }

            @Override
            public R remove(@RequestParam("path") String path) throws Throwable {
                throw throwable;
            }

            @Override
            public Response download(String id) throws Throwable {
                throw throwable;
            }

            @Override
            public Response getFileByUrl(String url) throws Throwable{
                throw throwable;
            }

            public Response getPdfByOriginOfficeFileId(String originOfficeFileId) throws Throwable {
                throw throwable;
            }

            @Override
            public Response startBuildPdfFile(String id) throws Throwable {
                throw throwable;
            }
        };
    }
}
