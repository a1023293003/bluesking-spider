package cn.bluesking.spider.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * 
 * @author 随心
 *
 */
public class ReflectionUtil {

    /**
     * 获取目标类中被指定注解注解的方法
     * 
     * @param targetClass     [Class<?>]目标类Class
     * @param annotationClass [Class<? extends Annotation>]注解类Class
     * @return [List<Method>]返回目标类中被指定注解注解的方法数组
     */
    public static List<Method> getMethodByAnnotation(
            Class<?> targetClass, Class<? extends Annotation> annotationClass) {
        Method[] methods = targetClass.getDeclaredMethods();
        List<Method> result = new ArrayList<Method>(methods.length);
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                result.add(method);
            }
        }
        return result;
    }
    
    /**
     * 调用静态无参方法
     * 
     * @param method [Method]目标方法
     * @throws Exception 
     */
    public static void invokeStaticMethodWithoutArgs(Method method) 
            throws Exception {
        try {
            method.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw e;
        }
    }
    
    /**
     * 调用静态方法
     * 
     * @param method [Method]目标方法
     * @param args   [Object[]]方法参数
     * @throws Exception 
     */
    public static void invokeStaticMethod(Method method, Object... args) 
            throws Exception {
        try {
            method.invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw e;
        }
    }
    
}
