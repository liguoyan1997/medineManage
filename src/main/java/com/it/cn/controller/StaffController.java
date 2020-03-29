package com.it.cn.controller;

import com.it.cn.entity.SysRole;
import com.it.cn.entity.Staff;
import com.it.cn.service.StaffService;
import com.it.cn.service.StaffService;
import com.it.cn.util.constants.ReturnStatusConstant;
import com.it.cn.util.page.Page;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import com.it.cn.util.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/staff")
public class StaffController {

    private final static String PREVIX = "/staff/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "staffList.html";
    }

    @Autowired
    private StaffService staffService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(Staff staff, HttpServletRequest request) {
        Page<Staff> page = staffService.findPage(new Page(request), staff);
        return page;
    }

    @RequestMapping("/form")
    public String Form(Staff staff, Model model, HttpServletRequest request) {
        String op = request.getParameter("op");
        if(staff.getStaffid() != null){
            staff = staffService.get(staff.getStaffid());
        }
        model.addAttribute("staff",staff);
        model.addAttribute("op",op);
        return PREVIX + "staffForm.html";
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public Staff save(@Valid Staff staff, BindingResult result){
        if(result.hasErrors()){
           staff = ValidatorUtil.toValidator(staff,result);
           return staff;
        }
        staffService.save(staff);
        return staff;
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(Staff staff){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            staffService.delete(staff.getStaffid());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            log.error("删除职工出错：",e);
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        staffService.delete(id);
    }*/

    @RequestMapping("/view")
    public String view(Staff staff,Model model){
        staff = staffService.get(staff.getStaffid());
        model.addAttribute("staff",staff);
        model.addAttribute("op","view");
        return PREVIX + "staffForm.html";
    }
}
