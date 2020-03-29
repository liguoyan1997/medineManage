package com.it.cn.base;

import lombok.Data;

@Data
public class BaseEntity<T> {
    //校验信息
    private String message;
}
