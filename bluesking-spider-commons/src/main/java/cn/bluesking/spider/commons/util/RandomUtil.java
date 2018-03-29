package cn.bluesking.spider.commons.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 * 
 * @author 随心
 *
 */
public final class RandomUtil {

    /** 随机数生成器 */
    private static final Random RANDOM = new Random();
    
    /**
     * 在传入参数范围内随机返回一个int32的整数,闭合下限,开上限
     * <pre>
     * 可能返回最小值,但不会返回最大值
     * 传入参数无顺序要求
     * </pre>
     * 
     * @param min [int]下限
     * @param max [int]上限
     * @return
     */
    public static int randomInt(int min, int max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            min ^= max;
            max ^= min;
            min ^= max;
        }
        return min + RANDOM.nextInt(max - min);
    }
    
    /** 字符表,包含大写字母、小写字母和数字 */
    private static char UPPER_LOWER_NUMBER[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', 
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b','c', 'd', 'e', 
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 
            'v', 'w', 'x', 'y', 'z', '0', '1'};//最后又重复两个0和1，因为需要凑足数组长度为64
    
    /**
     * 生成指定长度的随机字符串
     * 
     * @param length [int]生成字符串长度
     * @return
     */
    public static String randomString(int length) {
        if (length > 0) {
            int index = 0;
            char[] temp = new char[length];
            int num = RANDOM.nextInt();
            int len = length % 5;
            for (int i = 0; i < len; i++) {
                // 取后面六位,记得对应的二进制是以补码形式存在的.
                temp[index ++] = UPPER_LOWER_NUMBER[num & 63];
                num >>= 6;// 63的二进制为:111111
                // 为什么要右移6位?因为数组里面一共有64个有效字符.
                // 为什么要除5取余?因为一个int型要用4个字节表示,也就是32位.
            }
            len = length / 5;
            for (int i = 0; i < len; i++) {
                num = RANDOM.nextInt();
                for (int j = 0; j < 5; j++) {
                    temp[index ++] = UPPER_LOWER_NUMBER[num & 63];
                    num >>= 6;  
                }
            }
            return new String(temp, 0, length);
        }  
        else if (length == 0) {
            return "";
        }
        else {
            throw new IllegalArgumentException("随机生成字符串长度不能为负数!length = " + length);
        }
    }
    
    /**
     * 在传入参数范围内随机返回一个int64的整数,闭合下限,开上限
     * <pre>
     * 可能返回最小值,但不会返回最大值
     * 传入参数无顺序要求
     * </pre>
     * 
     * @param min [int]下限
     * @param max [int]上限
     * @return
     */
    public static long randomLong(long min, long max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            min ^= max;
            max ^= min;
            min ^= max;
        }
        return min + RANDOM.nextLong() % (max - min);
    }
    
    /** 国内所有手机号码的网络标识符 */
    private static final int[] PHONE_PREFIX = {139, 138, 137, 136, 135, 
            134, 159, 158, 157, 150, 151, 152, 188, 187, 182, 183, 184, 
            178, 130, 131, 132, 156, 155, 186, 185, 176, 133, 153, 189, 
            180, 181, 177, 199};
    
    /**
     * 随机手机号码
     * @return
     */
    public static String randomPhone() {
        return PHONE_PREFIX[RANDOM.nextInt(PHONE_PREFIX.length)] + "" + randomInt(12345678, 98765432);
    }
    
    /** 常见邮箱后缀 */
    private static final String[] EMAIL_SUFFIX = {"@gmail.com", "@yahoo.com", "@msn.com", 
            "@hotmail.com", "@aol.com", "@ask.com", "@live.com", "@qq.com", "@0355.net", 
            "@163.com", "@163.net", "@263.net", "@3721.net", "@yeah.net", "@googlemail.com", 
            "@mail.com", "@hotmail.com", "@msn.com", "@yahoo.com", "@gmail.com", "@aim.com", 
            "@aol.com", "@mail.com", "@walla.com", "@inbox.com", "@126.com", "@163.com", 
            "@sina.com", "@21cn.com", "@sohu.com", "@yahoo.com.cn", "@tom.com", "@qq.com", 
            "@etang.com", "@eyou.com", "@56.com", "@x.cn", "@chinaren.com", "@sogou.com", 
            "@citiz.com", "@hongkong.com", "@ctimail.com", "@hknet.com", "@netvigator.com", 
            "@mail.hk.com", "@swe.com.hk", "@ITCCOLP.COM.HK", "@BIZNETVIGATOR.COM", "@SEED.NET.TW", 
            "@TOPMARKEPLG.COM.TW", "@PCHOME.COM.TW", "@cyber.net.pk", "@libero.it", "@webmail.co.za", 
            "@xtra.co.nz", "@pacific.net.sg", "@FASTMAIL.FM", "@emirates.net.ae", "@eim.ae", 
            "@net.sy", "@scs-net.org", "@mail.sy", "@ttnet.net.tr", "@superonline.com", 
            "@yemen.net.ye", "@y.net.ye", "@cytanet.com.cy", "@aol.com", "@netzero.net", 
            "@twcny.rr.com", "@comcast.net", "@warwick.net", "@comcast.net", "@cs.com", 
            "@verizon.net", "@bigpond.com", "@otenet.gr", "@cyber.net.pk", "@vsnl.com", 
            "@wilnetonline.net", "@cal3.vsnl.net.in", "@rediffmail.com", 
            "@sancharnet.in@NDF.VSNL.NET.IN", "@DEL3.VSNL.NET.IN", "@xtra.co.nz", "@yandex.ru", 
            "@t-online.de", "@NETVISION.NET.IL", "@BIGPOND.NET.AU", "@MAIL.RU EV", "@ADSL.LOXINFO.COM", 
            "@SCS-NET.ORG", "@EMIRATES.NET.AE", "@QUALITYNET.NET", "@ZAHAV.NET.IL", "@netvision.net.il", 
            "@xx.org.il", "@hn.vnn.vn", "@hcm.fpt.vn", "@hcm.vnn.vn", "@candel.co.jp", "@zamnet.zm", 
            "@amet.com.ar", "@infovia.com.ar", "@mt.net.mk", "@sotelgui.net.gn", "@prodigy.net.mx", 
            "@citechco.net", "@xxx.meh.es", "@terra.es", "@wannado.fr", "@mindspring.com", 
            "@excite.com", "@africaonline.co.zw", "@samara.co.zw", "@zol.co.zw", "@mweb.co.zw", 
            "@aviso.ci", "@africaonline.co.ci", "@afnet.net", "@mti.gov.na", "@namibnet.com", 
            "@iway.na", "@be-local.com", "@infoclub.com.np", "@mos.com.np", "@ntc.net.np", 
            "@kalianet.to", "@mail.ru", "@dnet.net.id", "@sinos.net", "@westnet.com.au", 
            "@gionline.com.au", "@cairns.net.au", "@mynet.com", "@mt.net.mk", "@indigo.ie", 
            "@eircom.net", "@sbcglobal.net", "@ntlworld.com", "@nesma.net.sa", "@mail.mn", 
            "@tiscali.co.uk", "@caron.se", "@vodamail.co.za", "@eunet.at", "@spark.net.gr", 
            "@swiszcz.com", "@club-internet.fr", "@walla.com"};

    /**
     * 随机生成一个邮箱地址
     * 
     * @return
     */
    public static String randomEmail() {
        return randomString(randomInt(4, 15)) + EMAIL_SUFFIX[randomInt(0, EMAIL_SUFFIX.length)];
    }
    
    /**
     * 随机生成一个全局唯一标识符
     * 
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 随机生成一个布尔值
     * 
     * @return
     */
    public static boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }
    
    /**
     * 返回一个介于0~1之间的随机双精度浮点数
     * 
     * @return
     */
    public static double randomDouble() {
        return RANDOM.nextDouble();
    }
    
    /**
     * 在传入参数范围内随机返回一个双精度浮点数,闭合下限,开上限
     * <pre>
     * 可能返回最小值,但不会返回最大值
     * 传入参数无顺序要求
     * </pre>
     * 
     * @param min [double]下限
     * @param max [double]上限
     * @return
     */
    public static double randomDouble(double min, double max) {
        double range = max - min;
        return min + range * randomDouble();
    }
    
}
