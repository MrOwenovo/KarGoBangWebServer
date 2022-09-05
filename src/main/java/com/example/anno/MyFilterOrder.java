package com.example.anno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MyFilterOrder {
    int value() default 50;
    String[] urlPatterns() default {"/*"};
}
