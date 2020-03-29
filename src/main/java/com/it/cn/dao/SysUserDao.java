package com.it.cn.dao;

import com.it.cn.base.BaseDao;
import com.it.cn.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserDao extends BaseDao<SysUser> {
    SysUser findLogin(@Param("sysuserLogin")String login,@Param("sysuserPassWord")String passwd);

    SysUser checkLogin(String login);

    SysUser checkPsd(String psd);
    /*方法以继承*/
}
