package cn.bluesking.spider.commons.util;

import java.text.DecimalFormat;

/**
 * 双精度浮点类型工具类
 * 
 * @author 随心
 *
 */
public final class DoubleUtil {

    /**
     * 截取指定位数的小数位
     * 
     * @param value [double]带操作doouble类型
     * @param num   [int]最多保留多少位小数
     * @return
     */
    public static double format(double value, int num) {
        StringBuilder buf = new StringBuilder("0");
        StringBuilder tmp = new StringBuilder("#0.");
        num --;
        while(num > 0) {
            if ((num & 1) != 0) {
                // 奇数
                tmp.append(buf);
            }
            buf.append(buf);
            num >>= 1;
        }
        DecimalFormat df = new DecimalFormat(tmp.toString());
        return Double.parseDouble(df.format(value));
    }
}
