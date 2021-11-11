package com.snaker.dfs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>File：FtpConfig.java</p>
 * <p>Title: ftp文件配置</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2019 2019年8月29日 下午5:27:02</p>
 * <p>Company: yinrunnet.com </p>
 *
 * @author snaker
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {
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
}