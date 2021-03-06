package cn.bluesking.spider.commons.util;

import java.util.Random;

/**
 * 数学方法工具类
 * 
 * @author 随心
 *
 */
public final class MathUtil {

    /** 随机数生成器 */
    private static final Random randomGenerator = new Random();
    
    /**
     * 获取一个介于start到end之间的随机数,包含start但不包含end
     * 
     * @param start [int]随机数下限
     * @param end   [int]随机数上限
     * @return
     */
    public static int getRandom(int start, int end) {
        int diff = end - start;
        if(diff == 0) return start;
        int sign = diff < 0 ? -1 : 1;
        return start + randomGenerator.nextInt(diff * sign) * sign;
    }
    
    /**
     * 获取一个介于start到end之间的随机数,包含start但不包含end
     * 
     * @param start [long]随机数下限
     * @param end   [long]随机数上限
     * @return
     */
    public static Long getRandom(long start, long end) {
        long diff = end - start;
        if(diff == 0) return start;
        int sign = diff < 0 ? -1 : 1;
        diff *= sign;
        long randLong = randomGenerator.nextLong();
        randLong = randLong < 0 ? randLong * -1 : randLong;
        return start + (randLong % diff) * sign;
    }
    
}
