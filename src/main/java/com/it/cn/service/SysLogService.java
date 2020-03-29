package com.it.cn.service;

import com.it.cn.dao.SysLogDao;
import com.it.cn.entity.SysLog;
import com.it.cn.util.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogService {
    
    @Autowired
    private SysLogDao sysLogDao;

    public List<SysLog> findList(SysLog entity) {
        return sysLogDao.findList(entity);
    }

    public SysLog get(String id) {
        return sysLogDao.get(id);
    }

    public void delete(String id) {
        sysLogDao.delete(id);
    }

    public void insert(SysLog entity) {
        sysLogDao.insert(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<SysLog> findPage(Page<SysLog> page, SysLog entity) {
        page.setList(findList(entity));
        return page;
    }
}
