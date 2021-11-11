package com.snaker.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Compare4Approval {

    // STRING FILE DATE STRING2 FILE2
    String type() default "STRING";

    String label();

    String readConverterExp() default "";

    String dictType() default "";

}
