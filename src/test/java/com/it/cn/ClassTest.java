package com.it.cn;

import com.it.cn.entity.SysUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class clazz = Class.forName("com.it.cn.entity.SysUser");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class,String.class);
        Object o = constructor.newInstance("13", "扎实那");
        Field field = clazz.getDeclaredField("sysid");
        Method getid = clazz.getDeclaredMethod("getid", String.class, String.class);
        Object stu = clazz.newInstance();
        field.setAccessible(true);
        Object invoke = getid.invoke(stu, "12", "张三");
        field.set(stu,"13");
        Object o1 = field.get(stu);
    }
}
