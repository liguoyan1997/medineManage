package com.it.cn.util.beetl;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExt implements WebRenderExt {

    /**
     * 这里可以配置模板 全局变量 如：template.binding("version","1.0");
     */
    @Override
    public void modify(Template template, GroupTemplate gtemplate, HttpServletRequest request,
                       HttpServletResponse response) {

        template.binding("version", "1.0");
    }

}
