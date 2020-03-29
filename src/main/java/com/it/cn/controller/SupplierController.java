package com.it.cn.controller;


import com.it.cn.entity.Supplier;
import com.it.cn.service.SupplierService;
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

/**
 * 123Controller
 *
 * @author 123
 * @version 1.0
 * @date 2019-08-18 16:32:41
 */
@Controller
@RequestMapping(value = "/supplier")
public class SupplierController {

    private final static String PREVIX = "/supplier/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "supplierList.html";
    }

    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(Supplier supplier, HttpServletRequest request) {
        Page<Supplier> page = supplierService.findPage(new Page(request), supplier);
        return page;
    }

    @RequestMapping("/form")
    public String Form(Supplier supplier, Model model, HttpServletRequest request) {
        String op = request.getParameter("op");
        if(supplier.getId() != null){
            supplier = supplierService.get(supplier.getId());
        }
        model.addAttribute("supplier",supplier);
        model.addAttribute("op",op);
        return PREVIX + "supplierForm.html";
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public Supplier save(Supplier supplier){
        supplierService.save(supplier);
        return supplier;
    }

    @RequiresRoles(value={"admin"},logical = Logical.OR)
    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(Supplier supplier){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            supplierService.delete(supplier.getId());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        supplierService.delete(id);
    }*/

    @RequestMapping("/view")
    public String view(Supplier supplier,Model model){
        supplier = supplierService.get(supplier.getId());
        model.addAttribute("supplier",supplier);
        model.addAttribute("op","view");
        return PREVIX + "supplierForm.html";
    }
}