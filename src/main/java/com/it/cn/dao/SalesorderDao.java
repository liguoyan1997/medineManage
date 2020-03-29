package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.Salesorder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesorderDao extends BaseDao<Salesorder> {
    /*方法以继承*/
}
