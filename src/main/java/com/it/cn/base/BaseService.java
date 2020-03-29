package com.it.cn.base;

import com.it.cn.dao.SysLogDao;
import com.it.cn.entity.SysLog;
import com.it.cn.entity.SysUser;
import com.it.cn.util.IP.GetIp;
import com.it.cn.util.page.Page;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class BaseService<D extends BaseDao<T>,T extends BaseEntity<T>> {

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private D dao;

    public List<T> findList(T entity) {
        return dao.findList(entity);
    }


    public T get(String id) {
        return dao.get(id);
    }

    public T update(T entity) {
        SysLog sysLog = new SysLog(((SysUser)SecurityUtils.getSubject().getPrincipal()).getSysuserLogin(),"数据更新操作","/"+entity.toString().split("\\(")[0]+"/save",entity.toString(), GetIp.getip(),new Date());
        sysLogDao.insert(sysLog);
        dao.update(entity);
        return entity;
    }

    public void delete(String id) {
        dao.delete(id);
    }

    /*@Validated参数校验*/
    public void insert(T entity) {
        SysLog sysLog = new SysLog(((SysUser)SecurityUtils.getSubject().getPrincipal()).getSysuserLogin(),"数据插入操作","/"+entity.toString().split("\\(")[0]+"/save",entity.toString(), GetIp.getip(),new Date());
        sysLogDao.insert(sysLog);
        dao.insert(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, T entity) {
        SysLog sysLog = new SysLog(((SysUser)SecurityUtils.getSubject().getPrincipal()).getSysuserLogin(),"数据分页展示","/"+entity.toString().split("\\(")[0]+"/list","", GetIp.getip(),new Date());
        sysLogDao.insert(sysLog);
        page.setList(findList(entity));
        return page;
    }
}
