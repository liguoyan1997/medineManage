package com.it.cn.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.cn.base.BaseService;
import com.it.cn.dao.SysRoleDao;
import com.it.cn.entity.SysRole;
import com.it.cn.entity.SysRole;
import com.it.cn.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("RoleService")
@Transactional(rollbackFor = Exception.class)
public class SysRoleService extends BaseService<SysRoleDao,SysRole> {
    /*继承*/
    /*已继承*/
   /* public SysRole save(SysRole sysRole) {
        if(sysRole == "" || sysRole.getDelflag() == null){
            String uuid = UuidUtil.uuid();
            sysRole.setDelflag("0");
            insert(sysRole);
        }else{
            update(sysRole);
        }
        return sysRole;
    }*/
}
