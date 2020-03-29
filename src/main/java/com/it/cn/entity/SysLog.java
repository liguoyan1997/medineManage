package com.it.cn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.cn.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class SysLog implements Serializable {
    @Id
    @GeneratedValue
    private String id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private String ip;
    /*表格显示格式*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /*格式化日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    public SysLog(String username, String operation, String method, String params, String ip, Date createtime) {
        this.username = username;
        this.operation = operation;
        this.method = method;
        this.params = params;
        this.ip = ip;
        this.createtime = createtime;
    }
}
