package com.lxh.enjoy.ch8;

import com.lxh.enjoy.ch8.vo.ITaskProcessor;
import com.lxh.enjoy.ch8.vo.JobInfo;
import com.lxh.enjoy.ch8.vo.TaskResult;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author lixiaohao
 * @since 2020/4/30 17:39
 */
public class PendingJobPool {

    // 保守估计
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();

    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);
    // 线程池，固定大小，有界队列
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60,
            TimeUnit.SECONDS, taskQueue);
    // job存放的容器
    private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    // 对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
    private static class PendingTask<T, R> implements Runnable {
        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData) {
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
        }
    }

    // 单例模式
    private PendingJobPool (){}

    private static class JobPoolHolder{
        public static PendingJobPool pool = new PendingJobPool();
    }

    public PendingJobPool getInstance(){
        return JobPoolHolder.pool;
    }

    // 调用者提交工作中的任务
    public <T, R> void putTask(String jobName, T t) {

    }

    // 调用者注册工作，如工作名，任务处理器等
    public <R> void registerJob(String jobName, int jobLength, ITaskProcessor<?, ?> taskProcessor, long expireTime) {

    }

    // 根据工作名称检索工作
    private <R> JobInfo<R> getJob(String jobName) {
        return null;
    }

    // 获得每个任务的处理详情
    public <R> List<TaskResult<R>> getTaskDetail(String jobName) {
        return null;
    }

    // 获得工作的整体处理进度
    public <R> String getTaskProgress(String jobName) {
        return null;
    }
}
