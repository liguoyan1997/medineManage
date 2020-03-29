package com.it.cn.controller;


import com.it.cn.entity.SysRole;
import com.it.cn.service.SysRoleService;
import com.it.cn.util.constants.ReturnStatusConstant;
import com.it.cn.util.page.Page;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 123Controller
 *
 * @author 123
 * @version 1.0
 * @date 2019-08-18 16:32:41
 */
@Controller
@RequestMapping(value = "/sysRole")
@Transactional(rollbackFor = Exception.class)
public class SysRoleController {

    private final static String PREVIX = "/sys_role/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "sysroleList.html";
    }

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(SysRole sysRole, HttpServletRequest request) {
        Page<SysRole> page = sysRoleService.findPage(new Page(request), sysRole);
        return page;
    }

    @RequiresRoles(value={"admin"})
    @RequestMapping("/form")
    public String Form(SysRole sysRole, Model model, HttpServletRequest request) {
        String op = request.getParameter("op");
        if(sysRole.getId() != null){
            sysRole = sysRoleService.get(sysRole.getId());
        }
        model.addAttribute("sysRole",sysRole);
        model.addAttribute("op",op);
        return PREVIX + "sysroleForm.html";
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public SysRole save(SysRole sysRole,HttpServletRequest request){
        String op = request.getParameter("op");
        if(op == "add" || op.equals("add")){
            sysRole.setDelflag("0");
            sysRoleService.insert(sysRole);
        }else{
            sysRoleService.update(sysRole);
        }
        return sysRole;
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(SysRole sysRole){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            sysRoleService.delete(sysRole.getId());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        sysRoleService.delete(id);
    }*/

    @RequestMapping("/view")
    public String view(SysRole sysRole,Model model){
        sysRole = sysRoleService.get(sysRole.getId());
        model.addAttribute("sysRole",sysRole);
        model.addAttribute("op","view");
        return PREVIX + "sysroleForm.html";
    }
}