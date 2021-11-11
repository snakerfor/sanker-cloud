package com.snaker.dfs.controller;

import com.snaker.common.core.controller.BaseController;
import com.snaker.common.core.domain.R;
import com.snaker.common.dfs.util.FtpUtils;
import com.snaker.common.utils.file.FileUploadUtils;
import com.snaker.dfs.config.DfsConfig;
import com.snaker.dfs.config.FtpConfig;
import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;
import com.snaker.dfs.mapper.DfsFileMapper;
import com.snaker.dfs.service.IDfsFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 文件上传 提供者
 *
 * @author snaker
 * @date 2019-05-16
 */
@Slf4j
@RestController
@RequestMapping("file")
public class DfsFileController extends BaseController {

    @Autowired
    private IDfsFileService dfsFileService;

    @Autowired
    @Lazy
    private DfsFileMapper dfsFileMapper;

    @Autowired
    private FtpConfig ftpConfig;

    @Autowired
    private DfsConfig dfsConfig;

    /**
     * 查询文件上传列表
     */
    @GetMapping("list")
    public R list(DfsFile dfsFile) {
        startPage();
        return result(dfsFileService.selectDfsFileList(dfsFile));
    }

    /**
     * 修改
     */
    @PostMapping("update")
    public R editSave(@RequestBody DfsFile dfsFile) {
        return toAjax(dfsFileService.updateDfsFile(dfsFile));
    }

    /**
     * 修改保存文件上传
     *
     * @throws IOException
     */
    @PostMapping("upload")
    public R editSave(@RequestParam("file") MultipartFile file) throws IOException {
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
            DfsFile dfsFile = new DfsFile();
            dfsFile.setId(fileId);
            dfsFile.setFileName(file.getOriginalFilename());
            dfsFile.setFileSuffix(extensionName);
            dfsFile.setUrl(ftpPath);
            dfsFile.setDeleteFlag("0");
            dfsFile.setCreateBy(getLoginName());
            return toAjax(dfsFileService.insertDfsFile(dfsFile));
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 删除文件上传
     */
    @PostMapping("remove")
    public R remove(String ids) {
        FtpUtils ftpUtils = null;
        try {
            List<DfsFile> dfsFile = dfsFileService.selectDfsFileListByIds(ids);
            if (dfsFile.size() > 0) {
                ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
                for (DfsFile file : dfsFile) {
                    if (ftpUtils.deleteFile(file.getUrl())) {
                        dfsFileService.deleteDfsFileByIds(file.getId());
                    } else {
                        return R.error("文件删除失败");
                    }
                }
                return R.ok();
            }
        } finally {
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
        }
        return R.error("文件不存在");
    }


    @GetMapping("get/{id}")
    public DfsFile get(@PathVariable("id") String id) {
        DfsFile dfsFileDB = dfsFileService.selectDfsFileById(id);
        return dfsFileDB;
    }


    @PostMapping("batchGetByIds")
    public R get(@RequestBody DfsFile dfsFile) {
        Set<String> ids = dfsFile.getQueryIds();
        if (CollectionUtils.isEmpty(ids)) {
            return R.data(new ArrayList<>());
        }
        String[] idsArr = (String[]) ids.toArray(new String[ids.size()]);
        return R.data(dfsFileMapper.selectDfsFileListByIds(idsArr));
    }

    @PostMapping("batchUpdateDfsFileDelFlagByOldDelFlags")
    public R batchUpdateDfsFileDelFlagByOldDelFlags(@RequestBody DfsFile dfsFile) {
        dfsFileMapper.batchUpdateDfsFileDelFlagByOldDelFlags(dfsFile);
        return R.ok();
    }


    @GetMapping("/getPicture")
    public void getPicture(HttpServletRequest request, HttpServletResponse response, String imgPath) throws Exception {
        FTPClient ftp = null;
        InputStream in = null;
        OutputStream os = null;
        FtpUtils ftpUtils = null;
        try {
            ftpUtils = new FtpUtils(ftpConfig.getHost(), ftpConfig.getPort(), ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath());
            ftp = ftpUtils.getFtpClient();
            //下载文件 FTP协议里面，规定文件名编码为iso-8859-1，所以读取时要将文件名转码为iso-8859-1
            //如果没有设置按照UTF-8读，获取的流是空值null
            in = ftp.retrieveFileStream(new String(imgPath.getBytes("UTF-8"), "iso-8859-1"));
            String picType = imgPath.split("\\.")[1];
            BufferedImage bufImg = null;
            bufImg = ImageIO.read(in);
            os = response.getOutputStream();
            ImageIO.write(bufImg, picType, os);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ftpUtils != null) {
                ftpUtils.closeFtpClient();
            }
        }
    }


    @PostMapping("/addZipDfsFile")
    public R addZipDfsFile(@RequestBody ZipDfsFile zipDfsFile) {
        logger.info("【参数打印 保存zip file :{}】", zipDfsFile);
        return toAjax(dfsFileService.insertZipDfsFile(zipDfsFile));
    }

    @GetMapping("/getZipDfsFile/{id}")
    public R getZipDfsFile(@PathVariable("id") String id) {
        return R.data(dfsFileService.selectZipDfsFileById(id));
    }

}
