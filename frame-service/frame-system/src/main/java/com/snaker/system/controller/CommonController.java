package com.snaker.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.snaker.common.redis.util.RedisUtils;
import com.snaker.common.utils.poi.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.snaker.common.utils.DateUtils;
import com.snaker.common.utils.StringUtils;
import com.snaker.common.utils.ToolUtil;
import com.snaker.common.utils.file.FileUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author sfd
 */
@Controller
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private RedisUtils redisUtils;
    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = DateUtils.dateTimeNow() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = ToolUtil.getDownloadPath() + fileName;
            response.setCharacterEncoding("utf-8");
            // 下载使用"application/octet-stream"更标准
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    // 下载导入错误信息的问题
    @GetMapping(value = "common/download/error")
    public void downloadFile(String key, HttpServletResponse response,HttpServletRequest request) throws Throwable {
        OutputStream outputStream = null;
        try {
            String fileName = "导入错误数据.xlsx";
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename="+FileUtils.setFileDownloadHeader(request, fileName ));
            outputStream = response.getOutputStream();
            // 错误数据从redis获取
            String e = redisUtils.get("import-error-" + key);
            List<List<String>> list = (List<List<String>>) JSONArray.parse(e);
            ExcelUtil<Object> excelUtil = new ExcelUtil<>();
            excelUtil.exportExcel(list, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}