import cn.hutool.core.util.ZipUtil;
import com.snaker.RddsSystemApp;
import com.snaker.common.exception.file.FileNameLengthLimitExceededException;
import com.snaker.common.utils.file.FileUtils;
import com.snaker.dfs.feign.RemoteFtpService;
import com.snaker.frame.domain.IstNewLccjResult;
import com.snaker.frame.domain.IstNewLccjResultDetail;
import com.snaker.system.feign.RemoteRoleService;
import feign.Response;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest(classes = RddsSystemApp.class)
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
@Slf4j
public class DownloadZip {

    @Autowired
    private ISysFileService iSysFileService;

    @Autowired
    private RemoteFtpService remoteFtpService;

    //    @Test
    public void listAll() throws Throwable {
        String[] fileIds = {"022da319be7944a9bc4906b32c907c3f", "8d56aefb8d4c4a89800d2d4733ed7779"};
        String uuid = UUID.randomUUID().toString();
        String tempDirPath = FileUtils.getSessionIsolatedPath(uuid);
        log.info("【生成临时文件夹位置:{}】", tempDirPath);
        File dir = new File(tempDirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (String fileId : fileIds) {
            // 下载文档
            Response serviceResponse = remoteFtpService.download(fileId);
            log.info(serviceResponse.headers().toString());
            // 获取文件名
            String fileName = getFileName(serviceResponse);
            log.info("文件名称" + fileName);
            String path = tempDirPath + fileName;
            Response.Body body = serviceResponse.body();
            BufferedInputStream bufferedInputStream = null;
            FileOutputStream out = null;
            BufferedOutputStream bufferedOutputStream = null;
            try {
                bufferedInputStream = new BufferedInputStream(body.asInputStream());
                out = new FileOutputStream(new File(path));
                bufferedOutputStream = new BufferedOutputStream(out);
                int length = 0;
                byte[] temp = new byte[1024 * 10];
                while ((length = bufferedInputStream.read(temp)) != -1) {
                    bufferedOutputStream.write(temp, 0, length);
                    bufferedOutputStream.flush();
                }
//                response.setHeader("Content-Disposition", serviceResponse.headers().get("Content-Disposition").toString().replace("[", "").replace("]", ""));
//                FileOutputStream out = new FileOutputStream(new File(path));
//                bufferedOutputStream = new BufferedOutputStream(out;
//                int length = 0;
//                byte[] temp = new byte[1024 * 10];
//                while ((length = bufferedInputStream.read(temp)) != -1) {
//                    bufferedOutputStream.write(temp, 0, length);
//                }
//                bufferedOutputStream.flush();
//                bufferedOutputStream.close();
//                bufferedInputStream.close();
//                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        }
        // 打包zip
//        out = new ZipOutputStream(response.getOutputStream());
//        //创建缓冲输出流
//        bos = new BufferedOutputStream(out);
//        File sourceFile = new File(sourceFileName);
//        ZipUtil.zip()
    }


//    @Before
//    public void init() {
//
//        // 旧数据准备
//        IstNewLccjResultDetail detail1 = new IstNewLccjResultDetail();
//        detail1.setFileId("11111");
//        oldList.add(detail1);
//        IstNewLccjResultDetail detail2 = new IstNewLccjResultDetail();
//        detail1.setFileId("11112");
//        oldList.add(detail2);
//        IstNewLccjResultDetail detail3 = new IstNewLccjResultDetail();
//        detail1.setFileId("11113");
//        oldList.add(detail3);
//
//        // 新数据准备
//        IstNewLccjResultDetail detail4 = new IstNewLccjResultDetail();
//        detail1.setFileId("11111");
//        newList.add(detail4);
//        IstNewLccjResultDetail detail5 = new IstNewLccjResultDetail();
//        detail1.setFileId("11112");
//        newList.add(detail5);
//        IstNewLccjResultDetail detail6 = new IstNewLccjResultDetail();
//        detail1.setFileId("11113");
//        newList.add(detail6);
//    }

    @Test
    public void getDiff() throws Throwable {
        List<IstNewLccjResultDetail> oldList = new ArrayList<IstNewLccjResultDetail>();
        List<IstNewLccjResultDetail> newList = new ArrayList<IstNewLccjResultDetail>();
        // 旧数据准备
        IstNewLccjResultDetail detail1 = new IstNewLccjResultDetail();
        detail1.setFileId("11114");
        oldList.add(detail1);
        IstNewLccjResultDetail detail2 = new IstNewLccjResultDetail();
        detail2.setFileId("11112");
        oldList.add(detail2);
        IstNewLccjResultDetail detail3 = new IstNewLccjResultDetail();
        detail3.setFileId("11113");
        oldList.add(detail3);

        // 新数据准备
        IstNewLccjResultDetail detail4 = new IstNewLccjResultDetail();
        detail4.setFileId("11111");
        newList.add(detail4);
        IstNewLccjResultDetail detail5 = new IstNewLccjResultDetail();
        detail5.setFileId("11112");
        newList.add(detail5);
        IstNewLccjResultDetail detail6 = new IstNewLccjResultDetail();
        detail6.setFileId("11113");
        newList.add(detail6);
        oldList.stream()
                .filter(map -> isEqual(map.getFileId(),newList))
                .forEach(map -> {
                    log.info(map.getFileId());
                });
    }

    public boolean isEqual(String fileId, List<IstNewLccjResultDetail> newList){
        for (IstNewLccjResultDetail detail : newList) {
            if (detail.getFileId().equals(fileId)) {
                return false;
            }
        }
        return true;
    }
    // 获取文件名
    public String getFileName(Response serviceResponse) {
        Collection<String> collect = serviceResponse.headers().get("content-disposition");
        Iterator<String> iterator = collect.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.indexOf("fileName") > -1) {
                return next.split("=")[1];
            }
        }
        return null;
    }
}