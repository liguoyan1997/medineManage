package com.it.cn.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.cn.entity.SysRole;
import com.it.cn.entity.SysUser;
import com.it.cn.service.SysRoleService;
import com.it.cn.service.SysUserService;
import com.it.cn.util.constants.ReturnStatusConstant;
import com.it.cn.util.page.Page;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import com.it.cn.util.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/sysuser")
public class SysUserController {
    private final static String PREVIX = "/sys_user/";

    @Autowired
    private SysUserService userService1;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("")
    public String index() {
       return PREVIX + "sys_userList.html";
    }

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS_QUERY)
    public Page List(SysUser user, HttpServletRequest request,@RequestParam(value = "pn",defaultValue = "1")Integer pn) {
//        PageHelper.startPage(1,6);
//        List<SysUser> list = userService1.findList(user);
//        PageInfo pageInfo = new PageInfo(list);
        Page<SysUser> page = userService1.findPage(new Page(request), user);
        return page;
    }

    @RequiresRoles(value={"admin"})
    @RequestMapping("/form")
    public String Form(@RequestParam("op") String op,SysUser user,Model model,HttpServletRequest request) {
        if(user.getSysid() != null){
            user = userService1.get(user.getSysid());
        }
        SysRole role = new SysRole();
        List<SysRole> roles = sysRoleService.findList(role);
        model.addAttribute("roles",roles);
        model.addAttribute("user",user);
        model.addAttribute("op",op);
        return PREVIX + "sys_userForm.html";
    }

    @RequiresRoles(value={"admin"})
    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public SysUser save(@Valid SysUser user, BindingResult result){
        if(result.hasErrors()){
            user = ValidatorUtil.toValidator(user,result);
            return user;
        }
        userService1.save(user);
        return user;
    }

    @RequestMapping("/resign")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public SysUser resign(@Valid SysUser user, BindingResult result){
        if(result.hasErrors()){
            user = ValidatorUtil.toValidator(user,result);
            return user;
        }
        userService1.save(user);
        return user;
    }

    @RequiresRoles(value={"admin"})
    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(SysUser user){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            userService1.delete(user.getSysid());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("删除信息出错：",e);
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        userService1.delete(id);
    }*/

    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable("id") String id, Model model){
        SysRole role = new SysRole();
        List<SysRole> roles = sysRoleService.findList(role);
        model.addAttribute("roles",roles);
        SysUser user = userService1.get(id);
        model.addAttribute("user",user);
        model.addAttribute("op","view");
        return PREVIX + "sys_userForm.html";
    }

    @RequestMapping("/personMessage")
    public String personMessage(SysUser user,SysRole role, Model model, HttpSession session){
        String loginName = (String) session.getAttribute("sysuser");
        user = userService1.checkLogin(loginName);
        role = sysRoleService.get(user.getRoleId());
        model.addAttribute("sysuser",user);
        model.addAttribute("sysrole",role);
        try {
            model.addAttribute("ip",InetAddress.getLocalHost().getHostAddress());
            model.addAttribute("ipName",InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return PREVIX + "personMessage.html";
    }

    @RequiresRoles(value={"admin"})
    @RequestMapping("/updatePsdHtml")
    public String updatePsdHtml(){
        return PREVIX + "updatePsd.html";
    }

    @RequestMapping("/updatePsd")
    @ResponseBody
    public int updatePsd(HttpServletRequest request,SysUser user,HttpSession session){
        String psd = request.getParameter("sysuserPassWord");
        String login = (String) session.getAttribute("sysuser");
        user = userService1.checkLogin(login);
        String mysqlPsd = user.getSysuserPassWord();
        int result = 0;
        if(psd.equals(mysqlPsd)){
            result = 1;    //存在
        }else{
            result = 0;    //不存在
        }
        return result;
    }

    @RequestMapping("/updatemysqlPsd")
    @ResponseBody
    public int updatemysqlPsd(HttpServletRequest request,SysUser user,HttpSession session){
        String psd = request.getParameter("onNewPassWord");
        String login = (String) session.getAttribute("sysuser");
        user = userService1.checkLogin(login);
        user.setSysuserPassWord(psd);
        int result = 0;
        try {
            userService1.update(user);
            result = 1;
        }catch (Exception e){
            result = 0;
            e.getMessage();
        }
        return result;
    }
}
