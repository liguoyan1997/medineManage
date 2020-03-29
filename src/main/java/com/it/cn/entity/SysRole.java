package com.it.cn.entity;

import com.it.cn.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class SysRole extends BaseEntity<SysRole> implements Serializable {
    @Id
    @GeneratedValue
    private String id;
    private String rolename;
    private String description; // 角色描述
    private Boolean available = Boolean.FALSE; // 默认不可用
    // 用户 - 角色关系：多对多关系;
    @ManyToMany
    private List<SysUser> users;
    private String delflag;
}
