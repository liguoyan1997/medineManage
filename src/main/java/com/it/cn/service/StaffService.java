package com.it.cn.service;

import com.it.cn.base.BaseService;
import com.it.cn.dao.StaffDao;
import com.it.cn.entity.Staff;
import com.it.cn.entity.SysUser;
import com.it.cn.util.UuidUtil;
import com.it.cn.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class StaffService extends BaseService<StaffDao,Staff> {
    /*已继承*/
    public Staff save(Staff staff) {
        if(staff.getStaffid() == "" || staff.getStaffid() == null){
            String uuid = UuidUtil.uuid();
            staff.setStaffid(uuid);
            staff.setStatus(0);
            insert(staff);
        }else{
            update(staff);
        }
        return staff;
    }
}
