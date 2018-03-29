package cn.bluesking.spider.proxyPool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定时任务注解
 * 
 * @author 随心
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledTask {

    /**
     * 定时任务执行时间间隔,单位:毫秒
     * 
     * @return
     */
    long value() default 0L;
}
