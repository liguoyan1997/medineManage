package com.it.cn.controller;


import com.it.cn.entity.Medicine;
import com.it.cn.entity.Salesorder;
import com.it.cn.service.MedicineService;
import com.it.cn.service.SalesorderService;
import com.it.cn.util.constants.ReturnStatusConstant;
import com.it.cn.util.page.Page;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 123Controller
 *
 * @author 123
 * @version 1.0
 * @date 2019-08-18 16:32:41
 */
@Controller
@RequestMapping(value = "/salesorder")
public class SalesorderController {

    private final static String PREVIX = "/salesorder/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "salesorderList.html";
    }

    @Autowired
    private SalesorderService salesorderService;
    @Autowired
    private MedicineService medicineService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(Salesorder salesorder, HttpServletRequest request) {
        Page<Salesorder> page = salesorderService.findPage(new Page(request), salesorder);
        return page;
    }

    @RequestMapping("/form")
    public String Form(Salesorder salesorder,Medicine medicine, Model model, HttpServletRequest request) {
        String op = request.getParameter("op");
        if(salesorder.getSoid() != null){
            salesorder = salesorderService.get(salesorder.getSoid());
        }
        List<Medicine> medicines = medicineService.findList(medicine);
        model.addAttribute("medicines",medicines);
        model.addAttribute("salesorder",salesorder);
        model.addAttribute("op",op);
        return PREVIX + "salesorderForm.html";
    }

    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public Salesorder save(Salesorder salesorder){
        salesorderService.save(salesorder);
        return salesorder;
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(Salesorder salesorder){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            salesorderService.delete(salesorder.getSoid());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        salesorderService.delete(id);
    }*/

    @RequestMapping("/view")
    public String view(Salesorder salesorder,Model model,Medicine medicine){
        salesorder = salesorderService.get(salesorder.getSoid());
        List<Medicine> medicines = medicineService.findList(medicine);
        model.addAttribute("medicines",medicines);
        model.addAttribute("salesorder",salesorder);
        model.addAttribute("op","view");
        return PREVIX + "salesorderForm.html";
    }
}