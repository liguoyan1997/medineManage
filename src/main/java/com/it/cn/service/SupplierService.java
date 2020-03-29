package com.it.cn.service;

import com.it.cn.base.BaseService;
import com.it.cn.dao.SupplierDao;
import com.it.cn.entity.Stock;
import com.it.cn.entity.Supplier;
import com.it.cn.util.UuidUtil;
import com.it.cn.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierService extends BaseService<SupplierDao,Supplier> {
    /*已继承*/
    public Supplier save(Supplier supplier) {
        if(supplier.getId() == "" || supplier.getId() == null){
            String uuid = UuidUtil.uuid();
            supplier.setId(uuid);
            supplier.setStatus(0);
            insert(supplier);
        }else{
            update(supplier);
        }
        return supplier;
    }
}
