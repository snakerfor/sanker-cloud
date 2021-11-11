package com.snaker.system.controller;

import cn.hutool.core.io.FileUtil;
import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;
import com.snaker.common.core.text.Convert;
import com.snaker.common.pojo.entity.Chunk;
import com.snaker.common.utils.poi.ExcelUtil;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;
import com.snaker.dfs.feign.RemoteCommonService;
import com.snaker.dfs.feign.RemoteFtpService;
import com.snaker.system.service.ISysFileService;
import com.snaker.system.service.ISysOssService;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文件上传 提供者
 *
 * @author snaker
 * @date 2019-05-16
 */
@Slf4j
@RestController
@RequestMapping("file")
public class SysFileController extends BaseController {

    @Autowired
    @Lazy
    private ISysFileService iSysFileService;

//
//    /**
//     * 查询文件上传
//     */
//    @GetMapping("get/{id}")
//    public DfsFile get(@PathVariable("id") String id) throws Throwable {
//        return remoteCommonService.get(id);
//    }
//
//    @GetMapping("/getDfsFile/{id}")
//    public R getDfsFile(@PathVariable("id") String id) throws Throwable {
//        return R.data(remoteCommonService.get(id));
//    }
//
//    @GetMapping("/getZipDfsFile/{id}")
//    public R ZipDfsFile(@PathVariable("id") String id) throws Throwable {
//        return remoteCommonService.getZipDfsFile(id);
//    }
//
//    /**
//     * 修改
//     */
//    @PostMapping("update")
//    public R editSave(@RequestBody DfsFile dfsFile) throws Throwable {
//        return remoteCommonService.editSave(dfsFile);
//    }
//
//    /**
//     * 修改保存文件上传
//     *
//     * @throws IOException
//     */
//    @PostMapping("ftp/upload")
//    public R editSave(@RequestParam("file") MultipartFile file) throws Throwable {
//        return remoteFtpService.upload(file, getCurrentUserId(), "0", null, null);
//    }
//
//
//    /**
//     * o
//     * 文件下载
//     *
//     * @param id       文件ID
//     * @param response
//     */
//    @GetMapping(value = "ftp/download/{id}")
//    public void downloadFile(@PathVariable String id, HttpServletResponse response) throws Throwable {
//        InputStream inputStream = null;
//        BufferedInputStream bufferedInputStream = null;
//        BufferedOutputStream bufferedOutputStream = null;
//        try {
//            Response serviceResponse = remoteFtpService.download(id);
//            Response.Body body = serviceResponse.body();
//            inputStream = body.asInputStream();
//            bufferedInputStream = new BufferedInputStream(inputStream);
//            response.setHeader("Content-Disposition", serviceResponse.headers().get("Content-Disposition").toString().replace("[", "").replace("]", ""));
//            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
//            int length = 0;
//            byte[] temp = new byte[1024 * 10];
//            while ((length = bufferedInputStream.read(temp)) != -1) {
//                bufferedOutputStream.write(temp, 0, length);
//            }
//            bufferedOutputStream.flush();
//            bufferedOutputStream.close();
//            bufferedInputStream.close();
//            inputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bufferedInputStream != null)
//                    bufferedInputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            try {
//                if (bufferedOutputStream != null)
//                    bufferedOutputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            try {
//                if (inputStream != null)
//                    inputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//

