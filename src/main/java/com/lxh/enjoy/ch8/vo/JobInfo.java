package com.lxh.enjoy.ch8.vo;

import com.lxh.enjoy.ch8.CheckJobProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 提交给框架执行的实体类
 *
 * @author lixiaohao
 * @since 2020/4/29 17:03
 */
public class JobInfo<R> {
    // 区分唯一的工作
    private final String jobName;
    // 工作任务个数
    private final int jobLength;
    // 这个工作任务的处理器
    private final ITaskProcessor<?, ?> taskProcessor;
    //
    private AtomicInteger successCount;
    private AtomicInteger taskProcessorCount;
    private LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;// 从头拿结果，从尾放结果
    // 过期时间
    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcessor<?, ?> taskProcessor, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcessor = taskProcessor;
        this.successCount = new AtomicInteger(0);
        this.taskProcessorCount = new AtomicInteger(0);
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcessor<?, ?> getTaskProcessor() {
        return taskProcessor;
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getTaskProcessorCount() {
        return taskProcessorCount.get();
    }

    public int getFailCount() {
        return taskProcessorCount.get() - successCount.get();
    }

    public String getTotalProcess() {
        return "success: " + successCount.get() + ", current: " + taskProcessorCount.get() + ", total: " + jobLength;
    }

    public List<TaskResult<R>> getTaskDetail() {
        List<TaskResult<R>> list = new ArrayList<>();
        TaskResult<R> taskResult;
        while ((taskResult = taskDetailQueue.pollFirst()) != null) {
            list.add(taskResult);
        }
        return list;
    }

    // 从业务应用角度来说，保证最终一致性几颗，不需要对方法加锁
    public void addTaskResult(TaskResult<R> taskResult, CheckJobProcessor checkJobProcessor) {
        if (TaskResultType.Success.equals(taskResult.getResultType())) {
            successCount.incrementAndGet();
        }
        taskDetailQueue.addLast(taskResult);
        taskProcessorCount.incrementAndGet();
        if (taskProcessorCount.get() == jobLength) {
            checkJobProcessor.putJob(jobName, expireTime);
        }
    }
}
