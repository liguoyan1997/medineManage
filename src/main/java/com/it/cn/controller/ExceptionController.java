package com.it.cn.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/")
@Controller
@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(org.apache.shiro.authz.AuthorizationException.class)
//    public String handleException(RedirectAttributes redirectAttributes, Exception exception, HttpServletRequest request) {
//        redirectAttributes.addFlashAttribute("message", "抱歉！您没有权限执行这个操作，请联系管理员！");
//        String url = WebUtils.getRequestUri(request);
//        return "redirect:/" + url.split("/")[1];	// 请求的规则 : /page/operate
//    }

    @ExceptionHandler(UnauthorizedException.class)
//    @ResponseBody
    public String defaultExceptionHandler(HttpServletRequest req,Exception e){
        return "/expection/expection.html";
    }


}
