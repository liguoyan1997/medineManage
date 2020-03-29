package com.it.cn.controller;

import com.it.cn.util.ValidateCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/validateCode")
public class ValidateCodeController {

    //生成验证码
    @RequestMapping("/produce")
    public String validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();

        ValidateCodeUtil vCode = new ValidateCodeUtil(120,40,4,100);
        session.setAttribute("code",vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }
}
