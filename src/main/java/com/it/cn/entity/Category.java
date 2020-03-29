package com.it.cn.entity;

import com.it.cn.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Category extends BaseEntity<Category> implements Serializable {
    private int cid;
    private String cname;
    private int status;
}
