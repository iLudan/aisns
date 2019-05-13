package com.aiaaa.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface LuxLog {
    public String value() default "xxx";
}
