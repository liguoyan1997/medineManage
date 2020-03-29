package com.it.cn.controller;

import com.it.cn.entity.Stock;
import com.it.cn.service.MedicineService;
import com.it.cn.service.StockService;
import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/echart")
@Controller
public class EchartController {
    private final static String PREVIX = "/chart/";

    @Autowired
    private StockService stockService;

    @RequestMapping("/mychart")
    public String mychart(){
        return PREVIX + "mychart.html";
    }

    @RequestMapping("/timechart")
    public String timechart(){
        return PREVIX + "timechart.html";
    }

    @RequestMapping(value = "/fxCountData")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Map fxCountData(){
        Map<String, List> map = new HashMap();
        List<Stock> countList = stockService.echart();
        List<Integer> countnumList = stockService.echartnum();
        map.put("countList",countList);
        map.put("countnumList",countnumList);
        return map;
    }

    @RequestMapping(value = "/timeCountData")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Map timeCountData(){
        Map<String, List> map = new HashMap();
        List<Integer> countnumList = stockService.timeechartnum();
        List<Integer> countnumList0 = stockService.timeechartnum0();
        map.put("countnumList",countnumList);
        map.put("countnumList0",countnumList0);
        return map;
    }
}
