package com.it.cn.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Vacrotar {
    private int code;
    private String message;
    public Vacrotar(int code,String message){
        this.code =  code;
        this.message =  message;
    }
}
