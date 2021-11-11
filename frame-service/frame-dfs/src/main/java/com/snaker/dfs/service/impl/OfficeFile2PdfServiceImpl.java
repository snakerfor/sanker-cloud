package com.snaker.dfs.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.aspose.cells.Workbook;
import com.aspose.words.License;
import com.snaker.common.enums.FileTypeEnum;
import com.snaker.common.utils.CommandUtil;
import com.snaker.common.utils.file.FileUtils;
import com.snaker.dfs.service.IOfficeFile2PdfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class OfficeFile2PdfServiceImpl implements IOfficeFile2PdfService {


    public static boolean convertCMD(String inputFile, String pdfFileOutDir) throws IOException, InterruptedException {
        log.info("【inputFile】" + inputFile);
        log.info("【pdfFileOutDir】" + pdfFileOutDir);
        long start = System.currentTimeMillis();
        String command;
        boolean flag;
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            command = "cmd /c start soffice --headless --invisible --convert-to pdf:writer_pdf_Export " + inputFile + " --outdir " + pdfFileOutDir;
        } else {
            command = "libreoffice --headless --invisible --convert-to pdf:writer_pdf_Export " + inputFile + " --outdir " + pdfFileOutDir;
        }
        flag = CommandUtil.executeLibreOfficeCommand(command);
        long end = System.currentTimeMillis();
        log.info("[用时:{} ms]", end - start);
        return flag;
    }

    private void convert(String fileName, String fileExtension, File originFile, byte[] bytes, File toFile, String sessionTempFilePath) throws IOException, InterruptedException {
        String originFileStr = sessionTempFilePath + fileName + "." + fileExtension;
        log.info("【originFileStr】" + originFileStr);
        originFile = new File(originFileStr);
        FileUtils.createFile(originFile);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(originFile, bytes);
        convertCMD(originFileStr, toFile.getParentFile().getPath());
        log.info("[ originFile文件大小为:{}]", originFile.length());
        int waitTimes = waitForToFileCreated(toFile);
        log.info("[ 共计休眠{}秒,toFile文件大小为:{}]", waitTimes, toFile.length());
    }


    @Override
    public File file2Pdf(byte[] bytes, FileTypeEnum fileTypeEnum, String fileName, String fileExtension, String sessionTempFilePath) throws Exception {
        //word转换时，遇到文件名含空格 . 或特殊符号时转换失败，所以统一重命名文件名
        fileName = UUID.fastUUID().toString(true);
        String toFileName = sessionTempFilePath + fileName + ".pdf";
        log.info("[office file 转 pdf 文件时，预生成的新的pdf文件是:{}]", toFileName);
        File toFile = new File(toFileName);
        File originFile = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            FileUtils.createFile(toFile);
            inputStream = new ByteArrayInputStream(bytes);


            switch (fileTypeEnum) {
                case WORD:
//                    getLicense2Word();
//                    Document document = new Document(inputStream);
//                    document.save(toFileName, com.aspose.words.SaveFormat.PDF);
                    convert(fileName, fileExtension, originFile, bytes, toFile, sessionTempFilePath);
                    break;
                case EXCEL:
                    getLicense2Excel();
                    Workbook wb = new Workbook(inputStream);
                    fileOutputStream = new FileOutputStream(toFile);

                    //把内容放在一张PDF页面上
                    com.aspose.cells.PdfSaveOptions pdfSaveOptions = new com.aspose.cells.PdfSaveOptions();
                    pdfSaveOptions.setOnePagePerSheet(true);
                    pdfSaveOptions.setAllColumnsInOnePagePerSheet(true);

                    wb.save(fileOutputStream, pdfSaveOptions);
                    break;
                case PPT:
//                    getLicense2Ppt();
//                    Presentation pres = new Presentation(inputStream);
//                    fileOutputStream = new FileOutputStream(toFile);
//                    pres.save(fileOutputStream, com.aspose.slides.SaveFormat.Pdf);
                    convert(fileName, fileExtension, originFile, bytes, toFile, sessionTempFilePath);
                    break;
                default:
                    throw new RuntimeException("[不支持的文件类型]" + fileTypeEnum);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (originFile != null) {
                FileUtil.del(originFile);
            }
        }
        return toFile;
    }

    private int waitForToFileCreated(File toFile) throws InterruptedException {
        int result = 0;

        while (toFile.length() == 0) {
            log.info("线程即将休眠5秒,当前toFile大小为:{}", toFile.length());
            Thread.sleep(5000L);
            result = result + 5;
            if (result > 3600) {
                throw new RuntimeException("已经转换了一个小时了,没戏了");
            }
        }
        return result;
    }

    private static boolean getLicense2Word() throws IOException {
        boolean result = false;
        InputStream is = null;
        try {
            // 凭证
            String license =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "      <Product>Aspose.Words for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                            "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                            "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                            "  </Data>\n" +
                            "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                            "</License>";

            is = new ByteArrayInputStream(license.getBytes("UTF-8"));
            License asposeLic = new License();
            asposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    private static boolean getLicense2Excel() throws IOException {
        boolean result = false;
        InputStream is = null;
        try {
            // 凭证
            String license =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "      <Product>Aspose.Words for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                            "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                            "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                            "  </Data>\n" +
                            "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                            "</License>";
            is = new ByteArrayInputStream(license.getBytes("UTF-8"));
            com.aspose.cells.License asposeLic = new com.aspose.cells.License();
            asposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    private static boolean getLicense2Ppt() throws IOException {
        boolean result = false;
        InputStream is = null;
        try {
            String license =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "      <Product>Aspose.Words for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                            "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                            "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                            "  </Data>\n" +
                            "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                            "</License>";
            is = new ByteArrayInputStream(license.getBytes("UTF-8"));
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return result;
    }


}
