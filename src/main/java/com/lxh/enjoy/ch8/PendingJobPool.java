package com.lxh.enjoy.ch8;

import com.lxh.enjoy.ch8.vo.ITaskProcessor;
import com.lxh.enjoy.ch8.vo.JobInfo;
import com.lxh.enjoy.ch8.vo.TaskResult;
import com.lxh.enjoy.ch8.vo.TaskResultType;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
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

    public static Map<String, JobInfo<?>> getJobInfoMap() {
        return jobInfoMap;
    }

    private static CheckJobProcessor checkJobProcessor = CheckJobProcessor.getInstance();

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
            ITaskProcessor<T, R> taskProcessor = (ITaskProcessor<T, R>) jobInfo.getTaskProcessor();
            TaskResult<R> result = null;
            // 业务人员具体实现
            try {
                result = taskProcessor.taskExecute(processData);
                // 做检查 防止处理不当
                if (result == null) {
                    result = new TaskResult<>(TaskResultType.Exception, r, "result is null");
                }
                if (null == result.getReturnValue()) {
                    if (StringUtils.isEmpty(result.getReason())) {
                        result = new TaskResult<>(TaskResultType.Exception, r, "reason is null");
                    } else {
                        result = new TaskResult<>(TaskResultType.Exception, r,
                                "result is null and reason is" + result.getReason());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = new TaskResult<>(TaskResultType.Exception, r, e.getMessage());
            } finally {
                jobInfo.addTaskResult(result, checkJobProcessor);
            }
        }
    }

    // 单例模式
    private PendingJobPool() {
    }

    private static class JobPoolHolder {
        public static PendingJobPool pool = new PendingJobPool();
    }

    public static PendingJobPool getInstance() {
        return JobPoolHolder.pool;
    }

    // 调用者提交工作中的任务
    @SuppressWarnings("unchecked")
    public <T, R> void putTask(String jobName, T t) {
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T, R> pendingTask = new PendingTask<>(jobInfo, t);
        taskExecutor.execute(pendingTask);
    }

    // 调用者注册工作，如工作名，任务处理器等
    public <R> void registerJob(String jobName, int jobLength, ITaskProcessor<?, ?> taskProcessor, long expireTime) {
        JobInfo<R> jobInfo = new JobInfo<R>(jobName, jobLength, taskProcessor, expireTime);
        if (null != jobInfoMap.putIfAbsent(jobName, jobInfo)) {
            throw new RuntimeException(jobName + "已经注册了");
        }
    }


    // 根据工作名称检索工作
    @SuppressWarnings("unchecked")
    private <R> JobInfo<R> getJob(String jobName) {
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if (null == jobInfo) {
            throw new RuntimeException(jobName + "非法任务");
        }
        return jobInfo;
    }

    // 获得每个任务的处理详情
    public <R> List<TaskResult<R>> getTaskDetail(String jobName) {
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTaskDetail();
    }

    // 获得工作的整体处理进度
    public <R> String getTaskProgress(String jobName) {
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcess();
    }
}
