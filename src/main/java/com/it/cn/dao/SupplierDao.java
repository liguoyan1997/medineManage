package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierDao extends BaseDao<Supplier> {
    /*方法以继承*/
}
