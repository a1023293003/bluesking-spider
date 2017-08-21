package cn.bluesking.spider.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * 
 * @author 随心
 *
 */
public final class ReflectionUtil {

	/**
	 * slf4j日志配置
	 */
	private static final Logger _LOG = LoggerFactory.getLogger(ReflectionUtil.class);
	
	/**
	 * 创建实例
	 * 
	 * @param cls [Class<?>]带创建实例类Class
	 * @return
	 */
	public static <T> T newInstance(Class<T> cls) {
		T instance;
		try {
			instance = cls.newInstance();
		} catch(Exception e) {
			_LOG.error("创建实例失败！", e);
			throw new RuntimeException(e);
		}
		return instance;
	}
	
	/**
	 * 创建实例
	 * 
	 * @param className [String]带创建实例类Class全路径
	 * @return
	 */
	public static Object newInstance(String className) {
		Object instance;
		try {
			Class<?> cls = Class.forName(className);
			instance = cls.newInstance();
		} catch(Exception e) {
			_LOG.error("创建实例失败！", e);
			throw new RuntimeException(e);
		}
		return instance;
	}
	
	/**
	 * 调用方法
	 * 
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;
		try {
			// 给予访问权限
			method.setAccessible(true);
			result = method.invoke(obj, args);
		} catch (Exception e) {
			_LOG.error("调用方法错误！", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 设置成员变量的值
	 * 
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setField(Object obj, Field field, Object value) {
		try {
			// 给予访问权限
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			_LOG.error("设置成员变量的值失败！", e);
			throw new RuntimeException(e);
		}
	}
}
