package com.it.cn.controller;


import com.it.cn.entity.SysLog;
import com.it.cn.service.SysLogService;
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
@RequestMapping(value = "/sysLog")
@Transactional(rollbackFor = Exception.class)
public class SysLogController {

    private final static String PREVIX = "/sys_log/";

    @RequestMapping("")
    public String index() {
        return PREVIX + "syslogList.html";
    }

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/list")
    @ResponseWrapper(ResponseCode.SUCCESS)
    public Page List(SysLog sysLog, HttpServletRequest request) {
        Page<SysLog> page = sysLogService.findPage(new Page(request), sysLog);
        return page;
    }
}