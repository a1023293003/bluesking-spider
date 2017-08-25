package cn.bluesking.spider.proxyTool;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.helper.ClassHelper;
import cn.bluesking.spider.commons.util.CollectionUtil;

/**
 * 代理生产者
 * 
 * @author 随心
 *
 */
public class ProxyProvider {

	/** slf4j日志配置 */
	private static final Logger _LOG = LoggerFactory.getLogger(ProxyProvider.class);
	
	/** 用于存储可用代理的阻塞队列 */
	private static final BlockingQueue<Proxy> proxyQueue = new LinkedBlockingQueue<Proxy>();
	
	/** 获取代理实现类对象链表头节点 */
	private static Entry header = null;
	
	/** 获取代理类对象list */
	private static List<ProxyGetter> getterList = null;
	
	/** 代理获取类数量 */
	private static int getterSize = -1;
	
	private static Thread timerThread = null;
	
	static {
		// 获取代理类class集合
		Set<Class<?>> getterSet = ClassHelper.getClassSetBySuper(ProxyGetter.class);
		// 代理获取类数量
		getterSize = getterSet.size();
		if(CollectionUtil.isNotEmpty(getterSet)) {
			// 获取代理类对象集合
			getterList = new ArrayList<ProxyGetter>(getterSet.size());
			try {
				for(Class<?> cls : getterSet) {
					ProxyGetter pg = (ProxyGetter) cls.newInstance();
					getterList.add(pg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 排序
			Collections.sort(getterList, new Comparator<ProxyGetter>() {
				@Override
				public int compare(ProxyGetter o1, ProxyGetter o2) {
					return o1.getInterval() <= o2.getInterval() ? -1 : 1;
				}
			});
		}
	}
	
	/**
	 * 获取代理队列元素个数
	 * @return
	 */
	public static int size() {
		return proxyQueue.size();
	}
	
	/**
	 * 往队列末尾代理对象
	 * @param proxy [Proxy]待插入代理对象
	 */
	public static void add(Proxy proxy) {
		proxyQueue.add(proxy);
	}
	
	/** 等待阻塞队列时间,单位:秒 */
	private static final int waitTimeForQueue = 2;
	
	/**
	 * 获取队列头的代理对象
	 * @return [Proxy]代理对象
	 */
	public static Proxy poll() {
		try {
			// 当队列为空时,获取代理的线程会一直堵塞
			return proxyQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 初始化
	 */
	private static void init() {
		// 初始化获取代理对象链表头
		header = new Entry(null, null, null);
		// 构建链表
		ProxyGetter getter = getterList.get(0);
		// 第一个节点等待时间为间隔时间
		Entry lastNode = new Entry(getter, null, getter.getInterval());
		_LOG.debug("第0个节点[" + lastNode.toString() + "]");
		header.nextNode = lastNode; // 存储当前节点的上一个节点
		Entry node; // 存储当前节点
		// 之后节点的等待时间为 (间隔时间 - 上一个节点的间隔时间)
		for(int i = 1; i < getterList.size(); i ++) {
			getter = getterList.get(i);
			node = new Entry(getter, null, getter.getInterval() - lastNode.getter.getInterval());
			lastNode.nextNode = node;
			lastNode = node;
			_LOG.debug("第" + i + "个节点[" + lastNode.toString() + "]");
		}
	}
	
	/**
	 * 执行定时器,开始代理爬取
	 */
	public static synchronized void execute() {
		// 有代理获取类才启动定时器
		if(getterSize > 0) {
			// 初始化
			init();
			// 先把任务执行一遍
			for(ProxyGetter getter : getterList) {
				newGetterThread(getter).start();
			}
			// 新开一个进程定时执行代理爬取任务
			if(timerThread == null || !timerThread.isAlive()) {
				// 新开一个线程作为定时器
				timerThread = new Thread() {
					public void run() {
						long waitTime = 0;
						try {
							while(!isInterrupted()) {
								waitTime = header.nextNode.waitExecuteTime;
								Thread.sleep(waitTime);
								// 获取链表头的代理获取类对象
								final ProxyGetter getter = pollFirstNode();
								newGetterThread(getter).start();
							}
						} catch(InterruptedException e) {
							_LOG.debug("定时器从阻塞中退出...");
						}
						_LOG.info(this.getName() + "定时器已终止...");
					}
				};
			} // 定时器线程为空才创建一个新的线程对象
			_LOG.info("启动定时器...");
			timerThread.start(); // 启动
		}
	}
	
	/** 管理代理爬取线程池 */
	private static List<Thread> threadPool = new ArrayList<Thread>();
	
	/** 代理队列最大代理数 */
	private static final int QUEUE_MAX_SIZE = 250;
	
	/**
	 * 返回一个执行代理获取对象方法的线程
	 * @param getter [ProxyGetter]代理获取对象
	 * @return
	 */
	private static Thread newGetterThread(ProxyGetter getter) {
		Thread thread =  new Thread() {
			@Override
			public void run() {
				if(proxyQueue.size() < QUEUE_MAX_SIZE) {
					getter.getProxys(); // 执行代理爬取任务
				}
				// 从线程队列中移除
				threadPool.remove(this);
			}
		};
		threadPool.add(thread);
		return thread;
	}
	
	/**
	 * 终止定时器
	 */
	public static synchronized void interrupt() {
		// 终止定时器
		if(timerThread != null) {
			timerThread.interrupt();
		}
		// 终止线程池
		for(Thread thread : threadPool) {
			if(thread.isAlive()) {
				thread.interrupt();
			}
		}
		// 释放线程池
		threadPool.clear();
	}
	
	/**
	 * 取出第一个任务节点
	 * @return
	 */
	private static synchronized ProxyGetter pollFirstNode() {
		if(getterSize <= 0) return null;
		// 用于寻找当前节点插入位置和计算等待时间
		long sumTime = 0;
		// 取出并删除首节点
		Entry firstNode = header.nextNode;
		header.nextNode = firstNode.nextNode;
		// 遍历链表,找到合适的插入位置
		Entry node = header;
		while(node.nextNode != null) {
			sumTime += node.nextNode.waitExecuteTime;
			// 当累计等待时间大于首节点间隔时间
			if(sumTime >= firstNode.getter.getInterval()) {
				// 插入到当前节点后一位
				long waitTime = node.nextNode.waitExecuteTime;
				firstNode.nextNode = node.nextNode;
				node.nextNode.waitExecuteTime = sumTime - firstNode.getter.getInterval();
				firstNode.waitExecuteTime = waitTime - node.nextNode.waitExecuteTime;
				node.nextNode = firstNode;
				break;
			}
			// 当到达链表末尾,直接把节点插入链表末尾
			if(node.nextNode.nextNode == null) {
				node.nextNode.nextNode = firstNode;
				firstNode.nextNode = null;
				firstNode.waitExecuteTime = firstNode.getter.getInterval() - sumTime;
				break;
			}
			node = node.nextNode;
		}
		// 只有一个节点的时候
		if(header.nextNode == null) {
			header.nextNode = firstNode;
			firstNode.nextNode = null;
			firstNode.waitExecuteTime = firstNode.getter.getInterval() - sumTime;
		}
		int index = 0;
		node = header.nextNode;
		_LOG.debug("==============执行首节点完毕之后===============");
		while(node != null) {
			_LOG.debug("第" + index + "个节点[" + node.toString() + "]");
			index ++;
			node = node.nextNode;
		}
		return firstNode.getter;
	}
	
	/**
	 * 代理获取对象节点
	 */
	private static class Entry {
		/** 当前代理获取对象节点 */
		private ProxyGetter getter = null;
		
		/** 下一个代理获取对象节点 */
		private Entry nextNode = null;
		
		/** 执行前需要等待的时间 */
		private Long waitExecuteTime = null;

		public Entry(ProxyGetter getter, Entry nextNode, Long waitExecuteTime) {
			this.getter = getter;
			this.nextNode = nextNode;
			this.waitExecuteTime = waitExecuteTime;
		}

		@Override
		public String toString() {
			return "Entry [getter=" + getter + ", nextNode=" + nextNode + ", waitExecuteTime=" + waitExecuteTime + "]";
		}
		
	}
	
}
