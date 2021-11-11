package com.snaker.system.service.impl;

import com.snaker.common.redis.annotation.RedisCache;
import com.snaker.common.utils.StringUtils;
import com.snaker.common.utils.poi.ExcelUtil;
import com.snaker.dfs.feign.RemoteCommonService;
import com.snaker.dfs.feign.RemoteFtpService;
import com.snaker.system.service.*;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class SysFileServiceImpl implements ISysFileService {

    private static final Logger log = LoggerFactory.getLogger(SysFileServiceImpl.class);

    @Autowired
    private RemoteCommonService remoteCommonService;

    @Autowired
    private RemoteFtpService remoteFtpService;

    @Autowired
    @Lazy
    private ISysUserService sysUserService;


    public static final String DM_APPROVE_KEY = "rdds:dm:approve";
    public static final String VISIBLE_0 = "0";//显示有效
    public static final String VISIBLE_1 = "1";//隐藏无效

    public static final String DEL_FLAG_0 = "0";//生效或已审批
    public static final String DEL_FLAG_1 = "1";//删除
    public static final String DEL_FLAG_2 = "2";//待发起
    public static final String DEL_FLAG_3 = "3";//待发起

    public static final String DM_APPROVAL_PROCESS_KEY = "dmApprovalProcessId";//文档审批流程ID

    @Override
    public void getFileByUrl(String url, HttpServletResponse response) throws Throwable {
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            Response serviceResponse = remoteFtpService.getFileByUrl(url);
            Response.Body body = serviceResponse.body();
            inputStream = body.asInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", serviceResponse.headers().get("Content-Disposition").toString().replace("[", "").replace("]", ""));
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            int length = 0;
            byte[] temp = new byte[1024 * 10];
            while ((length = bufferedInputStream.read(temp)) != -1) {
                bufferedOutputStream.write(temp, 0, length);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("文件下载失败", e);
        } finally {
            try {
                if (bufferedInputStream != null)
                    bufferedInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    @RedisCache(key = "excel_temp", fieldKey = "#path", expired = 60 * 60 * 24 * 7)
    public List<String> readExcelTemplate(String path) {
        List<String> rs = null;
        InputStream headStream = null;
        try {
            headStream = remoteFtpService.getFileByUrl(path).body().asInputStream();
            ExcelUtil<Object> excelUtil = new ExcelUtil<>();
            List<String> head = excelUtil.importExcel(headStream).get(0);
            if (!head.isEmpty()) {
//                rs = StringUtils.join(head.toArray(), ",");
                return head;
            }
        } catch (Throwable t) {
            log.error("导入模板获取失败", t);
        } finally {
            try {
                if (headStream != null) {
                    headStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

//    @Override
//    public List<DfsFile> batchGetDfsFileByFileIds(Set<String> fileIds) throws Throwable {
//        DfsFile dfsFile = new DfsFile();
//        dfsFile.setQueryIds(fileIds);
//        R r = remoteCommonService.batchGetByIds(dfsFile);
//        return r.getListData(DfsFile.class);
//    }
//
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Map<String, Object> batchSaveFileToDfs(File file, long currentUserId, String fileCategoryId) throws Throwable {
//        Map<String, Object> result = new HashMap<>();
//        List<DfsFile> successList = new ArrayList<>();
//        List<DfsFile> failList = new ArrayList<>();
////        StringBuilder failMark = new StringBuilder("");
//        List<DfsFile> newDfsFiles = new ArrayList<>();
//        File[] files = null;
//        SysUser user = sysUserService.selectUserById(Long.valueOf(currentUserId));
//        try {
//            files = file.listFiles();
//            for (File fileItem : files) {
//                FileInputStream fileInputStreamItem = null;
//                FileInputStream fileInputStreamItem2 = null;
//                try {
//                    fileInputStreamItem = new FileInputStream(fileItem);
//                    String md5 = DigestUtils.md5Hex(fileInputStreamItem);
//                    //查询系统中是否已存在
//                    DfsFile df = dfsFileExtendService.selectDfsFileByMd5(md5);
//                    if (null != df) {
//                        failList.add(df);
////                        failMark.append("【" + df.getFileName() + "】已存在于【" + df.getFileCategoryName() + "】类型下;");
//                        continue;
//                    }
//                    String delFlag = DEL_FLAG_0;
//                    SysMenu sysMenu = sysMenuService.selectMenuByPerms(DM_APPROVE_KEY);
//                    if (sysMenu != null && VISIBLE_0.equals(sysMenu.getVisible())) {
//                        delFlag = DEL_FLAG_2;
//                    }
//                    fileInputStreamItem2 = new FileInputStream(fileItem);
//                    MultipartFile multipartFile = new MultipartFileDto("file", fileItem.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStreamItem2);
//                    R r = remoteFtpService.upload(multipartFile, currentUserId, delFlag, md5, null);
//                    if (!r.judgeSuccess()) {
//                        throw new RuntimeException(r.getMsg());
//                    }
//                    DfsFile dfsFile = r.getData(DfsFile.class);
//                    log.info("[上传完毕一个附件{}]", dfsFile.toString());
//                    dfsFile.setAutor(user.getUserName());
//                    successList.add(dfsFile);
//                    newDfsFiles.add(dfsFile);
//                } finally {
//                    if (fileInputStreamItem != null) {
//                        fileInputStreamItem.close();
//                    }
//                    if (fileInputStreamItem2 != null) {
//                        fileInputStreamItem2.close();
//                    }
//                }
//            }
//        } finally {
//            FileUtil.del(file);
//        }
//        if (CollectionUtils.isNotEmpty(newDfsFiles)) {
//            batchSaveFileToDBWithoutTransactional(newDfsFiles, fileCategoryId);
//        }
//        result.put("successList", successList);
//        result.put("failList", failList);
//        List<DfsFile> list = new ArrayList<>();
//        list.addAll(successList);
//        list.addAll(failList);
//        result.put("list", list);
////        result.put("message",(successList.size() > 0 ? "成功上传" + successList.size() + "个文件;":"") + failMark.toString());
//        return result;
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Map<String, Object> batchSaveFileToDfs(List<File> files, long currentUserId, String fileCategoryId) throws Throwable {
//        return batchSaveFileToDfsWithoutTransactional(files, currentUserId, fileCategoryId);
//    }
//
//    @Override
//    public Map<String, Object> batchSaveFileToDfsWithoutTransactional(List<File> files, long currentUserId, String fileCategoryId) throws Throwable {
//        Map<String, Object> result = new HashMap<>();
//        List<DfsFile> successList = new ArrayList<>();
//        List<DfsFile> failList = new ArrayList<>();
////        StringBuilder failMark = new StringBuilder("");
//        List<DfsFile> newDfsFiles = new ArrayList<>();
//        SysUser user = sysUserService.selectUserById(Long.valueOf(currentUserId));
//        try {
//            for (File fileItem : files) {
//                FileInputStream fileInputStreamItem = null;
//                FileInputStream fileInputStreamItem2 = null;
//                try {
//                    fileInputStreamItem = new FileInputStream(fileItem);
//                    String md5 = DigestUtils.md5Hex(fileInputStreamItem);
//                    //查询系统中是否已存在
//                    DfsFile df = dfsFileExtendService.selectDfsFileByMd5(md5);
//                    if (null != df) {
//                        failList.add(df);
////                        failMark.append("【" + df.getFileName() + "】已存在于【" + df.getFileCategoryName() + "】类型下;");
//                        continue;
//                    }
//                    String delFlag = DEL_FLAG_0;
////                    SysMenu sysMenu = sysMenuService.selectMenuByPerms(DM_APPROVE_KEY);
////                    if (sysMenu != null && VISIBLE_0.equals(sysMenu.getVisible())) {
////                        delFlag = DEL_FLAG_2;
////                    }
//                    fileInputStreamItem2 = new FileInputStream(fileItem);
//                    MultipartFile multipartFile = new MultipartFileDto("file", fileItem.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStreamItem2);
//                    R r = remoteFtpService.upload(multipartFile, currentUserId, delFlag, md5, null);
//                    if (!r.judgeSuccess()) {
//                        throw new RuntimeException(r.getMsg());
//                    }
//                    DfsFile dfsFile = r.getData(DfsFile.class);
//                    log.info("[上传完毕一个附件{}]", dfsFile.toString());
//                    dfsFile.setAutor(user.getUserName());
//                    successList.add(dfsFile);
//                    newDfsFiles.add(dfsFile);
//                } finally {
//                    if (fileInputStreamItem != null) {
//                        fileInputStreamItem.close();
//                    }
//                    if (fileInputStreamItem2 != null) {
//                        fileInputStreamItem2.close();
//                    }
//                }
//            }
//        } finally {
//            // 全部删除
//            for (File file : files) {
//                FileUtil.del(file);
//            }
//        }
//        if (CollectionUtils.isNotEmpty(newDfsFiles)) {
//            batchSaveFileToDBWithoutTransactional(newDfsFiles, fileCategoryId);
//        }
//        result.put("successList", successList);
//        result.put("failList", failList);
//        List<DfsFile> list = new ArrayList<>();
//        list.addAll(successList);
//        list.addAll(failList);
//        result.put("list", list);
////        result.put("message",(successList.size() > 0 ? "成功上传" + successList.size() + "个文件;":"") + failMark.toString());
//        return result;
//    }
//
//    @Override
//    public void batchSaveFileToDBWithoutTransactional(List<DfsFile> dfsFiles, String fileCategoryId) {
//        DfsFileCategory dfsFileCategory = dfsFileCategoryService.selectDfsFileCategoryById(fileCategoryId);
//        if (dfsFileCategory == null && dfsFileCategory.getDelFlag().intValue() == 1) {
//            throw new RuntimeException("文件类别不存在或已被删除!");
//        }
//        String ancestors = dfsFileCategory.getAncestors() + "," + dfsFileCategory.getId();
//        List<DfsFileExtend> insertDfsFileExtends = new ArrayList<>();
//        for (DfsFile dfsFile : dfsFiles) {
//            DfsFileExtend dfsFileExtendItem = new DfsFileExtend();
//            dfsFileExtendItem.setId(dfsFile.getId());
//            dfsFileExtendItem.setDfsFileCategoryId(fileCategoryId);
//            dfsFileExtendItem.setAncestors(ancestors);
//            dfsFileExtendItem.setAutor(dfsFile.getAutor());
//            dfsFileExtendItem.setTitle(dfsFile.getFileName().substring(0, dfsFile.getFileName().lastIndexOf(".")));
//            insertDfsFileExtends.add(dfsFileExtendItem);
//        }
//
//        if (CollectionUtils.isNotEmpty(insertDfsFileExtends)) {
//            dfsFileExtendService.batchInsertDfsFileExtends(insertDfsFileExtends);
//        }
//    }
//
//    @Override
//    public void batchSaveFileToDBWithoutTransactional(Set<String> fileIdAndNames, String fileCategoryId, String currentUserId) {
//        DfsFileCategory dfsFileCategory = dfsFileCategoryService.selectDfsFileCategoryById(fileCategoryId);
//        if (dfsFileCategory == null && dfsFileCategory.getDelFlag().intValue() == 1) {
//            throw new RuntimeException("文件类别不存在或已被删除!");
//        }
//        String ancestors = dfsFileCategory.getAncestors() + "," + dfsFileCategory.getId();
//        List<DfsFileExtend> insertDfsFileExtends = new ArrayList<>();
//        SysUser user = sysUserService.selectUserById(Long.valueOf(currentUserId));
//        for (String fileIdAndName : fileIdAndNames) {
//            DfsFileExtend dfsFileExtendItem = new DfsFileExtend();
//            dfsFileExtendItem.setId(fileIdAndName.split(",")[0]);
//            dfsFileExtendItem.setTitle(fileIdAndName.split(",")[1]);
//            dfsFileExtendItem.setDfsFileCategoryId(fileCategoryId);
//            dfsFileExtendItem.setAncestors(ancestors);
//            dfsFileExtendItem.setAutor(user.getUserName());
//            insertDfsFileExtends.add(dfsFileExtendItem);
//        }
//
//        if (CollectionUtils.isNotEmpty(insertDfsFileExtends)) {
//            dfsFileExtendService.batchInsertDfsFileExtends(insertDfsFileExtends);
//        }
//    }
//
//    /**
//     * 修改文档不提交审批
//     *
//     * @param dfsFile
//     * @return
//     */
//    @Override
//    public List<DfsFile> getCategoryFileList(DfsFile dfsFile) {
//        return dfsFileExtendService.getCategoryFileList(dfsFile);
//    }
//
//    /**
//     * 修改文档不提交审批
//     *
//     * @param file
//     * @param dfsFile
//     * @param sourceFile
//     * @param currentUserIdString
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    public int updateDmFile(MultipartFile file, DfsFile dfsFile, MultipartFile sourceFile, String currentUserIdString) throws Throwable {
//        if (file != null) {
//            if (sourceFile != null) {
//                String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
//                String sourceFileName = sourceFile.getOriginalFilename().substring(0, sourceFile.getOriginalFilename().lastIndexOf("."));
//                if (!fileName.equals(sourceFileName)) {
//                    throw new RuntimeException("pdf文件和源文件名称不一致，请修正后重新上传");
//                }
//            }
//            String md5 = DigestUtils.md5Hex(file.getInputStream());
//            //查询系统中是否已存在
//            DfsFile df = dfsFileExtendService.selectDfsFileByMd5(md5);
//            if (null != df) {
//                throw new RuntimeException("此文件系统中已存在");
//            }
//            DfsFile oldDfsFile = dfsFileExtendService.selectDfsFileById(dfsFile.getId());
//            //状态为待提交时，应删除旧的源文件
//            if (DEL_FLAG_2.equals(oldDfsFile.getDeleteFlag())) {
//                remoteFtpService.remove(oldDfsFile.getSourceFilePath());
//                dfsFileExtendService.delDfsFile(oldDfsFile.getSourceFileId());
//            }
//            R r = remoteFtpService.upload(file, Long.valueOf(currentUserIdString), null, md5, dfsFile.getId());
//            if (!r.judgeSuccess()) {
//                throw new RuntimeException(r.getMsg());
//            }
//            DfsFile newDfsFile = r.getData(DfsFile.class);
//            //上传新源文件
//            if (sourceFile != null) {
//                md5 = DigestUtils.md5Hex(sourceFile.getInputStream());
//                DfsFile sf = dfsFileExtendService.selectOnlyDfsFileByMd5(md5);
//                DfsFile tmf = new DfsFile();
//                tmf.setId(oldDfsFile.getId());
//                if (sf != null) {
//                    tmf.setSourceFileId(sf.getId());
//                } else {
//                    //上传新源文件
//                    r = remoteFtpService.upload(sourceFile, Long.valueOf(currentUserIdString), DEL_FLAG_0, md5, null);
//                    if (!r.judgeSuccess()) {
//                        throw new RuntimeException(r.getMsg());
//                    }
//                    DfsFile sourceDfsFile = r.getData(DfsFile.class);
//                    tmf.setSourceFileId(sourceDfsFile.getId());
//                }
//                dfsFileExtendService.updateDfsFile(tmf);
//            }
//            if (DEL_FLAG_0.equals(oldDfsFile.getDeleteFlag())) {
//                //版本记录
//                DfsFileVersion dfsFileVersion = new DfsFileVersion();
//                dfsFileVersion.setId(IdUtill.getNextId("dmv"));
//                dfsFileVersion.setFileId(oldDfsFile.getId());
//                dfsFileVersion.setAutor(oldDfsFile.getAutor());
//                dfsFileVersion.setTitle(oldDfsFile.getTitle());
//                dfsFileVersion.setFileName(oldDfsFile.getFileName());
//                dfsFileVersion.setUrl(oldDfsFile.getUrl());
//                dfsFileVersion.setVersionNum(oldDfsFile.getVersionNum());
//                dfsFileVersion.setNewFileName(newDfsFile.getFileName());
//                dfsFileVersion.setNewFileSuffix(newDfsFile.getFileSuffix());
//                dfsFileVersion.setNewUrl(newDfsFile.getUrl());
//                dfsFileVersion.setCreateBy(newDfsFile.getCreateBy());
//                dfsFileVersion.setCreateTime(DateUtils.getNowDate());
//                dfsFileVersion.setOldFileId(oldDfsFile.getId());
//                dfsFileVersion.setSourceFileId(oldDfsFile.getSourceFileId());
//                dfsFileExtendService.insertDfsFileVersion(dfsFileVersion);
//                //将旧版本文件记录到文件表中
//                oldDfsFile.setId(IdUtill.getNextId("dm"));
//                dfsFileExtendService.insertDfsFile(oldDfsFile);
//            }
//        }
//        //修改扩展表
//        DfsFileExtend dfsFileExtend = dfsFileExtendService.selectDfsFileExtendById(dfsFile.getId());
//        dfsFileExtend.setTitle(dfsFile.getTitle());
//        dfsFileExtend.setAutor(dfsFile.getAutor());
//        dfsFileExtendService.updateDfsFileExtend(dfsFileExtend);
//        return 1;
//    }
//
//    /**
//     * 修改文档并提交审批
//     *
//     * @param file
//     * @param dfsFile
//     * @param sourceFile
//     * @param currentUserId
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    public int sumbitDmFile(MultipartFile file, DfsFile dfsFile, MultipartFile sourceFile, long currentUserId) throws Throwable {
//        DfsFile oldDfsFile = dfsFileExtendService.selectDfsFileById(dfsFile.getId());
//        if (DEL_FLAG_2.equals(oldDfsFile.getDeleteFlag())) {
//            //初始状态为待发起时，先修改文档信息再提交发起审批流程
//            updateDmFile(file, dfsFile, sourceFile, String.valueOf(currentUserId));
//            sendApprove(dfsFile.getId(), String.valueOf(currentUserId));
//        }
//        if (DEL_FLAG_0.equals(oldDfsFile.getDeleteFlag())) {
//            //二次审批 先上传新附件到服务器再发起审批流程
//            if (file == null) {
//                throw new RuntimeException("附件未有变动");
//            }
//            //上传新附件
//            String md5 = DigestUtils.md5Hex(file.getInputStream());
//            //查询系统中是否已存在
//            DfsFile df = dfsFileExtendService.selectDfsFileByMd5(md5);
//            if (null != df) {
//                throw new RuntimeException("此文件系统中已存在");
//            }
//            R r = remoteFtpService.upload(file, currentUserId, DEL_FLAG_2, md5, null);
//            if (!r.judgeSuccess()) {
//                throw new RuntimeException(r.getMsg());
//            }
//            DfsFile newDfsFile = r.getData(DfsFile.class);
//            //上传源文件
//            if (sourceFile != null) {
//                md5 = DigestUtils.md5Hex(sourceFile.getInputStream());
//                DfsFile sf = dfsFileExtendService.selectOnlyDfsFileByMd5(md5);
//                if (sf != null) {
//                    newDfsFile.setSourceFileId(sf.getId());
//                } else {
//                    //上传新源文件
//                    r = remoteFtpService.upload(sourceFile, currentUserId, DEL_FLAG_0, md5, null);
//                    if (!r.judgeSuccess()) {
//                        throw new RuntimeException(r.getMsg());
//                    }
//                    DfsFile sourceDfsFile = r.getData(DfsFile.class);
//                    newDfsFile.setSourceFileId(sourceDfsFile.getId());
//                }
//            }
//            newDfsFile.setTitle(dfsFile.getTitle());
//            newDfsFile.setAutor(dfsFile.getAutor());
//            newDfsFile.getDfsFiles().add(newDfsFile);
//            sendApproveAgain(oldDfsFile, newDfsFile, String.valueOf(currentUserId));
//        }
//        return 1;
//    }
//
//    /**
//     * 二次审批发起审批流程
//     *
//     * @param oldDfsFile
//     * @param newDfsFile
//     * @param userId
//     * @return
//     */
//    private Boolean sendApproveAgain(DfsFile oldDfsFile, DfsFile newDfsFile, String userId) throws Throwable {
//        newDfsFile.setBeforFileId(oldDfsFile.getId());
//        String needUpdateData = JSONObject.toJSONString(newDfsFile);
//        oldDfsFile.getDfsFiles().add(oldDfsFile);
//        ConvertUtil convertUtil = new ConvertUtil(DfsFile.class, new String[]{"fileCategoryName", "title", "autor", "versionNum"});
//        List data = convertUtil.getApproveDataNoString(oldDfsFile, null, true);
//        Map<String, Object> record = new HashMap<String, Object>();
//        record.put("base", data);
//        ConvertUtil convertUtil1 = new ConvertUtil(DfsFile.class, new String[]{}, new String[]{"fileCategoryName", "versionNum", "deleteFlag", "createBy", "updateBy", "createTime", "updateTime"});
//        List changeData = convertUtil1.getChangeData(newDfsFile, oldDfsFile, null, true);
//        record.put("change", changeData);
//        BizApprove bizApprove = new BizApprove();
//        String dmApprovalProcessId = iSysConfigService.selectConfigByKey(DM_APPROVAL_PROCESS_KEY);
//        if (org.apache.commons.lang3.StringUtils.isBlank(dmApprovalProcessId)) {
//            throw new RuoyiException("获取的流程id空");
//        }
//        bizApprove.setProcDefId(dmApprovalProcessId);
//        bizApprove.setTitle("文档审批流程");
//        bizApprove.setRecord(JSON.toJSONString(record));
//        bizApprove.setCreateBy(String.valueOf(userId));
//        BizApprove biz = remoteApproveService.sendApprove(bizApprove);
//        /* 发送审批成功后 返回数据存储，配置审批流程状态变化的回调类  by snaker 2020/11/16 16:15 */
//        SysApprove sysApprove = new SysApprove();
//        sysApprove.setService("sysFileServiceImpl");
//        sysApprove.setProcDefId(bizApprove.getProcDefId());
//        sysApprove.setProcInstId(biz.getProcInstId());
//        sysApprove.setData(needUpdateData);
//        iSysApproveService.insertSysApprove(sysApprove);
//        DfsFile dfsFiletm = new DfsFile();
//        dfsFiletm.setId(oldDfsFile.getId());
//        dfsFiletm.setMaintainerId(Long.valueOf(userId));
//        SysUser user = sysUserService.selectUserById(Long.valueOf(userId));
//        dfsFiletm.setMaintainerName(user.getUserName());
//        dfsFileExtendService.updateDfsFile(dfsFiletm);
//        return true;
//    }
//
//    /**
//     * 待发起状态，一次提交审批流程
//     *
//     * @param id
//     * @param userId
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    @Transactional(rollbackFor = Throwable.class)
//    public Boolean sendApprove(String id, String userId) throws Throwable {
//        DfsFile dfsFile = dfsFileExtendService.selectDfsFileById(id);
//        dfsFile.getDfsFiles().add(dfsFile);
//        ConvertUtil convertUtil = new ConvertUtil(DfsFile.class);
//        String data = convertUtil.getApproveDataAgain(dfsFile, null, false);
//        BizApprove bizApprove = new BizApprove();
//        String dmApprovalProcessId = iSysConfigService.selectConfigByKey(DM_APPROVAL_PROCESS_KEY);
//        if (org.apache.commons.lang3.StringUtils.isBlank(dmApprovalProcessId)) {
//            throw new RuoyiException("获取的流程id空");
//        }
//        bizApprove.setProcDefId(dmApprovalProcessId);
//        bizApprove.setTitle("文档审批流程");
//        bizApprove.setRecord(data);
//        bizApprove.setCreateBy(userId);
//        BizApprove biz = remoteApproveService.sendApprove(bizApprove);
//        /* 发送审批成功后 返回数据存储，配置审批流程状态变化的回调类  by snaker 2020/11/16 16:15 */
//        SysApprove sysApprove = new SysApprove();
//        sysApprove.setService("sysFileServiceImpl");
//        sysApprove.setProcDefId(bizApprove.getProcDefId());
//        sysApprove.setProcInstId(biz.getProcInstId());
//        sysApprove.setRecordId(dfsFile.getId());
//        iSysApproveService.insertSysApprove(sysApprove);
//        /* 修改业务数据状态为审核中 by snaker 2020/11/16 11:59 */
//        DfsFile dfsFiletm = new DfsFile();
//        dfsFiletm.setId(dfsFile.getId());
//        dfsFiletm.setDeleteFlag("3");
//        dfsFileExtendService.updateDfsFile(dfsFiletm);
//        return true;
//    }
//
//    @Override
//    public int handleApprove(SysApprove sysApprove) {
//        /* 流程状态：1-处理中，2-结束，3-已撤回，4-已中止
//		    审批状态: 1-处理中，2-通过，3-驳回，4-撤回，5-删除，6-中止
//	    // 接收审批服务提供的审批状态
//        // 接收审批数据ID+审批状态，更新数据状态
//        // 修改数据状态
//         */
//        String id = sysApprove.getRecordId();
//        if (StringUtils.isNotEmpty(id)) {
//            DfsFile dfsFile = dfsFileExtendService.selectDfsFileById(id);
//            if (ApproveConstant.STATUS_FINISH == Integer.parseInt(sysApprove.getStatus())) {
//                if (ApproveConstant.RESULT_PASS == Integer.parseInt(sysApprove.getResult())) {
//                    dfsFile.setDeleteFlag(DEL_FLAG_0);
//                    dfsFile.setUpdateTime(new Date());
//                    return dfsFileExtendService.updateDfsFile(dfsFile);
//                }
//                if (ApproveConstant.RESULT_FAIL == Integer.parseInt(sysApprove.getResult()) ||
//                        ApproveConstant.RESULT_CANCELED == Integer.parseInt(sysApprove.getResult()) ||
//                        ApproveConstant.RESULT_DELETED == Integer.parseInt(sysApprove.getResult())) {
//                    dfsFile.setDeleteFlag(DEL_FLAG_2);
//                    return dfsFileExtendService.updateDfsFile(dfsFile);
//                }
//            }
//            if (ApproveConstant.STATUS_CANCELED == Integer.parseInt(sysApprove.getStatus()) ||
//                    ApproveConstant.STATUS_SUSPEND == Integer.parseInt(sysApprove.getStatus())) {
//                dfsFile.setDeleteFlag(DEL_FLAG_2);
//                return dfsFileExtendService.updateDfsFile(dfsFile);
//            }
//            return 1;
//        }
//        DfsFile dfsFile = JSONObject.parseObject(sysApprove.getData(), DfsFile.class);
//        String beforId = dfsFile.getBeforFileId();
//        DfsFile oldDfsFile = dfsFileExtendService.selectDfsFileById(beforId);
//        if (ApproveConstant.STATUS_FINISH == Integer.parseInt(sysApprove.getStatus())) {
//            if (ApproveConstant.RESULT_PASS == Integer.parseInt(sysApprove.getResult())) {
//                //将旧版本文件记录到文件表中
//                DfsFile oldDfsFileCopy = oldDfsFile;
//                oldDfsFileCopy.setId(IdUtill.getNextId("dm"));
//                dfsFileExtendService.insertDfsFile(oldDfsFileCopy);
//                DfsFileVersion dfsFileVersion = new DfsFileVersion();
//                dfsFileVersion.setId(IdUtill.getNextId("dmv"));
//                dfsFileVersion.setFileId(beforId);
//                dfsFileVersion.setAutor(oldDfsFile.getAutor());
//                dfsFileVersion.setTitle(oldDfsFile.getTitle());
//                dfsFileVersion.setFileName(oldDfsFile.getFileName());
//                dfsFileVersion.setUrl(oldDfsFile.getUrl());
//                dfsFileVersion.setVersionNum(oldDfsFile.getVersionNum());
//                dfsFileVersion.setNewFileName(dfsFile.getFileName());
//                dfsFileVersion.setNewFileSuffix(dfsFile.getFileSuffix());
//                dfsFileVersion.setNewUrl(dfsFile.getUrl());
//                dfsFileVersion.setCreateBy(dfsFile.getCreateBy());
//                dfsFileVersion.setCreateTime(DateUtils.getNowDate());
//                dfsFileVersion.setOldFileId(oldDfsFileCopy.getId());
//                dfsFileVersion.setSourceFileId(oldDfsFile.getSourceFileId());
//                dfsFileExtendService.insertDfsFileVersion(dfsFileVersion);
//
//                //更新文档
//                oldDfsFile.setId(beforId);
//                oldDfsFile.setUpdateTime(new Date());
//                oldDfsFile.setUpdateBy(dfsFile.getCreateBy());
//                oldDfsFile.setMd5(dfsFile.getMd5());
//                oldDfsFile.setFileName(dfsFile.getFileName());
//                oldDfsFile.setFileSuffix(dfsFile.getFileSuffix());
//                oldDfsFile.setUrl(dfsFile.getUrl());
//                oldDfsFile.setDeleteFlag(DEL_FLAG_0);
//                oldDfsFile.setVersionNum(oldDfsFile.getVersionNum() + 1);
//                oldDfsFile.setMaintainerId(null);
//                oldDfsFile.setMaintainerName(null);
//                oldDfsFile.setSourceFileId(dfsFile.getSourceFileId());
//                dfsFileExtendService.updateDfsFile(oldDfsFile);
//                dfsFileExtendService.delDfsFilePdfRef(oldDfsFile.getId());
//                //更新扩展表
//                DfsFileExtend dfsFileExtend = dfsFileExtendService.selectDfsFileExtendById(oldDfsFile.getId());
//                dfsFileExtend.setTitle(dfsFile.getTitle());
//                dfsFileExtend.setAutor(dfsFile.getAutor());
//                dfsFileExtendService.updateDfsFileExtend(dfsFileExtend);
//                //删除临时表
//                dfsFileExtendService.delDfsFilePdfRef(dfsFile.getId());
//                dfsFileExtendService.delDfsFile(dfsFile.getId());
//                return 1;
//            }
//        }
//        if (ApproveConstant.STATUS_DEALING != Integer.parseInt(sysApprove.getStatus())) {
//            DfsFile dfsFiletm = new DfsFile();
//            dfsFiletm.setId(oldDfsFile.getId());
//            dfsFiletm.setMaintainerId(null);
//            dfsFiletm.setMaintainerName(null);
//            dfsFileExtendService.updateDfsFile(dfsFiletm);
//            //删除临时表及文件
//            dfsFileExtendService.delDfsFilePdfRef(dfsFile.getId());
//            dfsFileExtendService.delDfsFile(dfsFile.getId());
//            try {
//                remoteFtpService.remove(dfsFile.getUrl());
//            } catch (Throwable throwable) {
//                log.error("删除文件失败，文件路径：" + dfsFile.getUrl());
//                throwable.printStackTrace();
//            }
//        }
//        return 1;
//    }
//
//    /**
//     * 获取文档历史版本列表
//     *
//     * @param fileId
//     * @return
//     */
//    @Override
//    public List<DfsFileVersion> getDmFileVersionList(String fileId) {
//        return dfsFileExtendService.getDmFileVersionList(fileId);
//    }
//
//    @Override
//    public void fileIdToFileConvert(List list) throws Throwable {
//        if (CollectionUtils.isEmpty(list)) {
//            return;
//        }
//        Set<String> totalIds = new HashSet<>();
//        for (Object object : list) {
//            if (object == null) continue;
//            Field[] allFields = object.getClass().getDeclaredFields();
//            for (int index = 0; index < allFields.length; index++) {
//                Field field = allFields[index];
//                FileIdToFileConvert attr = field.getAnnotation(FileIdToFileConvert.class);
//                if (attr == null) {
//                    continue;
//                }
//                String fileTargetFieldName = attr.fileTargetFieldName();
//                if (StringUtils.isBlank(fileTargetFieldName)) {
//                    continue;
//                }
//                String fieldName = field.getName();
//                String fileIds = (String) ReflectUtils.getFieldValue(object, fieldName);
//                if (StringUtils.isBlank(fileIds)) {
//                    continue;
//                }
//
//                HashSet<String> idsSet = new HashSet<>(Arrays.asList(fileIds.split(",")));
//                totalIds.addAll(idsSet);
//            }
//        }
//        //过滤空的
//        totalIds = totalIds.stream().filter(item -> StringUtils.isNotBlank(item)).collect(Collectors.toSet());
//        if (CollectionUtils.isEmpty(totalIds)) {
//            return;
//        }
//        List<DfsFile> dfsFiles = batchGetDfsFileByFileIds(totalIds);
//        if (CollectionUtils.isEmpty(dfsFiles)) {
//            return;
//        }
//        Map<String, DfsFile> dfsFilesKeyMap = dfsFiles.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
//
//        for (Object object : list) {
//            if (object == null) continue;
//            Field[] allFields = object.getClass().getDeclaredFields();
//            for (int index = 0; index < allFields.length; index++) {
//                Field field = allFields[index];
//                FileIdToFileConvert attr = field.getAnnotation(FileIdToFileConvert.class);
//                if (attr == null) {
//                    continue;
//                }
//                String fileTargetFieldName = attr.fileTargetFieldName();
//                if (StringUtils.isBlank(fileTargetFieldName)) {
//                    continue;
//                }
//                String fieldName = field.getName();
//                String fileIds = (String) ReflectUtils.getFieldValue(object, fieldName);
//                if (StringUtils.isBlank(fileIds)) {
//                    continue;
//                }
//                List<DfsFile> fieldFiles = new ArrayList<>(50);
//                String[] fileIdsArr = fileIds.split(",");
//                for (String fileId : fileIdsArr) {
//                    fieldFiles.add(dfsFilesKeyMap.get(fileId));
//                }
//                List<DfsFile> originFiles = (List<DfsFile>) ReflectUtils.getFieldValue(object, fileTargetFieldName);
//                if (CollectionUtils.isEmpty(originFiles)) {
//                    originFiles = new ArrayList<>(50);
//                }
//                originFiles.addAll(fieldFiles);
//                ReflectUtils.setFieldValue(object, fileTargetFieldName, originFiles);
//            }
//        }
//    }
//
//    /**
//     * 批量删除文档 服务器上文件、业务关联关系、历史版本均删除，文档本身状态置为已删除
//     *
//     * @param ids
//     * @param paths
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    @Transactional
//    public int deleteDmfilesByIds(String ids, String paths) throws Throwable {
//        //删除服务器上的文件
//        remoteFtpService.remove(paths);
//        //删除文档关联关系
//        List<DmBusinessFileRes> list = dfsFileExtendService.selectDmBusinessFileResByFileIds(ids);
//        if (CollectionUtils.isNotEmpty(list)) {
//            for (DmBusinessFileRes r : list) {
//                dfsFileExtendService.updateBusinessTableFileFieldData(r.getBusinessTable(), r.getBusinessField(), r.getBusinessId(), r.getFileId());
//                dfsFileExtendService.delDmBusinessFileResByFileIds(ids);
//            }
//        }
//        //删除所有历史版本
//        List<DfsFileVersion> dfsFileVersionList = dfsFileExtendService.selectDfsfileVersionByFileIds(ids);
//        if (CollectionUtils.isNotEmpty(dfsFileVersionList)) {
//            for (DfsFileVersion dfsFileVersion : dfsFileVersionList) {
//                //删除历史版本文件
//                remoteFtpService.remove(dfsFileVersion.getUrl());
//                dfsFileExtendService.delDfsFile(dfsFileVersion.getOldFileId());
//                //删除历史版本源文件
//                if (StringUtils.isNotEmpty(dfsFileVersion.getSourceFileId())) {
//                    remoteFtpService.remove(dfsFileVersion.getSourceFilePath());
//                    dfsFileExtendService.delDfsFile(dfsFileVersion.getSourceFileId());
//                }
//                dfsFileExtendService.deleteDmfileVersion(dfsFileVersion.getId());
//            }
//        }
//        //删除源文件
//        List<DfsFile> dfsFileList = dfsFileExtendService.selectSourceFileListByIds(ids);
//        if (CollectionUtils.isNotEmpty(dfsFileList)) {
//            for (DfsFile f : dfsFileList) {
//                remoteFtpService.remove(f.getUrl());
//            }
//        }
//        dfsFileExtendService.deleteDmSourceFileByIds(ids);
//        //删除文档本身
//        return dfsFileExtendService.deleteDmfilesByIds(ids);
//    }
//
//    /**
//     * @return int
//     * @Description 全局删除文件（by fileId）
//     * @Param [fileIds]
//     * @Author snaker
//     * @Date 2021/3/10 10:11
//     **/
//    @Transactional
//    @Override
//    public int deleteDmfilesByIds(String fileIds) throws Throwable {
//        if (StringUtils.isEmpty(fileIds)) {
//            return 0;
//        }
//        Set<String> staffsSet = new HashSet<>(Arrays.asList(fileIds.split(",")));
//        List<DfsFile> list = batchGetDfsFileByFileIds(staffsSet);
//        StringBuffer paths = new StringBuffer();
//        for (DfsFile dfsFile : list) {
//            paths.append(dfsFile.getUrl()).append(",");
//        }
//        if (paths.length() > 0) {
//            return deleteDmfilesByIds(fileIds, paths.substring(0, paths.length() - 1));
//        }
//        return 0;
//    }
//
//    @Override
//    public int batchUpdateDfsFileToDelFlag0(String fileIds) throws Throwable {
//        Set<String> staffsSet = new HashSet<>(Arrays.asList(fileIds.split(",")));
//        List<DfsFile> list = batchGetDfsFileByFileIds(staffsSet);
//        int count = 0;
//        if (CollectionUtils.isNotEmpty(list)) {
//            DfsFile tm = null;
//            for (DfsFile df : list) {
//                if (DEL_FLAG_3.equals(df.getDeleteFlag()) || DEL_FLAG_2.equals(df.getDeleteFlag())) {
//                    tm = new DfsFile();
//                    tm.setId(df.getId());
//                    tm.setDeleteFlag(DEL_FLAG_0);
//                    dfsFileExtendService.updateDfsFile(tm);
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//
//    @Override
//    public int batchUpdateDfsFileToDelFlag2(String fileIds) throws Throwable {
//        Set<String> staffsSet = new HashSet<>(Arrays.asList(fileIds.split(",")));
//        List<DfsFile> list = batchGetDfsFileByFileIds(staffsSet);
//        int count = 0;
//        if (CollectionUtils.isNotEmpty(list)) {
//            DfsFile tm = null;
//            for (DfsFile df : list) {
//                if (DEL_FLAG_3.equals(df.getDeleteFlag())) {
//                    tm = new DfsFile();
//                    tm.setId(df.getId());
//                    tm.setDeleteFlag(DEL_FLAG_2);
//                    dfsFileExtendService.updateDfsFile(tm);
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//
//    @Override
//    public int batchUpdateDfsFileToDelFlag(String fileIds, String delFlag) {
//        if (StringUtils.isEmpty(fileIds)) {
//            return 0;
//        }
//        Set<String> staffsSet = new HashSet<>(Arrays.asList(fileIds.split(",")));
//        List<DfsFile> list = null;
//        try {
//            list = batchGetDfsFileByFileIds(staffsSet);
//        } catch (Throwable throwable) {
//            return 0;
//        }
//        int count = 0;
//        if (CollectionUtils.isNotEmpty(list)) {
//            DfsFile tm = null;
//            for (DfsFile df : list) {
//                if (DEL_FLAG_3.equals(delFlag)) {
//                    if (DEL_FLAG_2.equals(df.getDeleteFlag())) {
//                        tm = new DfsFile();
//                        tm.setId(df.getId());
//                        tm.setDeleteFlag(delFlag);
//                        dfsFileExtendService.updateDfsFile(tm);
//                        count++;
//                    }
//                } else if (DEL_FLAG_2.equals(delFlag)) {
//                    if (DEL_FLAG_3.equals(df.getDeleteFlag())) {
//                        tm = new DfsFile();
//                        tm.setId(df.getId());
//                        tm.setDeleteFlag(delFlag);
//                        dfsFileExtendService.updateDfsFile(tm);
//                        count++;
//                    }
//                } else if (DEL_FLAG_0.equals(delFlag)) {
//                    if (DEL_FLAG_3.equals(df.getDeleteFlag())
//                            || DEL_FLAG_2.equals(df.getDeleteFlag())) {
//                        tm = new DfsFile();
//                        tm.setId(df.getId());
//                        tm.setDeleteFlag(delFlag);
//                        dfsFileExtendService.updateDfsFile(tm);
//                        count++;
//                    }
//                }
//            }
//        }
//        return count;
//    }
//
//    @Override
//    public int delDmBusinessFileResByBusinessId(Integer modal, String businessId) {
//        return dfsFileExtendService.delDmBusinessFileResByBusinessId(modal, businessId);
//    }
//
//    @Override
//    public void batchInsertDmBusinessFileRes(List<DmBusinessFileRes> list) {
//        final int BATCH_UPDATE_SIZE = 100;
//        if (CollectionUtils.isNotEmpty(list)) {
//            int insertListSize = list.size();
//            int pageNum = insertListSize % BATCH_UPDATE_SIZE == 0 ? insertListSize / BATCH_UPDATE_SIZE : (insertListSize / BATCH_UPDATE_SIZE + 1);
//            if (pageNum > 0) {
//                for (int i = 1; i <= pageNum; i++) {
//                    dfsFileExtendService.batchInsertDmBusinessFileRes(list.stream().skip(BATCH_UPDATE_SIZE * (i - 1)).limit(BATCH_UPDATE_SIZE).collect(Collectors.toList()));
//                }
//            }
//        }
//    }
//
//    @Override
//    public int batchDelTmDfsFiles(String fileIds) throws Throwable {
//        Set<String> staffsSet = new HashSet<>(Arrays.asList(fileIds.split(",")));
//        List<DfsFile> list = batchGetDfsFileByFileIds(staffsSet);
//        int count = 0;
//        if (CollectionUtils.isNotEmpty(list)) {
//            DfsFile tm = null;
//            for (DfsFile df : list) {
//                if (DEL_FLAG_3.equals(df.getDeleteFlag())) {
//                    remoteFtpService.remove(df.getUrl());
//                    dfsFileExtendService.delDfsFile(df.getId());
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//
//    @Override
//    public List<DmBusinessFileRes> getDmBusinessFilResList(String fileId) {
//        return dfsFileExtendService.getDmBusinessFilResList(fileId);
//    }
//
//    @Override
//    @Transactional
//    public int deleteDmfileVersion(String id, String path, String sourceFilePath, String fileId) throws Throwable {
//        remoteFtpService.remove(path);
//        if (StringUtils.isNotEmpty(sourceFilePath)) {
//            remoteFtpService.remove(sourceFilePath);
//        }
//        dfsFileExtendService.delDfsFile(fileId);
//        return dfsFileExtendService.deleteDmfileVersion(id);
//    }
//
//    @Override
//    @Transactional
//    public int deleteDmBusinessFileResList(List<DmBusinessFileRes> list) {
//        int count = 0;
//        for (DmBusinessFileRes dmBusinessFileRes : list) {
//            dfsFileExtendService.delDmBusinessFileResByEntity(dmBusinessFileRes);
//            count++;
//        }
//        return count;
//    }
//
//    /**
//     * 上传预览PDF文件
//     *
//     * @param file
//     * @param dfsFile
//     * @param currentUserId
//     * @return
//     */
//    @Override
//    public int uploadDmSourceFile(MultipartFile file, DfsFile dfsFile, long currentUserId) throws Throwable {
//        //上传新附件
//        String md5 = DigestUtils.md5Hex(file.getInputStream());
//        //查询系统中是否已存在
//        DfsFile df = dfsFileExtendService.selectOnlyDfsFileByMd5(md5);
//        String fileId = null;
//        if (df != null) {
//            fileId = df.getId();
//        } else {
//            R r = remoteFtpService.upload(file, currentUserId, DEL_FLAG_0, md5, null);
//            if (!r.judgeSuccess()) {
//                throw new RuntimeException(r.getMsg());
//            }
//            DfsFile sourceDfsFile = r.getData(DfsFile.class);
//            fileId = sourceDfsFile.getId();
//        }
//        dfsFile.setSourceFileId(fileId);
//        dfsFileExtendService.updateDfsFile(dfsFile);
//        return 1;
//    }
//
//    /**
//     * 保存包含源文件的文件（传入的为文件夹）
//     *
//     * @param file
//     * @param currentUserId
//     * @param fileCategoryId
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    public Map<String, Object> saveContainSourceFile(File file, long currentUserId, String fileCategoryId) throws Throwable {
//        Map<String, Object> result = new HashMap<>();
//        List<DfsFile> successList = new ArrayList<>();
//        List<DfsFile> failList = new ArrayList<>();
//        List<DfsFile> newDfsFiles = new ArrayList<>();
//        File[] files = null;
//        SysUser user = sysUserService.selectUserById(Long.valueOf(currentUserId));
//        files = file.listFiles();
//        if (files.length > 2) {
//            throw new RuntimeException("文件夹中最多只能包含两个文件");
//        }
//        File pdfFile = null;
//        File sourceFile = null;
//        int pdfFileCount = 0;
//        for (File fileItem : files) {
//            String name = fileItem.getName();
//            if (name.toLowerCase().endsWith(".pdf")) {
//                pdfFile = fileItem;
//                pdfFileCount++;
//            } else {
//                sourceFile = fileItem;
//            }
//        }
//        if (pdfFileCount != 1) {
//            throw new RuntimeException("文件夹中有且只能包含1个pdf文件");
//        }
//        if (sourceFile != null) {
//            if (!pdfFile.getName().substring(0, pdfFile.getName().lastIndexOf(".")).equals(sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf(".")))) {
//                throw new RuntimeException("pdf文件和源文件名称不一致，请修正后重新上传");
//            }
//        }
//        try {
//            FileInputStream fileInputStreamItem = null;
//            FileInputStream fileInputStreamItem2 = null;
//            FileInputStream fileInputStreamItem3 = null;
//            FileInputStream fileInputStreamItem4 = null;
//            try {
//                fileInputStreamItem = new FileInputStream(pdfFile);
//                String md5 = DigestUtils.md5Hex(fileInputStreamItem);
//                //查询系统中是否已存在
//                DfsFile df = dfsFileExtendService.selectDfsFileByMd5(md5);
//                if (null != df) {
//                    failList.add(df);
//                    result.put("successList", successList);
//                    result.put("failList", failList);
//                    List<DfsFile> list = new ArrayList<>();
//                    list.addAll(successList);
//                    list.addAll(failList);
//                    result.put("list", list);
//                    return result;
//                }
//                String delFlag = DEL_FLAG_0;
//                SysMenu sysMenu = sysMenuService.selectMenuByPerms(DM_APPROVE_KEY);
//                if (sysMenu != null && VISIBLE_0.equals(sysMenu.getVisible())) {
//                    delFlag = DEL_FLAG_2;
//                }
//                fileInputStreamItem2 = new FileInputStream(pdfFile);
//                MultipartFile multipartFile = new MultipartFileDto("file", pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStreamItem2);
//                R r = remoteFtpService.upload(multipartFile, currentUserId, delFlag, md5, null);
//                if (!r.judgeSuccess()) {
//                    throw new RuntimeException(r.getMsg());
//                }
//                DfsFile dfsFile = r.getData(DfsFile.class);
//                //上传源文件
//                if (sourceFile != null) {
//                    fileInputStreamItem3 = new FileInputStream(sourceFile);
//                    md5 = DigestUtils.md5Hex(fileInputStreamItem3);
//                    DfsFile sf = dfsFileExtendService.selectOnlyDfsFileByMd5(md5);
//                    if (sf != null) {
//                        dfsFile.setSourceFileId(sf.getId());
//                    } else {
//                        fileInputStreamItem4 = new FileInputStream(sourceFile);
//                        multipartFile = new MultipartFileDto("file", sourceFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStreamItem4);
//                        r = remoteFtpService.upload(multipartFile, currentUserId, DEL_FLAG_0, md5, null);
//                        if (!r.judgeSuccess()) {
//                            throw new RuntimeException(r.getMsg());
//                        }
//                        DfsFile sourceDfsFile = r.getData(DfsFile.class);
//                        dfsFile.setSourceFileId(sourceDfsFile.getId());
//                    }
//                    dfsFileExtendService.updateDfsFile(dfsFile);
//                }
//                dfsFile.setAutor(user.getUserName());
//                successList.add(dfsFile);
//                newDfsFiles.add(dfsFile);
//            } finally {
//                if (fileInputStreamItem != null) {
//                    fileInputStreamItem.close();
//                }
//                if (fileInputStreamItem2 != null) {
//                    fileInputStreamItem2.close();
//                }
//                if (fileInputStreamItem3 != null) {
//                    fileInputStreamItem3.close();
//                }
//                if (fileInputStreamItem4 != null) {
//                    fileInputStreamItem4.close();
//                }
//            }
//        } finally {
//            FileUtil.del(file);
//        }
//        if (CollectionUtils.isNotEmpty(newDfsFiles)) {
//            batchSaveFileToDBWithoutTransactional(newDfsFiles, fileCategoryId);
//        }
//        result.put("successList", successList);
//        result.put("failList", failList);
//        List<DfsFile> list = new ArrayList<>();
//        list.addAll(successList);
//        list.addAll(failList);
//        result.put("list", list);
//        return result;
//    }
//
//
//    @Override
//    public DfsFile uploadFile(File uploadFile, Long currentUserId, String delFlag, String md5) throws Throwable {
//        DfsFile dfsFile = null;
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(uploadFile);
//            MultipartFile multipartFile = new MultipartFileDto("file", uploadFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//            R r = remoteFtpService.upload(multipartFile, currentUserId, delFlag, md5, null);
//            if (!r.judgeSuccess()) throw new RuntimeException(r.getMsg());
//
//            dfsFile = r.getData(DfsFile.class);
//            log.info("[上传完毕一个附件{}]", dfsFile.toString());
//        } finally {
//            if (fileInputStream != null) fileInputStream.close();
//        }
//        return dfsFile;
//    }
//
//    @Override
//    public ZipDfsFile uploadDirFileAndBuildDirTreeAndZipFile(File dirFile, Long currentUserId) throws Throwable {
//        ZipDfsFile result = new ZipDfsFile();
//
//        File zipFile = FileUtils.fileToZip(dirFile.getPath(), dirFile.getParent(), dirFile.getName());
//
//        DfsFile zipDfsFile = uploadFile(zipFile, currentUserId, "0", null);
//
//        DirTree zipDirTree = new DirTree(zipDfsFile.getId(), dirFile.getName(), true);
//        buildDirTree(zipDirTree, dirFile, currentUserId);
//
//        BeanUtils.copyProperties(zipDfsFile, result);
//
//        result.setDirDataStr(JSON.toJSONString(zipDirTree));
//
//        R r = remoteCommonService.addZipDfsFile(result);
//        r.throwRuntimeExceptionIfError();
//        return result;
//    }
//
//    private void buildDirTree(DirTree dirTree, File dirFile, Long currentUserId) throws Throwable {
//        File[] files = dirFile.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                //是文件夹
//                DirTree childDirTree = new DirTree(IdUtill.getNextId("temp-"), file.getName(), true);
//                buildDirTree(childDirTree, file, currentUserId);
//                dirTree.getChildren().add(childDirTree);
//            } else {
//                //是文件
//                DfsFile dfsFile = uploadFile(file, currentUserId, "0", null);
//                dirTree.getChildren().add(new DirTree(dfsFile.getId(), dfsFile.getFileName(), false));
//            }
//        }
//    }
//
//    @Override
//    public String uploadBigFile(Chunk chunk, HttpServletResponse response) throws IOException {
//        /**
//         * 每一个上传块都会包含如下分块信息：
//         * chunkNumber: 当前块的次序，第一个块是 1，注意不是从 0 开始的。
//         * totalChunks: 文件被分成块的总数。
//         * chunkSize: 分块大小，根据 totalSize 和这个值你就可以计算出总共的块数。注意最后一块的大小可能会比这个要大。
//         * currentChunkSize: 当前块的大小，实际大小。
//         * totalSize: 文件总大小。
//         * identifier: 这个就是每个文件的唯一标示。
//         * filename: 文件名。
//         * relativePath: 文件夹上传的时候文件的相对路径属性。
//         * 一个分块可以被上传多次，当然这肯定不是标准行为，但是在实际上传过程中是可能发生这种事情的，这种重传也是本库的特性之一。
//         *
//         * 根据响应码认为成功或失败的：
//         * 200 文件上传完成
//         * 201 文加快上传成功
//         * 500 第一块上传失败，取消整个文件上传
//         * 507 服务器出错自动重试该文件块上传
//         */
//
//        String filePath = FileUtils.getSessionIsolatedPath("bigFile");
//        log.info("【断点续传-文件所在位置:{}】", filePath);
//        File dir = new File(filePath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        File file = new File(filePath, chunk.getFilename());
//        //第一个块,则新建文件
//        if (chunk.getChunkNumber() == 1 && !file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                response.setStatus(500);
//                return "exception:createFileException";
//            }
//        }
//
//        InputStream fos = null;
//        RandomAccessFile raf = null;
//        try {
//            //将块文件写入文件中
//            fos = chunk.getFile().getInputStream();
//            raf = new RandomAccessFile(file, "rw");
//            int len = -1;
//            byte[] buffer = new byte[1024];
//
//            long i = (chunk.getChunkNumber() - 1) * 1024 * 1024 * 5L;
//            raf.seek(i);
//            while ((len = fos.read(buffer)) != -1) {
//                raf.write(buffer, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            if (chunk.getChunkNumber() == 1) {
//                file.delete();
//            }
//            response.setStatus(507);
//            return "exception:writeFileException";
//        } finally {
//            if (fos != null) fos.close();
//            if (raf != null) raf.close();
//        }
//        if (chunk.getChunkNumber().equals(chunk.getTotalChunks())) {
//            response.setStatus(200);
//            log.info("【断点续传-最终文件为:{}】", file.getPath());
//            return file.getPath();
//        } else {
//            response.setStatus(201);
//            return "ok";
//        }
//    }
//
//    /**
//     * @return void
//     * @Description 压缩包形式下载文件
//     * @Param [ids  文件ids, bt 二进制数组]
//     * @Author snaker
//     * @Date 2021/4/26 14:29
//     **/
//    @Override
//    public void downloadZip(String ids, ByteArrayOutputStream bt) throws Throwable {
//        String[] fileIds = ids.split(",");
//        String uuid = UUID.randomUUID().toString();
//        String tempDirPath = FileUtils.getSessionIsolatedPath(uuid);
//        log.info("【生成临时文件夹位置:{}】", tempDirPath);
//        File dir = new File(tempDirPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        for (String fileId : fileIds) {
//            BufferedInputStream bufferedInputStream = null;
//            FileOutputStream out = null;
//            BufferedOutputStream bufferedOutputStream = null;
//            try {
//                // 下载文档
//                Response serviceResponse = remoteFtpService.download(fileId);
//                log.info(serviceResponse.headers().toString());
//                // 获取文件名
//                String fileName = "";
//                Collection<String> collect = serviceResponse.headers().get("content-disposition");
//                Iterator<String> iterator = collect.iterator();
//                while (iterator.hasNext()) {
//                    String next = iterator.next();
//                    if (next.indexOf("fileName") > -1) {
//                        fileName = next.split("=")[1];
//                        break;
//                    }
//                }
//                log.info("文件名称" + fileName);
//                String path = tempDirPath + fileName;
//                Response.Body body = serviceResponse.body();
//
//                bufferedInputStream = new BufferedInputStream(body.asInputStream());
//                out = new FileOutputStream(new File(path));
//                bufferedOutputStream = new BufferedOutputStream(out);
//                int length = 0;
//                byte[] temp = new byte[1024 * 10];
//                while ((length = bufferedInputStream.read(temp)) != -1) {
//                    bufferedOutputStream.write(temp, 0, length);
//                    bufferedOutputStream.flush();
//                }
//
//            } catch (Throwable e) {
//                e.printStackTrace();
//            } finally {
//                if (bufferedInputStream != null) {
//                    bufferedInputStream.close();
//                }
//                if (bufferedOutputStream != null) {
//                    bufferedOutputStream.close();
//                }
//                if (out != null) {
//                    out.close();
//                }
//                if (dir.exists()) {
//                    FileUtils.deleteFile(tempDirPath);
//                }
//            }
//        }
//        ZipOutputStream out = new ZipOutputStream(bt);
//        //创建缓冲输出流等等
//        ZipUtil.zip(out, false, null, new File(tempDirPath));
//    }
}
