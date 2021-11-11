package com.snaker.system.domain;

import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 上传文件列表对象 t_upload_list
 * 
 * @author Random-pro
 * @date 2020-02-14
 */
public class SysUploadList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件id */
    @Excel(name = "文件id")
    private String fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件MD5 */
    @Excel(name = "文件MD5")
    private String fileMd5;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;

    /**    0=禁止访问,1=需要授权,2=游客访问 */
    @Excel(name = "文件权限 0=禁止访问,1=需要授权,2=游客访问")
    private String fileAuth;

    /** 文件路径 (相对于根路径) */
    @Excel(name = "文件路径")
    private String filePath;

    /** 上传时间 */
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 下载计数 */
    @Excel(name = "下载计数")
    private Integer downloadCount;

    public void setFileId(String fileId) 
    {
        this.fileId = fileId;
    }

    public String getFileId() 
    {
        return fileId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileMd5(String fileMd5) 
    {
        this.fileMd5 = fileMd5;
    }

    public String getFileMd5() 
    {
        return fileMd5;
    }
    public void setFileSize(String fileSize) 
    {
        this.fileSize = fileSize;
    }

    public String getFileSize() 
    {
        return fileSize;
    }
    public void setFileAuth(String fileAuth) 
    {
        this.fileAuth = fileAuth;
    }

    public String getFileAuth() 
    {
        return fileAuth;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
    public void setUploadTime(Date uploadTime) 
    {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() 
    {
        return uploadTime;
    }
    public void setDownloadCount(Integer downloadCount) 
    {
        this.downloadCount = downloadCount;
    }

    public Integer getDownloadCount() 
    {
        return downloadCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("fileMd5", getFileMd5())
            .append("fileSize", getFileSize())
            .append("fileAuth", getFileAuth())
            .append("filePath", getFilePath())
            .append("uploadTime", getUploadTime())
            .append("downloadCount", getDownloadCount())
            .toString();
    }
}
