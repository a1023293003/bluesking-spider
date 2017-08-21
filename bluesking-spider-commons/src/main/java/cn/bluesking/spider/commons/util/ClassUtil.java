package cn.bluesking.spider.commons.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类操作工具类
 * 
 * @author 随心
 *
 */
public final class ClassUtil {

	/**
	 * slf4j日志配置
	 */
	private static final Logger _LOG = LoggerFactory.getLogger(ClassUtil.class);
	
	/**
	 * 获取一个类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		// 获取当前线程的类加载器
		return Thread.currentThread().getContextClassLoader();
	}
	
	/**
	 * 加载类
	 * 
	 * @param className [String]类名
	 * @return [Class<?>]类对象
	 */
	public static Class<?> loadClass(String className) {
		return loadClass(className, false);
	}
	
	/**
	 * 加载类
	 * 
	 * @param className [String]类名
	 * @param isInitialized [boolean]是否初始化
	 * @return [Class<?>]类对象
	 */
	public static Class<?> loadClass(String className, boolean isInitialized) {
		try {
			return Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			_LOG.error("加载%s类失败！", className);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取指定包名下的所有类
	 * 
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packageName) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		try {
			// TODO System.getProperty("file.separator")当前系统文件分隔符
			Enumeration<URL> urls = getClassLoader().getResources(
					packageName.replace(".", "/"));
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if(url != null) {
					// 获取url协议:protocol://host:port/path?query#fragment
					String protocol = url.getProtocol();
					// 区分文件和引用包的类
					if("file".equals(protocol)) {
						String packagePath = url.
								toURI().getPath().replaceAll("%20", " "); // 这里先转成URI再取路径
						addClass(classSet, packagePath, packageName);
					} else if("jar".equals(protocol)) {
						JarURLConnection jarURLConnection = 
								(JarURLConnection) url.openConnection();
						if(jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if(jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								while(jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if(jarEntryName.endsWith(".class")) {
										String className = jarEntryName.substring(0, 
												jarEntryName.lastIndexOf(".")).
												replaceAll("/", ".");
										doAddClass(classSet, className);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			_LOG.error("获取Class Set集合失败！", e);
			throw new RuntimeException(e);
		}
		return classSet;
	}
	
	/**
	 * 添加指定包中所有类到集合中
	 * 
	 * @param classSet [Set<Class<?>>]类集合
	 * @param packagePath [String]包路径
	 * @param packageName [String]包名
	 */
	private static void addClass(Set<Class<?>> classSet, String packagePath, 
			String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class")) 
						|| file.isDirectory();
			}
		});
		if(ArrayUtil.isNotEmpty(files)) {
			for(File file : files) {
				String fileName = file.getName();
				if(file.isFile()) {
					String className = fileName.substring(0, fileName.lastIndexOf("."));
					if(StringUtil.isNotEmpty(packageName)) {
						className = packageName + "." + className;
					}
					doAddClass(classSet, className);
				} else {
					String subPackagePath = fileName;
					if(StringUtil.isNotEmpty(packagePath)) {
						subPackagePath = packagePath + "/" + subPackagePath;
					}
					String subPackageName = fileName;
					if(StringUtil.isNotEmpty(packageName)) {
						subPackageName = packageName + "." + subPackageName;
					}
					addClass(classSet, subPackagePath, subPackageName);
				}
			}
		}
	}
	
	/**
	 * 添加指定类到集合中
	 * 
	 * @param classSet [Set<Class<?>>]类集合
	 * @param className [String]待添加类名
	 */
	private static void doAddClass(Set<Class<?>> classSet, String className) {
		Class<?> cls = loadClass(className, false);
		classSet.add(cls);
	}
}
