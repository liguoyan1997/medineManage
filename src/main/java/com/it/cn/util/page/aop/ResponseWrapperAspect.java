package com.it.cn.util.page.aop;

import com.it.cn.util.page.converter.restful.ResponseCode;
import com.it.cn.util.page.converter.restful.ResponseWrapper;
import com.it.cn.util.page.converter.restful.ResponseWrapperEntity;
import com.it.cn.util.page.Page;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 统一接口数据返回AOP
 *
 * @author i.yoo
 * @version 2018年9月1日10:43:11
 */
@Configuration
@Aspect
public class ResponseWrapperAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MappingJackson2HttpMessageConverter converter;

    // 定义切点Pointcut
    @Pointcut("@annotation(com.it.cn.util.page.converter.restful.ResponseWrapper)")
    public void excudeService() {
    }

    //环切
    @Around(value = "excudeService()")
    @ResponseBody
    public void doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = pjp.proceed();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        ResponseWrapper wrapper = method.getAnnotation(ResponseWrapper.class);
        ResponseWrapperEntity wrapperEntity = new ResponseWrapperEntity(wrapper.value(), ret);
        if (ret instanceof Page) {
            wrapperEntity = new ResponseWrapperEntity(wrapper.value(), ((Page) ret).getList(), ((Page) ret).getTotal());
        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        converter.write(wrapperEntity, MediaType.APPLICATION_JSON, outputMessage);
        shutdownResponse(response);
    }

    @AfterThrowing(pointcut = "excudeService()", throwing = "error")
    public void handleForException(JoinPoint jp, Throwable error) throws Throwable{
        ResponseWrapperEntity wrapper = new ResponseWrapperEntity(ResponseCode.SYSTEM_ERROR, error.getMessage());
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        logger.error(jp.getSignature().getName() + "-error!", error);
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        converter.write(wrapper, MediaType.APPLICATION_JSON, outputMessage);
        shutdownResponse(response);
    }

    private void shutdownResponse(HttpServletResponse response) throws IOException {
        response.getOutputStream().close();
    }
}
