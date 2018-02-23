package cn.bluesking.spider.mobile.lagou.translate;

import cn.bluesking.spider.commons.util.CodecUtil;
import cn.bluesking.spider.commons.util.RegexUtil;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20171227000109839";
    private static final String SECURITY_KEY = "mQDzf2f5yUb6aA_1sPib";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "    /**\r\n" + 
                "     * Lock held on access to workers set and related bookkeeping.\r\n" + 
                "     * While we could use a concurrent set of some sort, it turns out\r\n" + 
                "     * to be generally preferable to use a lock. Among the reasons is\r\n" + 
                "     * that this serializes interruptIdleWorkers, which avoids\r\n" + 
                "     * unnecessary interrupt storms, especially during shutdown.\r\n" + 
                "     * Otherwise exiting threads would concurrently interrupt those\r\n" + 
                "     * that have not yet interrupted. It also simplifies some of the\r\n" + 
                "     * associated statistics bookkeeping of largestPoolSize etc. We\r\n" + 
                "     * also hold mainLock on shutdown and shutdownNow, for the sake of\r\n" + 
                "     * ensuring workers set is stable while separately checking\r\n" + 
                "     * permission to interrupt and actually interrupting.\r\n" + 
                "     */\r\n" + 
                "    private final ReentrantLock mainLock = new ReentrantLock();\r\n" + 
                "\r\n" + 
                "    /**\r\n" + 
                "     * Set containing all worker threads in pool. Accessed only when\r\n" + 
                "     * holding mainLock.\r\n" + 
                "     */\r\n" + 
                "    private final HashSet<Worker> workers = new HashSet<Worker>();\r\n" + 
                "\r\n" + 
                "    /**\r\n" + 
                "     * Wait condition to support awaitTermination\r\n" + 
                "     */\r\n" + 
                "    private final Condition termination = mainLock.newCondition();";
        String comment = RegexUtil.regexAString(query, "\\*\\*\r\n([\\s\\S]+?)    \\*/");
        comment = comment.replaceAll("\r\n +?\\*", "");
        System.out.println(comment);
        String result = api.getTransResult(comment, "auto", "zh");
        result = RegexUtil.regexAString(result, "\"dst\":\"([\\s\\S]+)\"");
        result = CodecUtil.decodeUnicode(result);
        System.out.println(result);
    }

}
