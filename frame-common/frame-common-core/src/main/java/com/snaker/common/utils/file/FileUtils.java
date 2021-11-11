package com.snaker.common.utils.file;

import cn.hutool.core.util.ZipUtil;
import com.snaker.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件处理工具类
 *
 * @author sfd
 */
@Slf4j
public class FileUtils {

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    public static final String SINGLE_FOLDER = "single";

    public static final String UN_ZIP_FOLDER = "unzip";

    public static final String ZIP_ATTACHMENT_FOLDER = "attachment";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 获取系统临时目录
     *
     * @return
     */
    public static String getTemp() {
        return System.getProperty("java.io.tmpdir");
    }

    //获取session临时解压目录
    public static String getSessionTempUnZipPath() {
        return getSessionTempFilePath() + UN_ZIP_FOLDER + File.separator;
    }

    //获取session临时文件目录
    public static String getSessionTempSingleFilePath() {
        return getSessionTempFilePath() + SINGLE_FOLDER + File.separator;
    }

    //获取session临时文件目录
    public static String getSessionTempFilePath() {
        return getTemp() + File.separator + "sessiondata" + File.separator + ServletUtils.getSessionId() + File.separator;
    }

    public static String getSessionIsolatedPath(String isolatedFolderName) {
        return getSessionTempFilePath() + isolatedFolderName + File.separator;
    }

    public static String getSessionIsolatedTempFile(String isolatedFolderName) {
        return getSessionTempFilePath() + isolatedFolderName;
    }


    /**
     * 创建临时文件
     *
     * @param filePath
     * @param data
     * @return
     */
    public static File createTempFile(String filePath, byte[] data) throws IOException {
        String temp = getTemp() + filePath;
        File file = new File(temp);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data, 0, data.length);
        fos.flush();
        fos.close();
        return file;
    }

    public static File fetchUnZipFile(File zipFile) throws Exception {
        String zipPath = zipFile.getPath();
        String unZipFilePath = zipPath.substring(0, zipPath.lastIndexOf(File.separator));
        String unZipFileName = FileUtils.unZip(zipFile, unZipFilePath);
        return new File(unZipFilePath + File.separator + unZipFileName + File.separator);
    }

    public static String unZip(File srcFile, String destDirPath) throws Exception {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) throw new RuntimeException(srcFile.getPath() + "所指文件不存在");

        // 开始解压
        ZipFile zipFile = null;
        String result = null;
        try {
            zipFile = new ZipFile(srcFile, Charset.forName("GBK"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                String entryName = entry.getName();
                if (StringUtils.isBlank(result)) {
                    //这个为解压后的文件夹名
                    result = entryName;
                }

                log.info(String.format("[ 解压文件(夹): %s ]", entryName));
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entryName;
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entryName);
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            log.info("解压完成，耗时：" + (end - start) + " ms");
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }
        return result;
    }

    public static void createFile(File createFile) throws IOException {
        File parentFile = createFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!createFile.exists()) {
            createFile.createNewFile();
        }
    }

    public static String getFileName(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        return string.substring(string.lastIndexOf("/") == -1 ? 0 : string.lastIndexOf("/") + 1, string.lastIndexOf("."));
    }

    public static String getFileExtension(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }

        return string.substring(string.lastIndexOf(".") + 1);
    }

    public static File fileToZip(String sourceFilePath, String zipFilePath, String fileName) throws IOException {
        log.info(">>>>>> 待压缩的文件目录：" + sourceFilePath + "  <<<<<<");
        log.info(">>>>>> 目标压缩文件为：" + zipFilePath + "/" + fileName + ".zip" + "  <<<<<<");
        return ZipUtil.zip(sourceFilePath, zipFilePath + "/" + fileName + ".zip");
    }
}
