package com.it.cn.service;

import com.it.cn.base.BaseService;
import com.it.cn.dao.MedicineDao;
import com.it.cn.dao.StockDao;
import com.it.cn.entity.Stock;
import com.it.cn.util.UuidUtil;
import com.it.cn.util.page.Page;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class StockService extends BaseService<StockDao,Stock> {
    /*已继承*/
    @Autowired
    private StockDao stockDao;
    @Autowired
    private MedicineDao medicineDao;

    public List<Stock> findOverdueList(Stock entity) {
        return stockDao.findOverdueList(entity);
    }

    public List<Stock> echart(){
        return stockDao.echart();
    }

    public List<Integer> echartnum(){
        return stockDao.echartnum();
    }

    public List<Integer> timeechartnum(){
        return  stockDao.timeechartnum();
    }

    public List<Integer> timeechartnum0(){
        return  stockDao.timeechartnum0();
    }

    public Stock save(Stock stock) {
        if(stock.getId() == "" || stock.getId() == null){
            String uuid = UuidUtil.uuid();
            stock.setId(uuid);
            stock.setStatus(0);
            insert(stock);
            medicineDao.delete(stock.getMid());
        }else{
            update(stock);
        }
        return stock;
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<Stock> findOverduePage(Page<Stock> page, Stock entity) {
        page.setList(findOverdueList(entity));
        return page;
    }
}
