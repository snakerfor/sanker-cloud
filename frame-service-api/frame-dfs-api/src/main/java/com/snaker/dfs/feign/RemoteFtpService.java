package com.snaker.dfs.feign;

import com.snaker.common.constant.ServiceNameConstants;
import com.snaker.common.core.domain.R;
import com.snaker.dfs.feign.factory.RemoteFtpFallbackFactory;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户 Feign服务层
 *
 * @author snaker
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.DFS_SERVICE, fallbackFactory = RemoteFtpFallbackFactory.class)
public interface RemoteFtpService {

    @PostMapping(value = "service/ftp/upload", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R upload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Long userId, @RequestParam("delFlag") String delFlag, @RequestParam("md5") String md5, @RequestParam("id") String id) throws Throwable;

    @PostMapping(value = "service/ftp/remove", produces = {MediaType.APPLICATION_JSON_VALUE})
    public R remove(@RequestParam("paths") String paths) throws Throwable;

    @GetMapping(value = "service/ftp/download/{id}", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public Response download(@PathVariable("id") String id) throws Throwable;

    @GetMapping(value = "service/ftp/getFileByUrl", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public Response getFileByUrl(@RequestParam("url") String url) throws Throwable;

    @GetMapping(value = "service/ftp/getPdfByOriginOfficeFileId/{originOfficeFileId}", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public Response getPdfByOriginOfficeFileId(@PathVariable("originOfficeFileId") String originOfficeFileId) throws Throwable;

    @GetMapping(value = "/service/ftp/startBuildPdfFile/{officeFileId}", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    public Response startBuildPdfFile(@PathVariable("officeFileId") String officeFileId) throws Throwable;

}
