package cn.bluesking.spider.mobile.lagou;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.bluesking.spider.commons.entity.MobileLagouPosition;
import cn.bluesking.spider.commons.helper.MybatisHelper;
import cn.bluesking.spider.commons.mapper.MobileLagouPositionMapper;
import cn.bluesking.spider.commons.util.ArrayUtil;

/**
 * 拉钩线程管理器
 * 
 * @author 随心
 *
 */
public class LagouThreadManager {

	/** 爬取结束标记数组 */
	private volatile Boolean[] flag = null;
	
	/** 线程数组 */
	private LagouSpiderThread[] threadArray = null;
	
	/** 守护线程,爬虫未完成任务死亡时复活它们 */
	private Thread daemon = null;
	
	/** 线程总数,同时也是每个爬虫爬取的间隔 */
	private int threadNum = 0;
	
	/** 最后的页数 */
	private int lastPage_no = 0;
	
	/** url前缀 */
	private String urlPrefix = null;
	
	/** 线程优先度 */
	private int priority = 5;
	
	/** 关键字 */
	private String keyWord = null;
	
	/** 职位id列表 */
	private List<Integer> positionIdList = null;
	
	/**
	 * 初始化数据
	 */
	private void init() {
		// 获取数据库中已有的职位信息
		SqlSession session = MybatisHelper.getSessionFactory().openSession(); // 获取会话
		MobileLagouPositionMapper mapper = session.getMapper(MobileLagouPositionMapper.class);
		// 查询数据库数据
		List<MobileLagouPosition> positions = mapper.selectByExample(null);
		// 创建id数组和id标记数组
		positionIdList = new ArrayList<Integer>(positions.size() + 100);
		for(MobileLagouPosition pos : positions) {
			positionIdList.add(pos.getPositionId());
		}
		session.commit();
		session.close();
		// 创建线程数组和标记数组
		threadArray = new LagouSpiderThread[threadNum];
		flag = new Boolean[threadNum];
		// 初始化标记数组
		ArrayUtil.setDefaultValues(flag, Boolean.FALSE);
		// 初始化进程数组并启动线程
		for(int i = 0; i < threadNum; i ++) {
			threadArray[i] = new LagouSpiderThread(i + 1, i, this);
			// 设置线程优先值
			threadArray[i].setPriority(priority);
			threadArray[i].start();
		}
		// 创建守护线程
		daemon = new Daemon();
		daemon.start();
	}
	
	/**
	 * 守护线程
	 * 
	 * @author 随心
	 *
	 */
	private class Daemon extends Thread {
		
		/** 守护线程检测间隔时间 */
		private static final int interval = 10000;
		
		@Override
		public void run() {
			// 爬虫线程未全部完成任务的时候
			while(isNotEnd()) {
				try {
					sleep(interval);
					for(int i = 0; i < threadNum; i ++) {
						// 有爬虫线程未完成任务就意外退出
						if(!flag[i] && !threadArray[i].isAlive()) {
							System.err.println("[守护线程重启编号" + i + "线程]");
							restartThread(i);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	/**
	 * 重启线程数组中指定下标的线程
	 * @param index
	 */
	private void restartThread(int index) {
		if(index < 0 || index >= threadArray.length) {
			throw new ArrayIndexOutOfBoundsException("线程数组下标越界异常");
		} else {
			threadArray[index] = new LagouSpiderThread(
					threadArray[index].getPageNo(), threadArray[index].getIndex(), this);
			threadArray[index].start();
		}
	}
	
	/**
	 * 构造方法
	 * @param threadNum [int]最大线程数量
	 * @param lastPageNo [int]最大爬取页数
	 * @param urlPrefix [String]url前缀
	 * @param keyWord [String]关键字
	 * @param city [String]城市
	 * @param priority [int]线程优先度
	 */
	public LagouThreadManager(int threadNum, int lastPageNo, 
			String urlPrefix, String keyWord, int priority) {
		this.threadNum = threadNum;
		this.lastPage_no = lastPageNo;
		this.urlPrefix = urlPrefix;
		this.keyWord = keyWord;
		this.priority = priority;
		// 初始化参数
		this.init();
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	
	public synchronized void addPositionId(int id) {
		this.positionIdList.add(id);
	}
	
	public int getThreadNum() {
		return threadNum;
	}

	public int getLastPage_no() {
		return lastPage_no;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public List<Integer> getPositionIdList() {
		return positionIdList;
	}

	/** 返回爬虫完成状态标记 */
	public Boolean[] getFlag() {
		return flag;
	}
	
	/** 获取线程数组 */
	public LagouSpiderThread[] getThreadArray() {
		return threadArray;
	}

	/**
	 * 获取指定编号线程完成状态
	 * @param index
	 * @return
	 */
	public boolean getFlag(int index) {
		return flag[index];
	}
	
	/**
	 * 修改指定线程完成标记
	 * @param index
	 * @param value
	 */
	public void setFlag(int index, boolean value) {
		flag[index] = value;
	}
	
	/**
	 * 判断所有线程是否都已经完成任务
	 * @return
	 */
	public boolean isEnd() {
		return !isNotEnd();
	}
	
	/**
	 * 判断是否存在线程没有完成任务
	 * @return
	 */
	public boolean isNotEnd() {
		return ArrayUtil.indexOf(flag, Boolean.FALSE) != -1;
	}
}
