package com.snaker.dfs.controller;

import com.snaker.common.core.domain.R;
import com.snaker.common.dfs.util.FtpUtils;
import com.snaker.common.enums.FileTypeEnum;
import com.snaker.common.utils.DateUtils;
import com.snaker.common.utils.ServletUtils;
import com.snaker.common.utils.file.FileUploadUtils;
import com.snaker.common.utils.file.FileUtils;
import com.snaker.dfs.config.DfsConfig;
import com.snaker.dfs.config.FtpConfig;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.service.IDfsFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 通用请求处理（ftp服务器存储）
 *
 * @author sfd
 */
@Slf4j
@RestController
@RequestMapping("service/ftp")
public class FtpController {

    @Autowired
    private FtpConfig ftpConfig;

    @Autowired
    private DfsConfig dfsConfig;

    @Autowired
    private IDfsFileService iDfsFileService;

    public static final String DEL_FLAG_0 = "0";//生效或已审批
    public static final String DEL_FLAG_1 = "1";//删除
    public static final String DEL_FLAG_2 = "2";//待发起

    /**
     * 通用下载请求
     */
    @GetMapping("download/{id}")
    public void download(@PathVariable("id") String id) {
        FtpUtils ftpUtils = null;
        try {
            DfsFile dfsFile = iDfsFileService.selectDfsFileById(id);
            HttpServletResponse response = ServletUtils.getResponse();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(dfsFile.getFileName(), "UTF-8"));
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
            ftpUtils.downloadFile(dfsFile.getUrl(), response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        } finally {
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
        }
    }

    @GetMapping("getPdfByOriginOfficeFileId/{originOfficeFileId}")
    public void getPdfByOriginOfficeFileId(@PathVariable("originOfficeFileId") String originOfficeFileId) throws Exception {
        log.info("[[[开始获取文件的PDF流]]]");
        if (StringUtils.isBlank(originOfficeFileId)) {
            throw new RuntimeException("参数：originOfficeFileId 不得为空!");
        }
        String downloadFileId = null;
        DfsFile dfsFile = iDfsFileService.selectDfsFileById(originOfficeFileId);
        FileTypeEnum fileTypeEnum = dfsFile.getFileTypeEnum();
        if (FileTypeEnum.WORD.equals(fileTypeEnum) || FileTypeEnum.EXCEL.equals(fileTypeEnum) || FileTypeEnum.PPT.equals(fileTypeEnum)) {
            String sessionTempFilePath = FileUtils.getSessionTempFilePath();
            downloadFileId = iDfsFileService.getPdfByOriginOfficeFileId(originOfficeFileId, sessionTempFilePath);
        } else {
            downloadFileId = originOfficeFileId;
        }
        download(downloadFileId);
        log.info("[[[结束获取文件的PDF流]]]");
    }

    @GetMapping("/startBuildPdfFile/{officeFileId}")
    public void startBuildPdfFile(@PathVariable("officeFileId") String officeFileId) throws Exception {
        log.info("[开始生成PDF文件,officeFileId:{}]", officeFileId);
        if (StringUtils.isBlank(officeFileId)) {
            throw new RuntimeException("参数：officeFileId 不得为空!");
        }
        DfsFile dfsFileDB = iDfsFileService.selectDfsFileById(officeFileId);
        if (dfsFileDB == null) throw new RuntimeException(officeFileId + ":文件不存在!");

        if (StringUtils.isNotBlank(dfsFileDB.getPdfId())) {
            log.info("[{}对应的PDF文件存在,pdf文件id为：{},返回]", dfsFileDB.getFileName(), dfsFileDB.getPdfId());
            return;
        }
        FileTypeEnum fileTypeEnum = dfsFileDB.getFileTypeEnum();
        if (FileTypeEnum.WORD.equals(fileTypeEnum) || FileTypeEnum.EXCEL.equals(fileTypeEnum) || FileTypeEnum.PPT.equals(fileTypeEnum)) {
            String sessionTempFilePath = FileUtils.getSessionTempFilePath();
            iDfsFileService.asynStartBuildPdfFile(officeFileId, sessionTempFilePath);
        } else {
            throw new RuntimeException("不支持的操作类型:" + fileTypeEnum.toString());
        }
    }

