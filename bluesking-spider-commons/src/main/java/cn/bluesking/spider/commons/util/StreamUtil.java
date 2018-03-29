package cn.bluesking.spider.commons.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流操作工具类
 * 
 * @author 随心
 *
 */
public final class StreamUtil {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(StreamUtil.class);
    
    /**
     * 从输入流中获取字符串
     * 
     * @param is [InputStream]输入流
     * @return [String]获取输入流内容对应的字符串
     */
    public static String getString(InputStream is) {
        StringBuilder sBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null) {
                sBuilder.append(line);
            }
            reader.close();
        } catch (Exception e) {
            _LOG.error("获取字符串出错！", e);
            throw new RuntimeException(e);
        }
        return sBuilder.toString();
    }

    /**
     * 将输入流复制到输出流
     * 
     * @param inputStream [InputStream]输入流
     * @param outputStream [OutputStream]输出流
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buf = new byte[4 * 1024];
            while((length = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            _LOG.error("复制流失败！", e);
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                _LOG.error("关闭流失败！", e);
            }
        }
    }
    
    /**
     * 从输入流中获取字符串,每行数据后面加上一个\n回车符
     * @param is [InputStream]输入流
     * @return
     */
    public static String getStringWithLF(InputStream is) {
        StringBuilder sBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            reader.close();
        } catch (Exception e) {
            _LOG.error("获取字符串出错！", e);
            throw new RuntimeException(e);
        }
        return sBuilder.toString();
    }
    
}
