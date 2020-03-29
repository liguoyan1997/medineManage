package com.it.cn.service;
  

import com.it.cn.base.BaseService;
import com.it.cn.dao.MedicineDao;
import com.it.cn.entity.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 123 Service
 *
 * @author 123
 * @date 2019-08-18 16:32:41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MedicineService extends BaseService<MedicineDao,Medicine> {

    @Autowired
    private MedicineDao medicineDao;

    public MedicineService(MedicineDao medicineDao) {
        this.medicineDao = medicineDao;
    }

    /*继承*/
    public List<Medicine> findListID(Medicine entity) {
        return medicineDao.findListID(entity);
    }
}  