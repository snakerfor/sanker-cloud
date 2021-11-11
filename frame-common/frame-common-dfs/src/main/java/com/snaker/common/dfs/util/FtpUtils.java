package com.snaker.common.dfs.util;

import com.snaker.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ftp工具类
 */
@Slf4j
public class FtpUtils {

    /**
     * ftp 服务器IP
     */
    private String host;
    /**
     * ftp 端口
     */
    private int port;
    /**
     * ftp 用户
     */
    private String username;
    /**
     * ftp 用户密码
     */
    private String password;
    /**
     * ftp 存放根路径
     */
    private String basePath;

    /**
     * 默认端口
     */
    public final static int DEFAULT_PORT = 21;

    private FTPClient ftpClient = null;

    public FtpUtils(String host, int port, String username, String password, String basePath) {
        boolean initResult = init(host, port, username, password, basePath);
        log.info("[初始化FtpClient结果:{}]", initResult);
    }

    /**
     * 初始化配置
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param basePath
     */
    private boolean init(String host, int port, String username, String password, String basePath) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.basePath = basePath;
        return initFtpClient();
    }

    /**
     * 初始化ftp服务器
     */
    private boolean initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:" + this.host + ":" + this.port);
            ftpClient.connect(host, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            ftpClient.setControlEncoding("UTF-8");
            //ftp通信有两种模式
            //PORT(主动模式)客户端开通一个新端口(>1024)并通过这个端口发送命令或传输数据,期间服务端只使用他开通的一个端口，例如21
            //PASV(被动模式)客户端向服务端发送一个PASV命令，服务端开启一个新端口(>1024),并使用这个端口与客户端的21端口传输数据
            //由于客户端不可控，防火墙等原因，所以需要由服务端开启端口，需要设置被动模式
            ftpClient.enterLocalPassiveMode();
            //设置传输方式为流方式
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connect failed...ftp服务器:" + this.host + ":" + this.port);
                return false;
            }
            System.out.println("connect successfu...ftp服务器:" + this.host + ":" + this.port);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 上传multipartfile文件
     *
     * @param decoderBytes
     * @param fileName
     * @return
     */
    public String upload(byte[] decoderBytes, String fileName) throws Exception {
        String filePath = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            //转到上传文件的根目录
            boolean b = ftpClient.changeWorkingDirectory(basePath);
            if (!b) {
                boolean d = createDirectory(ftpClient, basePath);
                if (!d) {
                    throw new RuntimeException("[创建目录失败]");
                }
            }
            //转到存储目录
            String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            boolean f = createDirectory(ftpClient, format);
            if (!f) {
                throw new RuntimeException("[转到存储目录失败]");
            }
            //设置上传文件的类型为二进制类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            byteArrayInputStream = new ByteArrayInputStream(decoderBytes);
            System.out.println("[fileName]" + fileName);
            boolean storeFileResult = ftpClient.storeFile(fileName, byteArrayInputStream);
            if (!storeFileResult) {
                throw new RuntimeException("[存储文件失败]");
            }
            //返回ftp存储路径
            filePath = basePath + "/" + format + "/" + fileName;
            log.info("[FtpUtils 上传文件成功，文件路径为:{}]", filePath);
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }


    /**
     * 文件下载
     *
     * @param path
     * @param out
     * @return
     */
    public boolean downloadFile(String path, OutputStream out) throws Exception {
        boolean flag = false;
        try {
            System.out.println("开始下载文件");
            //切换FTP目录
            int lastSpIndex = path.lastIndexOf("/");
            String directory = StringUtils.substring(path, 0, lastSpIndex);
            String filename = StringUtils.substring(path, lastSpIndex + 1);
            ftpClient.changeWorkingDirectory(directory);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    ftpClient.retrieveFile(file.getName(), out);
                    out.close();
                }
            }
//            ftpClient.logout();
            flag = true;
            System.out.println("下载文件成功");
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    public byte[] getByteArrByFilePathName(String pathName) throws Exception {
        InputStream inputStream = null;
        try {
            int lastSpIndex = pathName.lastIndexOf("/");
            String directory = StringUtils.substring(pathName, 0, lastSpIndex);
            String filename = StringUtils.substring(pathName, lastSpIndex + 1);
            ftpClient.changeWorkingDirectory(directory);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据，ftp server可能每次开启不同的端口来传输数据，
            // 但是在Linux上，由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftpClient.enterLocalPassiveMode();
            inputStream = ftpClient.retrieveFileStream(filename);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return bytes;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 删除ftp文件
     *
     * @param path
     * @return
     */
    public boolean deleteFile(String path) {
        boolean flag = false;
        try {
            //切换FTP目录
            int lastSpIndex = path.lastIndexOf("/");
            String directory = StringUtils.substring(path, 0, lastSpIndex);
            ftpClient.changeWorkingDirectory(directory);
            ftpClient.dele(StringUtils.substring(path, lastSpIndex + 1));
//            ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * ftp 服务器创建目录
     *
     * @param ftp
     * @param path
     * @return
     */
    private boolean createDirectory(FTPClient ftp, String path) {
        if (StringUtils.isEmpty(path)) return true;
        String[] dirs = path.split("/");
        //创建目录
        try {
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) continue;
                //判断是否存在目录
                if (!ftp.changeWorkingDirectory(dir)) {
                    //不存在则创建
                    if (!ftp.makeDirectory(dir)) {
                        return false;//创建目录失败
                    }
                    //进入新创建的目录
                    ftp.changeWorkingDirectory(dir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public FTPClient getFtpClient() {
        return this.ftpClient;
    }

    public void closeFtpClient() {
        if (this.ftpClient != null) {
            try {
                this.ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (this.ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
