package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.Category;
import com.it.cn.entity.Medicine;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.Entity;
import java.util.List;

@Mapper
public interface CategoryDao extends BaseDao<Category> {
    /*继承*/
}
