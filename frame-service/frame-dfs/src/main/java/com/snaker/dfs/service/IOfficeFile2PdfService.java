package com.snaker.dfs.service;

import com.snaker.common.enums.FileTypeEnum;

import java.io.File;

public interface IOfficeFile2PdfService {

    File file2Pdf(byte[] bytes, FileTypeEnum fileTypeEnum, String fileName, String fileExtension, String sessionTempFilePath) throws Exception;
}
