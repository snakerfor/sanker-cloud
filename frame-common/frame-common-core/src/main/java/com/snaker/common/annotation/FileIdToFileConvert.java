package com.snaker.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FileIdToFileConvert {

    /**
     * 附件对应的字段名,这列如有值，则会根据“;”分割，获取的路径读取到文件，文件信息会放在对应的字段中,且该字段必须为List<DfsFile>类型
     */
    public String fileTargetFieldName();

}
