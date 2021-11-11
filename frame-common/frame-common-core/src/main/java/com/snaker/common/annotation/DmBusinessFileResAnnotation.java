package com.snaker.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DmBusinessFileResAnnotation {

    int modal();

    String businessTable();

    String businessTableDsc();

    String businessField();

    String businessFieldDsc();

    boolean delFlagWithMainDataDelFlag() default false;

    int delFlag();

}
