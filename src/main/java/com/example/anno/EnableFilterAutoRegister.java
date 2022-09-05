package com.example.anno;

import com.example.config.WebFilterAutoRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动注册filter
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(WebFilterAutoRegister.class)
@Documented
public @interface EnableFilterAutoRegister {
}