    /**
     * 通过文件路径下载文件
     *
     * @param url
     */
    @GetMapping("getFileByUrl")
    public void getFileByUrl(@RequestParam("url") String url) throws Exception {
        FtpUtils ftpUtils = null;
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
            ftpUtils.downloadFile(url, response.getOutputStream());
        } finally {
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping(value = "upload", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R upload(MultipartFile file, Long userId, String delFlag, String md5, String id) throws Exception {
        InputStream inputStream = null;
        FtpUtils ftpUtils = null;
        try {
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
            String fileId = UUID.randomUUID().toString().replaceAll("-", "");
            String extensionName = FileUploadUtils.getExtension(file);
            String fileName = fileId + "." + extensionName;
            String ftpPath = ftpUtils.upload(file.getBytes(), fileName);
            //异常情况处理
            if (ftpPath == null || ftpPath.length() < 3) {
                return R.error();
            }
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(id)) {
                DfsFile dfsFile = new DfsFile();
                dfsFile.setId(fileId);
                dfsFile.setFileName(originalFilename.contains("/") ? originalFilename.substring(originalFilename.lastIndexOf("/") + 1) : originalFilename);
                dfsFile.setFileSuffix(extensionName);
                dfsFile.setUrl(ftpPath);
                dfsFile.setDeleteFlag(delFlag);
                dfsFile.setMd5(md5);
                dfsFile.setUpdateTime(DateUtils.getNowDate());
                dfsFile.setUpdateBy(String.valueOf(userId));
                dfsFile.setCreateBy(String.valueOf(userId));
                dfsFile.setVersionNum(1.0);
                iDfsFileService.insertDfsFile(dfsFile);
                return R.ok().data(dfsFile);
            }
            DfsFile oldDfsFile = iDfsFileService.selectDfsFileById(id);

            if (DEL_FLAG_2.equals(oldDfsFile.getDeleteFlag())) {
                //删除
                if (ftpUtils.deleteFile(oldDfsFile.getUrl())) {
                    oldDfsFile.setFileName(originalFilename.contains("/") ? originalFilename.substring(originalFilename.lastIndexOf("/") + 1) : originalFilename);
                    oldDfsFile.setFileSuffix(extensionName);
                    oldDfsFile.setUrl(ftpPath);
                    oldDfsFile.setMd5(md5);
                    oldDfsFile.setUpdateTime(DateUtils.getNowDate());
                    oldDfsFile.setUpdateBy(String.valueOf(userId));
                    oldDfsFile.setVersionNum(oldDfsFile.getVersionNum());
                    iDfsFileService.updateDfsFile(oldDfsFile);
                    iDfsFileService.delDfsFilePdfRef(oldDfsFile.getId());
                } else {
                    throw new RuntimeException("删除旧文件失败");
                }
            }

            if (DEL_FLAG_0.equals(oldDfsFile.getDeleteFlag())) {
                oldDfsFile.setFileName(originalFilename.contains("/") ? originalFilename.substring(originalFilename.lastIndexOf("/") + 1) : originalFilename);
                oldDfsFile.setFileSuffix(extensionName);
                oldDfsFile.setUrl(ftpPath);
                oldDfsFile.setMd5(md5);
                oldDfsFile.setUpdateTime(DateUtils.getNowDate());
                oldDfsFile.setUpdateBy(String.valueOf(userId));
                oldDfsFile.setVersionNum(oldDfsFile.getVersionNum() + 1);
                iDfsFileService.updateDfsFile(oldDfsFile);
                iDfsFileService.delDfsFilePdfRef(oldDfsFile.getId());
            }
            return R.ok().data(oldDfsFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
        }
    }

    @PostMapping(value = "remove", produces = {MediaType.APPLICATION_JSON_VALUE})
    public R remove(String paths) throws Exception {
        FtpUtils ftpUtils = null;
        try {
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
            if (StringUtils.isNotEmpty(paths)) {
                String[] pathArray = paths.split(",");
                for (String path : pathArray) {
                    ftpUtils.deleteFile(path);
                }
            }
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return R.error(e.getMessage());
        } finally {
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
        }
        return R.ok();
    }


}