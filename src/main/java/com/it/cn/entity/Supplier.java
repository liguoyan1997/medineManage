package com.it.cn.entity;

import com.it.cn.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Supplier extends BaseEntity<Supplier> implements Serializable {
    private String id;
    private String spid;
    private String spname;
    private String sparea;
    private String spmeno;
    private int status;
}
