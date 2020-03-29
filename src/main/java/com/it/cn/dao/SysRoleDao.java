package com.it.cn.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.cn.base.BaseDao;
import com.it.cn.entity.SysRole;
import com.it.cn.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleDao extends BaseDao<SysRole> {
    /*继承*/
}
