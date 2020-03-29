package com.it.cn.util.validator;

import com.it.cn.base.BaseEntity;
import com.it.cn.entity.Staff;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;

@Configuration
public class ValidatorUtil {

    public static <T extends BaseEntity<T>> T toValidator (@Valid T t, BindingResult result){
        StringBuffer mes = new StringBuffer();
        if(result.hasErrors()){
            for (ObjectError error : result.getFieldErrors()) {
                mes.append(error.getDefaultMessage());
                mes.append("*");
                System.out.println(error.getDefaultMessage());
            }
            t.setMessage(mes.toString());
            return t;
        }
        return t;
    }
}
