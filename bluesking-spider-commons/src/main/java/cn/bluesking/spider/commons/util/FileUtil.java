package cn.bluesking.spider.commons.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类
 * 
 * @author 随心
 *
 */
public final class FileUtil {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取真实文件名(自动去掉文件路径)
     * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }
    
    /**
     * 创建文件
     * 
     * @param filePath [String]文件路径
     * @return [File]方法执行成功返回创建成功的文件对象,方法执行失败抛出运行异常
     */
    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if(!parentDir.exists()) {
                FileUtils.forceMkdir(parentDir);
            }
        } catch (Exception e) {
            _LOG.error("创建文件失败！", e);
            throw new RuntimeException(e);
        }
        return file;
    }
    
    
}
