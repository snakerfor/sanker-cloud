package com.snaker.dfs.feign;

import com.snaker.common.constant.ServiceNameConstants;
import com.snaker.common.core.domain.R;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;
import com.snaker.dfs.feign.factory.RemoteCommonFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 Feign服务层
 *
 * @author snaker
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.DFS_SERVICE, fallbackFactory = RemoteCommonFallbackFactory.class)
public interface RemoteCommonService {

    /**
     * 删除文件上传
     */
    @PostMapping("file/remove")
    public R remove(@RequestParam("ids") String ids) throws Throwable;

    /**
     * 获取文件by文件ID
     *
     * @param id
     * @return
     */
    @GetMapping("file/get/{id}")
    public DfsFile get(@PathVariable("id") String id) throws Throwable;

    @GetMapping("file/getZipDfsFile/{id}")
    public R getZipDfsFile(@PathVariable("id") String id) throws Throwable;

    /**
     * 修改文件
     *
     * @param dfsFile
     * @return
     */
    @PostMapping("file/update")
    public R editSave(@RequestBody DfsFile dfsFile) throws Throwable;

    //根据ids 批量查找file
    @PostMapping("file/batchGetByIds")
    public R batchGetByIds(@RequestBody DfsFile dfsFile) throws Throwable;

    @PostMapping("file/batchUpdateDfsFileDelFlagByOldDelFlags")
    public R batchUpdateDfsFileDelFlagByOldDelFlags(@RequestBody DfsFile dfsFile) throws Throwable;

    @PostMapping("file/addZipDfsFile")
    public R addZipDfsFile(@RequestBody ZipDfsFile zipDfsFile) throws Throwable;
}
