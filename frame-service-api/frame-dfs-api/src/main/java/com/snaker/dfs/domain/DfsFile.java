package com.snaker.dfs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.snaker.common.annotation.Excel;
import com.snaker.common.core.domain.BaseEntity;
import com.snaker.common.enums.FileTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 文件上传
 */
@ToString
@Data
public class DfsFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Excel(name = "文档类型")
    private String fileCategoryName;

    @Excel(name = "文档描述")
    private String title;

    /**
     * 文件名
     */
    @Excel(name = "文档", filePropertyName = "dfsFiles")
    private String fileName;

    @Excel(name = "作者")
    private String autor;

    @Excel(name = "版本号")
    private Double versionNum;

    private String beforFileId;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * URL地址
     */
    private String url;

    private String md5;

    private String fileCategoryId;


    /* 当前维护者ID*/
    private Long maintainerId;
    /* 当前维护者姓名*/
    private String maintainerName;

    /**
     * 服务商
     */
    private String deleteFlag;

    private FileTypeEnum fileTypeEnum;

    private String pdfId;

    private List<DfsFile> dfsFiles = new ArrayList<>();

    private Integer alreadyExtend;

    private String newDelFlagOpt;

    private Set<String> oldDelFlagsOpt;

    private String sourceFileId;

    private Integer uploadSourceFileFlag;

    private String sourceFilePath;


    public FileTypeEnum getFileTypeEnum() {
        return FileTypeEnum.getFileTypeEnumByFileSuffix(this.fileSuffix);
    }

}
