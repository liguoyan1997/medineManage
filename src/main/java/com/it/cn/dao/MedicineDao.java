package com.it.cn.dao;
  

import com.it.cn.base.BaseDao;
import com.it.cn.entity.Medicine;
import com.it.cn.entity.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 123 Dao
 *
 * @author 123
 * @date 2019-08-18 16:32:41
 */
@Mapper
public interface MedicineDao extends BaseDao<Medicine> {
    /*方法以继承*/
    List<Medicine> findListID(Medicine entity);
}