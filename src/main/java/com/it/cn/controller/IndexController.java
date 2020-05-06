package com.it.cn.controller;

import com.it.cn.dao.SysLogDao;
import com.it.cn.entity.SysLog;
import com.it.cn.entity.SysUser;
import com.it.cn.service.SysUserService;
import com.it.cn.util.IP.GetIp;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private SysUserService sysUserService;




    /*登陆页面*/
    @RequestMapping("/")
    public String index(Model model){
      model.addAttribute("error","* * *     أنا أعيش من دون أيام     * * *");
      return "/index/login/login0.html";
    }

    /*登录失败*/
    @RequestMapping("/500")
    public String aa(){
        return "/public/error/500.html";
    }

    /*用户退出*/
    @RequestMapping("/exitLogin")
    public RedirectView loginOut(){
        //springboott页面重定向
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        //退出
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        redirectTarget.setUrl("");
        return redirectTarget;
    }

    /*跳转注册页面*/
    @RequestMapping("/toResign")
    public String toResign(){
        return "/index/login/resign0.html";
    }

    /*跳转登录页面*/
    @RequestMapping("/toLogin")
    public String toLogin(Model model){
        model.addAttribute("error","* * *     أنا أعيش من دون أيام     * * *");
        return "/index/login/login0.html";
    }

    /*登陆*/
    @RequestMapping("/loginGet")
    public String loginGet(SysUser sysUser, HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("code");
        session.setAttribute("sessionCode",sessionCode);
        if(sessionCode == null || session.equals("") || sysUser.getSysuserLogin() == null || sysUser.getSysuserLogin() == ""){
            return "redirect:/";
        }else{
            session.setAttribute("sysuser",sysUser.getSysuserLogin());
            SysUser user = sysUserService.checkLogin(sysUser.getSysuserLogin());
            session.setAttribute("userFile",user.getSysFile());
            SysLog sysLog = new SysLog(session.getAttribute("sysuser").toString(),"用户登录操作","{}","", GetIp.getip(),new Date());
            sysLogDao.insert(sysLog);
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(sysUser.getSysuserLogin(),sysUser.getSysuserPassWord());
        //获取用户登录信息
        Subject subject= SecurityUtils.getSubject();
        if (StringUtils.equalsIgnoreCase(sysUser.getCode(),sessionCode)) {
            try {
                //登录
                subject.login(usernamePasswordToken);
                //查找用户所对应的功能
//                List<RoleFunction> roleFunctions = roleFunctionService.findFunctionByUser(user1.getId());
//                model.addAttribute("roleFunctions", JSON.toJSONString(roleFunctions));
            }catch (IncorrectCredentialsException ice){
                model.addAttribute("error","******password  error******");
                return "/index/login/login0.html";
            }catch (UnknownAccountException uae) {
                model.addAttribute("error","******userName  error******");
                return "/index/login/login0.html";
            }catch (ExcessiveAttemptsException eae) {
                model.addAttribute("error","********time  error********");
                return "/index/login/login0.html";
            }
            return "redirect:/toMain";
        }else {
            model.addAttribute("error","*******验证码错误********");
            return "/index/login/login0.html";
        }
    }

    /*查看用户名是否存在*/
    @RequestMapping("/checkUser")
    @ResponseBody
    public int checkUser(HttpServletRequest request,SysUser user){
        String checkLogin = request.getParameter("sysuserLogin");
        SysUser sysUser = sysUserService.checkLogin(checkLogin);
        int result = 0;
        if(sysUser == null){
        }else{
            result = 1;    //存在
        }
        return result;
    }

    /*房间页面*/
    @RequestMapping("/indexMain")
    public String indexMain(){
        return "index/main.html";
    }

    /*房间页面*/
    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request,HttpSession session,Model model){
        session = request.getSession();
        if(session.getAttribute("sessionCode") == null || session.getAttribute("sessionCode") == ""){
            return "redirect:/exitLogin";
        }
        model.addAttribute("sysuser",session.getAttribute("sysuser"));
        model.addAttribute("userFile",session.getAttribute("userFile"));
        return "/index.html";
    }
}
