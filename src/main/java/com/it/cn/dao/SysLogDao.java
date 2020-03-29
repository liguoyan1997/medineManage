package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.SysLog;
import com.it.cn.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLogDao {
    List<SysLog> findList(SysLog entity);

    SysLog get(String id);

    void delete(String id);

    void insert(SysLog entity);
}
