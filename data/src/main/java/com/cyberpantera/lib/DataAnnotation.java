package com.cyberpantera.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface DataAnnotation {
    int nameIndex() default 0;
    int dailyWorkingHours() default 0;
    String drawablePath() default "";
    String jsonPath() default "";
    String paramsResId() default "";
}