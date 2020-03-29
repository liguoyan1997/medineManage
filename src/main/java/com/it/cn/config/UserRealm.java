package com.it.cn.config;

import com.it.cn.dao.SysUserDao;
import com.it.cn.entity.State;
import com.it.cn.entity.SysUser;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    private Logger logger = Logger.getLogger(UserRealm.class);

    @Autowired
    private SysUserDao userDao;
//    @Autowired
//    private RoleFunctionDao roleFunctionDao;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("授权认证");
        //获取登录名
        SysUser token = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String userName=token.getSysuserLogin();
        //根据登录名获取用户以及角色id
        SysUser user = userDao.checkLogin(userName);
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        //设置角色
        authorizationInfo.addRole(String.valueOf(user.getRole().getRolename()));    // 添加角色
//        for (SysPermission permission : role.getPermissions()) {
//            authorizationInfo.addStringPermission(permission.getPermission());    // 添加具体权限
//            logger.info("用户" + user.getSysuserLogin() + "权限放入完成!");
//        }
        return authorizationInfo;
    }


    /**
     * 身份认证
     * AuthenticationToken 用于收集用户提交的身份（如用户名）及凭据（如密码）在shro域里找
     * getPrincipal()获取当前对象
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token.getPrincipal() == null){
            return null;
        }
        String username = (String) token.getPrincipal();  //获取单个用户对象
        SysUser user = userDao.checkLogin(username); // 获取用户登录账号
        if(user==null) {
            //用户不存在就抛出异常
            throw new UnknownAccountException();
        }
        if (State.LOCKED.equals(user.getStatus())){
            //用户被锁定就抛异常
            throw new LockedAccountException();
        }
        //密码可以通过SimpleHash加密，然后保存进数据库。
        //此处是获取数据库内的账号、密码、盐值，保存到登陆信息info中
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user,
                user.getSysuserPassWord(),
                getName());                   //realm name
        return authenticationInfo;
    }
}
