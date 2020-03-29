package com.it.cn.controller;


import com.it.cn.entity.*;
import com.it.cn.service.*;
import com.it.cn.util.constants.ReturnStatusConstant;
import com.it.cn.util.page.Page;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 123Controller
 *
 * @author 123
 * @version 1.0
 * @date 2019-08-18 16:32:41
 */
@Controller
@RequestMapping(value = "/stock")
public class StockController {

    private final static String PREVIX = "/stock/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "stockList.html";
    }

    @RequestMapping("/Overdue")
    public String indexOverdue() {
        return PREVIX + "stockOverdueList.html";
    }

    @Autowired
    private StockService stockService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SalesorderService salesorderService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(Stock stock, HttpServletRequest request) {
        Page<Stock> page = stockService.findPage(new Page(request), stock);
        return page;
    }

    @RequestMapping("/Overduelist")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page OverdueList(Stock stock, HttpServletRequest request) {
        Page<Stock> page = stockService.findOverduePage(new Page(request), stock);
        return page;
    }

    @RequestMapping("/form")
    public String Form(Stock stock, Category category,Medicine medicine, Supplier supplier, Model model, HttpServletRequest request) {
        String op = request.getParameter("op");
        if(stock.getId() != null){
            stock = stockService.get(stock.getId());
        }
        List<Medicine> medicines = medicineService.findListID(medicine);
        List<Supplier> suppliers = supplierService.findList(supplier);
        List<Category> categorys = categoryService.findList(category);
        model.addAttribute("medicines",medicines);
        model.addAttribute("suppliers",suppliers);
        model.addAttribute("categorys",categorys);
        model.addAttribute("stock",stock);
        model.addAttribute("op",op);
        return PREVIX + "stockForm.html";
    }

    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
    @RequestMapping("/save")
    @ResponseWrapper(ResponseCode.SUCCESS_SAVE)
    public Stock save(Stock stock){
        stockService.save(stock);
        return stock;
    }

    @RequestMapping("/sellSave")
    @ResponseWrapper
    public ReturnStatusConstant sellSave(Stock stock, Salesorder salesorder, HttpSession session){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            //库存售出
            int num = stock.getMnum();
            stock = stockService.get(stock.getId());
            String sysName = (String) session.getAttribute("sysuser");
            int resultnum = stock.getMnum() - num;
            stock.setMnum(resultnum);
            stockService.update(stock);
            //销售单生成
            salesorder = new Salesorder(stock.getMid(),stock.getMname(),num,stock.getMprice(),sysName);
            salesorderService.save(salesorder);
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    @RequestMapping("/delete")
    @ResponseWrapper
    public ReturnStatusConstant delete(Stock stock){
        ReturnStatusConstant returnStatusConstant =  new ReturnStatusConstant();
        try{
            stockService.delete(stock.getId());
            returnStatusConstant.setCode(ReturnStatusConstant.SUCCESS);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_MESSAGE);
        }catch (Exception e){
            returnStatusConstant.setCode(ReturnStatusConstant.FAIL);
            returnStatusConstant.setMessage(ReturnStatusConstant.SUCCESS_FAIL);
        }
        return returnStatusConstant;
    }

    /*public void delete(@RequestParam("sysid") String id){
        stockService.delete(id);
    }*/

    @RequestMapping("/view")
    public String view(Stock stock,Model model,Category category,Medicine medicine, Supplier supplier){
        stock = stockService.get(stock.getId());
        List<Medicine> medicines = medicineService.findListID(medicine);
        List<Supplier> suppliers = supplierService.findList(supplier);
        List<Category> categorys = categoryService.findList(category);
        model.addAttribute("medicines",medicines);
        model.addAttribute("suppliers",suppliers);
        model.addAttribute("categorys",categorys);
        model.addAttribute("stock",stock);
        model.addAttribute("op","view");
        return PREVIX + "stockForm.html";
    }

    /*
    * 入库时 根据药品编号返显其他值
    * */
    @RequestMapping("/midDisplay")
    @ResponseBody
    public Stock updatemysqlPsd(HttpServletRequest request,Stock stock){
        String mid = request.getParameter("mid");
        Medicine medicine = medicineService.get(mid);
        stock = new Stock(medicine.getMname(), medicine.getMpri(),medicine.getMnum(),medicine.getMcon(),medicine.getJtime(),medicine.getMusetime(),medicine.getMcate());
        return stock;
    }
}