package com.it.cn.service;

import com.it.cn.base.BaseService;
import com.it.cn.dao.SalesorderDao;
import com.it.cn.entity.Salesorder;
import com.it.cn.util.UuidUtil;
import com.it.cn.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SalesorderService extends BaseService<SalesorderDao,Salesorder> {
    /*已继承*/
    public Salesorder save(Salesorder salesorder) {
        if(salesorder.getSoid() == "" || salesorder.getSoid() == null){
            String uuid = UuidUtil.uuid();
            salesorder.setSoid(uuid);
            salesorder.setStatus(0);
            insert(salesorder);
        }else{
            update(salesorder);
        }
        return salesorder;
    }
}
