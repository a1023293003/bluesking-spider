package cn.bluesking.spider.commons.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import cn.bluesking.spider.commons.util.ClassUtil;

/**
 * 类操作助手类
 * 
 * @author 随心
 *
 */
public final class ClassHelper {
	
	/**
	 * 定义类集合(用于存放所加载的类)
	 */
	private static final Set<Class<?>> CLASS_SET;
	
	static {
		String basePackage = ClassHelper.class.getPackage().getName();
		basePackage = basePackage.substring(0, basePackage.lastIndexOf("."));
		basePackage = "cn.bluesking.spider";
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * 获取应用包名下所有类
	 * @return
	 */
	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}
	
	/**
	 * 获取应用包名下某父类(或接口)的所有子类(或实现类)
	 * @param superClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls : CLASS_SET) {
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取应用包名下带有某注解的所有类
	 * @param clazz [Class<? extends Annotation>]指定注解类的class
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls : CLASS_SET) {
			if(cls.isAnnotationPresent(annotationClass)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

}