    /**
     * 通过文件路径获取文件
     *
     * @param url
     * @return
     */
    @GetMapping("ftp/downloadByPath")
    public void getFileByPath(String path, HttpServletResponse response) throws Throwable {
        iSysFileService.getFileByUrl(path, response);
    }
//
//    /**
//     * 删除文件上传
//     */
//    @PostMapping("remove")
//    public R remove(String ids) throws Throwable {
//        return remoteCommonService.remove(ids);
//    }
//
//    @PostMapping("/batchGetDfsFileByIds")
//    public R batchGetDfsFileByIds(@RequestBody Set<String> fileIds) throws Throwable {
//        DfsFile dfsFile = new DfsFile();
//        dfsFile.setQueryIds(fileIds);
//        return remoteCommonService.batchGetByIds(dfsFile);
//    }
//
//    @GetMapping("/startBuildPdfFile/{officeFileId}")
//    public R startBuildPdfFile(@PathVariable("officeFileId") String officeFileId) throws Throwable {
//        remoteFtpService.startBuildPdfFile(officeFileId);
//        return R.ok();
//    }
//
//    @GetMapping("/showFileByResponse")
//    public void showFileByResponse(HttpServletResponse response, String fileId) throws Throwable {
//        log.info("[开始获取PDF流，接口接收文件id为:{}]", fileId);
//
//        InputStream inputStream = null;
//        BufferedInputStream bufferedInputStream = null;
//        BufferedOutputStream bufferedOutputStream = null;
//        ServletOutputStream responseOutputStream = null;
//        try {
//            response.reset();
//            response.setContentType("application/pdf");
//
//            Response serviceResponse = remoteFtpService.getPdfByOriginOfficeFileId(fileId);
//            Response.Body body = serviceResponse.body();
//            inputStream = body.asInputStream();
//            bufferedInputStream = new BufferedInputStream(inputStream);
//
//            response.setHeader("Content-Disposition", "inline; filename= file");
//            responseOutputStream = response.getOutputStream();
//            bufferedOutputStream = new BufferedOutputStream(responseOutputStream);
//            int length = 0;
//            byte[] temp = new byte[1024 * 10];
//            while ((length = bufferedInputStream.read(temp)) != -1) {
//                bufferedOutputStream.write(temp, 0, length);
//            }
//            bufferedOutputStream.flush();
//        } finally {
//            try {
//                if (responseOutputStream != null)
//                    responseOutputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            try {
//                if (bufferedOutputStream != null)
//                    bufferedOutputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            try {
//                if (bufferedInputStream != null)
//                    bufferedInputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            try {
//                if (inputStream != null)
//                    inputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        log.info("[结束获取PDF文件流!]");
//    }
//
//    /**
//     * 批量保存文档到指定文档类型
//     *
//     * @param request
//     * @param fileCategoryId
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("/batchSaveFile/{fileCategoryId}")
//    public R uploadDir(HttpServletRequest request, @PathVariable("fileCategoryId") String fileCategoryId) throws Throwable {
//        if (StringUtils.isBlank(fileCategoryId)) {
//            throw new RuntimeException("请指定文件类别");
//        }
//        File file = sysOssService.copyDirToTempDir(request);
//        System.out.println("[file.getPath()]" + file.getPath());
//        Map<String, Object> result = iSysFileService.batchSaveFileToDfs(file, getCurrentUserId(), fileCategoryId);
//        return R.data(result);
//    }
//
//    /**
//     * 保存包含源文件的文件（传入的为文件夹）
//     *
//     * @param request
//     * @param fileCategoryId
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("/saveContainSourceFile/{fileCategoryId}")
//    public R saveContainSourceFile(HttpServletRequest request, @PathVariable("fileCategoryId") String fileCategoryId) throws Throwable {
//        if (StringUtils.isBlank(fileCategoryId)) {
//            throw new RuntimeException("请指定文件类别");
//        }
//        File file = sysOssService.copyDirToTempDir(request);
//        Map<String, Object> result = iSysFileService.saveContainSourceFile(file, getCurrentUserId(), fileCategoryId);
//        return R.data(result);
//    }
//
//    /**
//     * 获取文档列表
//     *
//     * @param dfsFile
//     * @return
//     */
//    @GetMapping("categoryFileList")
//    public R getCategoryFileList(DfsFile dfsFile) {
//        startPage();
//        dfsFile.setCreateBy(getCurrentUserIdString());
//        return result(iSysFileService.getCategoryFileList(dfsFile));
//    }
//
//    /**
//     * 修改文档不提交审批
//     *
//     * @param file
//     * @param dfsFile
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("dm/update")
//    public R updateDmFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile, DfsFile dfsFile) throws Throwable {
//        return toAjax(iSysFileService.updateDmFile(file, dfsFile, sourceFile, getCurrentUserIdString()));
//    }
//
//    /**
//     * 修改文档并提交审批
//     *
//     * @param file
//     * @param dfsFile
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("dm/submit")
//    public R sumbit(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "sourceFile", required = false) MultipartFile sourceFile, DfsFile dfsFile) throws Throwable {
//        return toAjax(iSysFileService.sumbitDmFile(file, dfsFile, sourceFile, getCurrentUserId()));
//    }
//
//    /**
//     * 批量提交发起审批
//     *
//     * @param ids
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("dm/sendApprove")
//    public R sendApprove(String ids) throws Throwable {
//        /* 整理审批数据，发送审批请求 by snaker 2020/11/16 16:15 */
//        String[] array = Convert.toStrArray(ids);
//        int success = 0;
//        for (String id : array) {
//            if (iSysFileService.sendApprove(id, getCurrentUserIdString())) {
//                success++;
//            }
//        }
//        return R.ok().put("total", array.length).put("success", success);
//    }
//
//    /**
//     * 获取文档历史版本列表
//     *
//     * @param fileId
//     * @return
//     * @throws Throwable
//     */
//    @GetMapping("dm/fileVersionList")
//    public R getDmFileVersionList(String fileId) throws Throwable {
//        return result(iSysFileService.getDmFileVersionList(fileId));
//    }
//
//    /**
//     * 获取引用文档模块记录列表
//     *
//     * @param fileId
//     * @return
//     * @throws Throwable
//     */
//    @GetMapping("dm/businessFilResList")
//    public R getDmBusinessFilResList(String fileId) throws Throwable {
//        return result(iSysFileService.getDmBusinessFilResList(fileId));
//    }
//
//    /**
//     * 批量导出
//     *
//     * @param dfsFile
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("dm/export")
//    public R export(DfsFile dfsFile) throws Throwable {
//        dfsFile.setCreateBy(getCurrentUserIdString());
//        List<DfsFile> list = iSysFileService.getCategoryFileList(dfsFile);
//        ExcelUtil<DfsFile> util = new ExcelUtil<DfsFile>(DfsFile.class);
//        return util.exportExcel(list, null, "文档数据导出");
//    }
//
//    /**
//     * 批量删除文档 服务器上文件、业务关联关系、历史版本均删除，文档本身状态置为已删除
//     *
//     * @param ids
//     * @return
//     */
//    @PostMapping("dm/remove")
//    public R deleteDmfilesByIds(String ids, String paths) throws Throwable {
//        return toAjax(iSysFileService.deleteDmfilesByIds(ids, paths));
//    }
//
//    /**
//     * 删除文档历史版本
//     *
//     * @param id
//     * @param path
//     * @param fileId
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("dm/file_version/remove")
//    public R deleteDmfileVersion(String id, String path, String sourceFilePath, String fileId) throws Throwable {
//        return toAjax(iSysFileService.deleteDmfileVersion(id, path, sourceFilePath, fileId));
//    }
//
//    /**
//     * 上传源文件
//     *
//     * @param file
//     * @param dfsFile
//     * @return
//     * @throws Throwable
//     */
//    @PostMapping("dm/uploadDmSourceFile")
//    public R uploadDmSourceFile(@RequestParam(value = "file", required = false) MultipartFile file, DfsFile dfsFile) throws Throwable {
//        return toAjax(iSysFileService.uploadDmSourceFile(file, dfsFile, getCurrentUserId()));
//    }
//
//    @PostMapping("/saveDirFile")
//    public R saveDirFile(HttpServletRequest request) throws Throwable {
//        File tempDirFile = null;
//        try {
//            tempDirFile = sysOssService.copyDirToTempDir(request, true);
//            System.out.println("【tempDirFile.getPath()】" + tempDirFile.getPath());
//
//            if (!tempDirFile.isDirectory()) throw new RuntimeException("文件异常");
//
//            File[] files = tempDirFile.listFiles();
//            if (files == null || files.length != 1) throw new RuntimeException("文件异常");
//
//            ZipDfsFile zipDfsFile = iSysFileService.uploadDirFileAndBuildDirTreeAndZipFile(files[0], getCurrentUserId());
//            return R.data(zipDfsFile);
//        } finally {
//            if (tempDirFile != null) {
//                FileUtil.del(tempDirFile);
//            }
//        }
//    }
//
//    @PostMapping("/bigFileUpload")
//    public String bigFileUpload(@ModelAttribute Chunk chunk, HttpServletResponse response) throws Throwable {
//        log.info("【断点续传-文件块信息为:{}】", chunk.toString());
//        return iSysFileService.uploadBigFile(chunk, response);
//    }
}
