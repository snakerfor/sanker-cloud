package com.snaker.common.pojo.entity;

import com.snaker.common.enums.SysProcessStatusEnum;
import com.snaker.common.enums.SysProcessTypeEnum;
import com.snaker.common.utils.DateUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    //进程类型
    private SysProcessTypeEnum processType;

    //进程状态
    private SysProcessStatusEnum processStatus;

    //自定义数据
    private String processData;

    //运行日志
    private String processLog = "";

    private Date createTime;

    private Date updateTime;

    private String beginTime;

    private String endTime;

    public void appendProcessLog(String string) {
        string = DateUtils.getTime() + "    " + string;
        this.processLog = StringUtils.isBlank(this.processLog) ? string : this.processLog + "\r\n" + "\r\n" + string;
    }
}
