package com.it.cn.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.it.cn.entity.Medicine;
import com.it.cn.entity.Salesorder;
import com.it.cn.entity.Stock;
import com.it.cn.service.MedicineService;
import com.it.cn.service.SalesorderService;
import com.it.cn.service.StockService;
import com.it.cn.util.excel.ExcelUtil;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/medicine")
@Controller
public class ExcelController {
    @Autowired
    private MedicineService medicineService;

    @Autowired
    private SalesorderService salesorderService;

    @Autowired
    private StockService stockService;

    /**
     * 将数据展示表导出到Excel表
     * @return
     */
    @RequestMapping(value = "/exportToExcel")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public String importExcel(Salesorder salesorder, Medicine medicine, Stock stock, HttpServletRequest request) throws Exception {
        String params = request.getParameter("excelExport");
        if(params != null){
            ExportParams exportParams = new ExportParams();
            if(params.equals("one")){
                exportParams.setSheetName("销售单");
                ExcelUtil.exportExcel("D://medicineManager//销售单.xls",exportParams,Salesorder.class,salesorderService.findList(salesorder));
            }else if(params.equals("two")){
                exportParams.setSheetName("进货单");
                ExcelUtil.exportExcel("D://medicineManager//进货单.xls",exportParams,Medicine.class,medicineService.findList(medicine));
            }else if(params.equals("three")){
                exportParams.setSheetName("库存单");
                ExcelUtil.exportExcel("D://medicineManager//库存单.xls",exportParams,Stock.class,stockService.findList(stock));
            }
        }
        return "success";
    }
}
