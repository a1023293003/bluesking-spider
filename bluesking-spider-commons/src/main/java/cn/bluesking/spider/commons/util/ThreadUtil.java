package cn.bluesking.spider.commons.util;

/**
 * 线程操作工具类
 * 
 * @author 随心
 *
 */
public final class ThreadUtil {

    /**
     * 随机暂停线程,最终随机时间介于[baseMillis, baseMillis + randomRange)
     * 
     * @param baseMillis  [long]基础随机毫秒数
     * @param randomRange [long]额外随机毫秒数范围
     * @throws InterruptedException 
     */
    public static void randomSleep(long baseMillis, long randomRange) throws InterruptedException {
        if (randomRange == 0) {
            Thread.sleep(baseMillis);
        } else {
            MathUtil.getRandom(0, randomRange);
            Thread.sleep(baseMillis + randomRange);
        }
    }
}
