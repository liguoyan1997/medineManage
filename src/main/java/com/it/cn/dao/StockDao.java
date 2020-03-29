package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Mapper
public interface StockDao extends BaseDao<Stock> {
    /*方法以继承*/
    List<Stock> findOverdueList(Stock stock);

    List<Stock> echart();

    List<Integer> echartnum();

    List<Integer> timeechartnum();

    List<Integer> timeechartnum0();
}
