package com.snaker.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class CommandUtil {


    /**
     * 执行command指令
     *
     * @param command
     * @return
     */
    public static boolean executeLibreOfficeCommand(String command) throws InterruptedException, IOException {
        log.info("[开始执行command命令:{}]", command);

        BufferedReader br = null;
        try {
            // Process可以控制该子进程的执行或获取该子进程的信息
            // exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
            Process process = Runtime.getRuntime().exec(command);

            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            log.info("[command执行result:{}]", sb.toString());

            int exitStatus = process.waitFor();// 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值,返回0表示正常结束
            // 第二种接受返回值的方法
            int i = process.exitValue(); // 接收执行完毕的返回值
            log.info("[执行command命令 i = {}]", i);
            log.info("[执行command命令 exitStatus = {}]", exitStatus);

            process.destroy(); // 销毁子进程
            log.info("[执行command命令结束]");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
