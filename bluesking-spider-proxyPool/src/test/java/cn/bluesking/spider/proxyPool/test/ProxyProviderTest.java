package cn.bluesking.spider.proxyPool.test;

import cn.bluesking.spider.proxyPool.TaskExecutor;

public class ProxyProviderTest {

    public static void testExecute() {
        TaskExecutor.execute();
        try {
            while (true) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        testExecute();
    }
    
}
