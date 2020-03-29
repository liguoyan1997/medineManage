package com.it.cn.util.page.converter.restful;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseWrapper {

    ResponseCode value() default ResponseCode.SUCCESS;
}
