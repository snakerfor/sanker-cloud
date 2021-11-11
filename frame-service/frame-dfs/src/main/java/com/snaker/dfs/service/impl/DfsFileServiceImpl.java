package com.snaker.dfs.service.impl;

import cn.hutool.core.io.FileUtil;
import com.snaker.common.core.text.Convert;
import com.snaker.common.dfs.util.FtpUtils;
import com.snaker.common.enums.FileTypeEnum;
import com.snaker.common.utils.file.FileUtils;
import com.snaker.dfs.config.FtpConfig;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;
import com.snaker.dfs.mapper.DfsFileMapper;
import com.snaker.dfs.service.IDfsFileService;
import com.snaker.dfs.service.IOfficeFile2PdfService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

/**
 * 业务 服务层实现
 *
 * @author sfd
 */
@Service
public class DfsFileServiceImpl implements IDfsFileService {

    private static final Logger log = LoggerFactory.getLogger(DfsFileServiceImpl.class);

    @Autowired
    @Lazy
    private IOfficeFile2PdfService iOfficeFile2PdfService;

    @Autowired
    private DfsFileMapper dfsFileMapper;

    @Autowired
    private FtpConfig ftpConfig;

    @Override
    public List<DfsFile> selectDfsFileList(DfsFile dfsFile) {
        return dfsFileMapper.selectDfsFileList(dfsFile);
    }

    @Override
    public int insertDfsFile(DfsFile dfsFile) {
        return dfsFileMapper.insertDfsFile(dfsFile);
    }

    @Override
    public int insertZipDfsFile(ZipDfsFile zipDfsFile) {
        return dfsFileMapper.insertZipDfsFile(zipDfsFile);
    }

    @Override
    public int updateDfsFile(DfsFile dfsFile) {
        return dfsFileMapper.updateDfsFile(dfsFile);
    }

    @Override
    public int deleteDfsFileByIds(String ids) {
        return dfsFileMapper.deleteDfsFileByIds(Convert.toStrArray(ids));
    }

    @Override
    public DfsFile selectDfsFileById(String id) {
        DfsFile dfsFileDB = dfsFileMapper.selectDfsFileById(id);
        richPdfFileId(dfsFileDB);
        return dfsFileDB;
    }

    @Override
    public List<DfsFile> selectDfsFileListByIds(String ids) {
        return dfsFileMapper.selectDfsFileListByIds(Convert.toStrArray(ids));
    }

    @Override
    public String fetchPdfFileIdByOriginOfficeFileId(String originOfficeFileId) {
        return dfsFileMapper.getPdfFileIdByOriginOfficeFileId(originOfficeFileId);
    }

    @Override
    public void richPdfFileId(DfsFile dfsFile) {
        if (dfsFile == null) return;
        FileTypeEnum fileTypeEnum = dfsFile.getFileTypeEnum();
        String pdfId = null;
        String id = dfsFile.getId();
        if (FileTypeEnum.WORD.equals(fileTypeEnum) || FileTypeEnum.EXCEL.equals(fileTypeEnum) || FileTypeEnum.PPT.equals(fileTypeEnum)) {
            pdfId = fetchPdfFileIdByOriginOfficeFileId(id);
        } else if (FileTypeEnum.PDF.equals(fileTypeEnum)) {
            pdfId = id;
        }
        dfsFile.setPdfId(pdfId);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    @Transactional(rollbackFor = Throwable.class)
    public synchronized void asynStartBuildPdfFile(String originOfficeFileId, String sessionTempFilePath) throws Exception {
        buildPdfFile(originOfficeFileId, sessionTempFilePath);
    }

    private String buildPdfFile(String originOfficeFileId, String sessionTempFilePath) throws Exception {
        log.info("[buildPdfFile方法开始处理:{}]", originOfficeFileId);
        DfsFile originOfficeFile = selectDfsFileById(originOfficeFileId);

        FtpUtils ftpUtils = null;
        File pdfFile = null;
        try {
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());

            String url = originOfficeFile.getUrl();
            String fileNameAndExtension = originOfficeFile.getFileName();
            String fileName = FileUtils.getFileName(fileNameAndExtension);
            String fileExtension = FileUtils.getFileExtension(fileNameAndExtension);

            FileTypeEnum fileTypeEnum = originOfficeFile.getFileTypeEnum();
            byte[] byteArrByFilePathName = ftpUtils.getByteArrByFilePathName(url);

            //生成pdf文件
            pdfFile = iOfficeFile2PdfService.file2Pdf(byteArrByFilePathName, fileTypeEnum, fileName, fileExtension, sessionTempFilePath);

            DfsFile newPdfFile = new DfsFile();
            String newPdfFileId = UUID.randomUUID().toString().replaceAll("-", "");
            log.info("生成的新pdf映射文件id为:{}", newPdfFileId);

            ftpUtils.closeFtpClient();
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
            System.out.println("【不知道为什么，但必须这里重新获取下ftpclient.....】");
            //转存ftp
            String newPdfFileFtpPath = ftpUtils.upload(Files.readAllBytes(pdfFile.toPath()), newPdfFileId + ".pdf");
            log.info("转存pdf映射文件的路径为:{}", newPdfFileFtpPath);

            newPdfFile.setId(newPdfFileId);
            newPdfFile.setFileName(fileName + ".pdf");
            newPdfFile.setFileSuffix(".pdf");
            newPdfFile.setDeleteFlag("0");
            newPdfFile.setUrl(newPdfFileFtpPath);
            dfsFileMapper.insertDfsFile(newPdfFile);
            dfsFileMapper.insertDfsFilePdfRef(originOfficeFileId, newPdfFileId);
            return newPdfFileId;
        } finally {
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
            if (pdfFile != null) {
                FileUtil.del(pdfFile);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String getPdfByOriginOfficeFileId(String originOfficeFileId, String sessionTempFilePath) throws Exception {
        String pdfFileId = fetchPdfFileIdByOriginOfficeFileId(originOfficeFileId);
        if (StringUtils.isNotBlank(pdfFileId)) {
            log.info("[已经有pdf映射文件:{}]", pdfFileId);
            return pdfFileId;
        }
        return buildPdfFile(originOfficeFileId, sessionTempFilePath);
    }

    @Override
    public Integer delDfsFilePdfRef(String id) {
        return dfsFileMapper.delDfsFilePdfRef(id);
    }


    @Override
    public ZipDfsFile selectZipDfsFileById(String id) {
        return dfsFileMapper.selectZipDfsFileById(id);
    }
}
