package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffDao extends BaseDao<Staff> {
    /*方法以继承*/
}
