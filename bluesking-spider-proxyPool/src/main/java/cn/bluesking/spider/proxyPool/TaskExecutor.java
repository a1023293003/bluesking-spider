package cn.bluesking.spider.proxyPool;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bluesking.spider.commons.helper.ClassHelper;
import cn.bluesking.spider.commons.util.CollectionUtil;
import cn.bluesking.spider.proxyPool.task.BaseTask;

/**
 * 任务执行者
 * 
 * @author 随心
 *
 */
public class TaskExecutor {

    /** slf4j日志配置 */
    private static final Logger _LOG = LoggerFactory.getLogger(TaskExecutor.class);

    /** 获取代理实现类对象链表头节点 */
    private static TaskEntry header;

    /** 获取代理类对象list */
    private static List<BaseTask> taskList;

    /** 代理获取类数量 */
    private static int taskSize = -1;

    /** 定时器线程 */
    private static Thread timerThread;
    
    /** 定时器任务处理连接池线程个数 */
    private static final int THREAD_POOL_NUM = 100;
    
    /** 暂停标记 */
    private static volatile boolean supenp = false;
    
    /** 处理定时器任务的线程池 */
    private static ThreadPoolExecutor threadPool = getThreadPoolExecutor();

    static {
        // 获取基础任务类的class集合
        Set<Class<?>> taskSet = ClassHelper.getClassSetBySuper(BaseTask.class);
        // 任务数量
        taskSize = taskSet.size();
        if (CollectionUtil.isNotEmpty(taskSet)) {
            // 获取任务对象集合
            taskList = new ArrayList<BaseTask>(taskSet.size());
            try {
                for (Class<?> cls : taskSet) {
                    if (cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
                        // 如果当前Class对象是接口或者抽象类,则不做处理
                        taskSize --;
                    } else {
                        BaseTask task = (BaseTask) cls.newInstance();
                        _LOG.debug("当前获取了" + cls.getSimpleName() + "任务类,它的等待间隔是" + task.getInterval());
                        taskList.add(task);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 按照任务执行的间隔从小到大排序排序
            Collections.sort(taskList, new Comparator<BaseTask>() {
                @Override
                public int compare(BaseTask task1, BaseTask task2) {
                    return task1.getInterval() <= task2.getInterval() ? -1 : 1;
                }
            });
        }
    }

    /**
     * 创建并返回一个线程池
     * 
     * @return
     */
    private static ThreadPoolExecutor getThreadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_NUM);
    }
    
    /**
     * 把任务添加到线程池的任务队列中
     * 
     * @param task [Runnable]待执行任务
     */
    public static void executeTask(Runnable task) {
        threadPool.execute(task);
    }
    
    /**
     * 初始化
     */
    private static void init() {
        // 初始化获取代理对象链表头
        header = new TaskEntry(null, null, null);
        // 把所有getter封装成GetterEntry,然后构建成任务链表
        BaseTask task = taskList.get(0);
        if (CollectionUtil.isNotEmpty(taskList)) {
            
            // 第一个节点等待时间为间隔时间
            TaskEntry firstNode = new TaskEntry(task, null, task.getInterval());
            _LOG.debug("第0个节点[" + firstNode.toString() + "]");
            header.nextNode = firstNode; // 存储当前节点的上一个节点
            TaskEntry node; // 存储当前节点
            // 之后节点的等待时间为 (间隔时间 - 上一个节点的间隔时间)
            for (int i = 1; i < taskList.size(); i++) {
                task = taskList.get(i);
                node = new TaskEntry(task, null, task.getInterval() - firstNode.task.getInterval());
                firstNode.nextNode = node;
                firstNode = node;
                _LOG.debug("第" + i + "个节点[" + firstNode.toString() + "]");
            }
        }
    }

    /**
     * 执行定时器,开始代理爬取
     */
    public static synchronized void execute() {
        // 有代理获取类才启动定时器
        if (taskSize > 0) {
            // 初始化
            init();
            // 先把任务执行一遍
            for (BaseTask task : taskList) {
                executeTask(task);
            }
            // 新开一个进程定时执行代理爬取任务
            if (timerThread == null || !timerThread.isAlive()) {
                // 新开一个线程作为定时器
                timerThread = new Thread() {
                    public void run() {
                        long waitTime = 0;
                        try {
                            while (!isInterrupted()) {
                                if (!isSupenp()) {
                                    waitTime = header.nextNode.waitExecuteTime;
                                    if (waitTime > 0) {
                                        Thread.sleep(waitTime);
                                    } else {
                                        // do nothing
                                    }
                                    if (!isSupenp()) {
                                        // 获取链表头的代理获取类对象
                                        final BaseTask task = pollFirstNode();
                                        executeTask(task);
                                    } else {
                                        // do nothing
                                    }
                                } else {
                                    // do nothing
                                }
                            }
                        } catch (InterruptedException e) {
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

    /**
     * 暂停定时器
     */
    public static synchronized void supenp() {
        supenp = true;
    }
    
    /**
     * 定时器是否处于暂停状态
     * 
     * @return
     */
    public static boolean isSupenp() {
        return supenp;
    }
    
    /**
     * 重启定时器
     */
    public static synchronized void restart() {
        supenp = false;
    }
    
    /**
     * 取出第一个任务节点
     * 
     * @return
     */
    private static synchronized BaseTask pollFirstNode() {
        if (taskSize <= 0)
            return null;
        // 用于寻找当前节点插入位置和计算等待时间
        long sumTime = 0;
        // 取出并删除首节点
        TaskEntry firstNode = header.nextNode;
        header.nextNode = firstNode.nextNode;
        // 遍历链表,找到合适的插入位置
        TaskEntry node = header;
        while (node.nextNode != null) {
            sumTime += node.nextNode.waitExecuteTime;
            // 当累计等待时间大于首节点间隔时间
            if (sumTime >= firstNode.task.getInterval()) {
                // 插入到当前节点后一位
                long waitTime = node.nextNode.waitExecuteTime;
                firstNode.nextNode = node.nextNode;
                node.nextNode.waitExecuteTime = sumTime - firstNode.task.getInterval();
                firstNode.waitExecuteTime = waitTime - node.nextNode.waitExecuteTime;
                node.nextNode = firstNode;
                break;
            }
            // 当到达链表末尾,直接把节点插入链表末尾
            if (node.nextNode.nextNode == null) {
                node.nextNode.nextNode = firstNode;
                firstNode.nextNode = null;
                firstNode.waitExecuteTime = firstNode.task.getInterval() - sumTime;
                break;
            }
            node = node.nextNode;
        }
        // 只有一个节点的时候
        if (header.nextNode == null) {
            header.nextNode = firstNode;
            firstNode.nextNode = null;
            firstNode.waitExecuteTime = firstNode.task.getInterval() - sumTime;
        }
        // 打印提示信息
        int index = 0;
        node = header.nextNode;
        _LOG.debug("==============执行首节点完毕之后===============");
        while (node != null) {
            _LOG.debug("第" + index + "个节点[" + node.toString() + "]");
            index++;
            node = node.nextNode;
        }
        return firstNode.task;
    }

    /**
     * 代理获取对象节点
     */
    private static class TaskEntry {
        
        /** 节点任务 */
        private BaseTask task = null;

        /** 下一个任务节点 */
        private TaskEntry nextNode = null;

        /** 执行前需要等待的时间 */
        private Long waitExecuteTime = null;

        /**
         * 构造方法
         * 
         * @param task            [BaseTask]当前节点任务
         * @param nextNode        [TaskEntry]下一个任务节点
         * @param waitExecuteTime [Long]执行前需要等待的时间
         */
        public TaskEntry(BaseTask task, TaskEntry nextNode, Long waitExecuteTime) {
            this.task = task;
            this.nextNode = nextNode;
            this.waitExecuteTime = waitExecuteTime;
        }

        @Override
        public String toString() {
            return "TaskEntry [task=" + task + ", nextNode=" + nextNode + ", waitExecuteTime=" + waitExecuteTime + "]";
        }

    }

}
