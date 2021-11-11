/*
package com.snaker.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.snaker.common.auth.annotation.HasPermissions;
import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;
import com.snaker.common.exception.file.OssException;
import com.snaker.common.utils.ValidatorUtils;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.feign.RemoteFtpService;
import com.snaker.system.domain.SysOss;
import com.snaker.system.oss.CloudConstant;
import com.snaker.system.oss.CloudConstant.CloudService;
import com.snaker.system.oss.CloudStorageConfig;
import com.snaker.system.oss.valdator.AliyunGroup;
import com.snaker.system.oss.valdator.QcloudGroup;
import com.snaker.system.oss.valdator.QiniuGroup;
import com.snaker.system.service.ISysConfigService;
import com.snaker.system.service.ISysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

*/
/**
 * 文件上传 提供者
 *
 * @author snaker
 * @date 2019-05-16
 *//*

@RestController
@RequestMapping("oss")
public class SysOssController extends BaseController {
    private final static String KEY = CloudConstant.CLOUD_STORAGE_CONFIG_KEY;

//    @Autowired
//    private ISysOssService sysOssService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private RemoteFtpService remoteFtpService;

    */
/**
     * 云存储配置信息
     *//*

    @RequestMapping("config")
    @HasPermissions("sys:oss:config")
    public CloudStorageConfig config() {
        String jsonconfig = sysConfigService.selectConfigByKey(CloudConstant.CLOUD_STORAGE_CONFIG_KEY);
        // 获取云存储配置信息
        CloudStorageConfig config = JSON.parseObject(jsonconfig, CloudStorageConfig.class);
        return config;
    }

    */
/**
     * 保存云存储配置信息
     *//*

    @RequestMapping("saveConfig")
    @HasPermissions("sys:oss:config")
    public R saveConfig(CloudStorageConfig config) {
        // 校验类型
        ValidatorUtils.validateEntity(config);
        if (config.getType() == CloudService.QINIU.getValue()) {
            // 校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == CloudService.ALIYUN.getValue()) {
            // 校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == CloudService.QCLOUD.getValue()) {
            // 校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }
        return toAjax(sysConfigService.updateValueByKey(KEY, new Gson().toJson(config)));
    }

    */
/**
     * 查询文件上传
     *//*

    @GetMapping("get/{id}")
    public SysOss get(@PathVariable("id") Long id) {
        return sysOssService.selectSysOssById(id);
    }

    */
/**
     * 查询文件上传列表
     *//*

    @GetMapping("list")
    public R list(SysOss sysOss) {
        startPage();
        return result(sysOssService.selectSysOssList(sysOss));
    }

    */
/**
     * 修改
     *//*

    @PostMapping("update")
    @HasPermissions("sys:oss:edit")
    public R editSave(@RequestBody SysOss sysOss) {
        return toAjax(sysOssService.updateSysOss(sysOss));
    }

    */
/**
     * 修改保存文件上传
     *
     * @throws IOException
     *//*

    @PostMapping("upload")
//    @HasPermissions("sys:oss:add")
    public R editSave(@RequestParam("file") MultipartFile file) throws Throwable {
        if (file.isEmpty()) {
            throw new OssException("上传文件不能为空");
        }
        // 上传文件
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
//        CloudStorageService storage = OSSFactory.build();
//        String url = storage.uploadSuffix(file.getBytes(), suffix);
        R r = remoteFtpService.upload(file,getCurrentUserId(),"405",null,null);
        if (!r.judgeSuccess()) {
            throw new OssException(r.getMsg());
        }
        DfsFile dfsFile = r.getData(DfsFile.class);
        logger.info("[处理一个文件{}]", dfsFile);
        // 保存文件信息
        SysOss ossEntity = new SysOss();
//        ossEntity.setUrl(url);
        ossEntity.setUrl(dfsFile.getUrl());
        ossEntity.setFileSuffix(suffix);
        ossEntity.setCreateBy(getLoginName());
        ossEntity.setFileName(fileName);
        ossEntity.setCreateTime(new Date());
//        ossEntity.setService(storage.getService());
        return toAjax(sysOssService.insertSysOss(ossEntity));
    }

    */
/**
     * 修改保存文件上传
     *
     * @throws IOException
     *//*

    @PostMapping("uploadtt")
//    @HasPermissions("sys:oss:add")
    public R editSavett(@RequestParam("file") MultipartFile file) throws Throwable {
        return remoteFtpService.upload(file,getCurrentUserId(),"404",null,null);
    }
    */
/**
     * 删除文件上传
     *//*

    @PostMapping("remove")
    @HasPermissions("sys:oss:remove")
    public R remove(String ids) {
        return toAjax(sysOssService.deleteSysOssByIds(ids));
    }
}
*/
