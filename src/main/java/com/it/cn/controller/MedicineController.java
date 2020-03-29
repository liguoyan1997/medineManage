package com.it.cn.controller;


import com.it.cn.entity.Category;
import com.it.cn.entity.Medicine;
import com.it.cn.entity.Medicine;
import com.it.cn.entity.Supplier;
import com.it.cn.service.CategoryService;
import com.it.cn.service.MedicineService;
import com.it.cn.service.MedicineService;
import com.it.cn.service.SupplierService;
import com.it.cn.util.constants.ReturnStatusConstant;
import com.it.cn.util.page.Page;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping(value = "/medicine")
public class MedicineController{

    private final static String PREVIX = "/medicine/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "medicineList.html";
    }

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(Medicine medicine, HttpServletRequest request) {
        Page<Medicine> page = medicineService.findPage(new Page(request), medicine);
        return page;
    }

    @RequestMapping("/form")
    public String Form(Medicine medicine,Category category, Supplier supplier, Model model, HttpServletRequest request) {
        String op = request.getParameter("op");
        if(medicine.getMid() != null){
            medicine = medicineService.get(medicine.getMid());
        }
        List<Supplier> suppliers = supplierService.findList(supplier);
        List<Category> categorys = categoryService.findList(category);
        model.addAttribute("categorys",categorys);
        model.addAttribute("suppliers",suppliers);
        model.addAttribute("medicine",medicine);
        model.addAttribute("op",op);
        return PREVIX + "medicineForm.html";
    }

    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public Medicine save(Medicine medicine){
        medicine.setStatus(0);
        medicineService.insert(medicine);
        return medicine;
    }

    @RequestMapping("/update")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public Medicine update(Medicine medicine){
        medicineService.update(medicine);
        return medicine;
    }

    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(Medicine medicine){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            medicineService.delete(medicine.getMid());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        medicineService.delete(id);
    }*/

    @RequestMapping("/view")
    public String view(Medicine medicine,Category category, Supplier supplier,Model model){
        medicine = medicineService.get(medicine.getMid());
        List<Supplier> suppliers = supplierService.findList(supplier);
        List<Category> categorys = categoryService.findList(category);
        model.addAttribute("categorys",categorys);
        model.addAttribute("suppliers",suppliers);
        model.addAttribute("medicine",medicine);
        model.addAttribute("op","view");
        return PREVIX + "medicineForm.html";
    }
}