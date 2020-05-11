package com.lxh.enjoy.ch8;

import com.lxh.enjoy.ch8.vo.ItemVO;

import java.util.concurrent.DelayQueue;

/**
 * @author lixiaohao
 * @since 2020/4/30 17:45
 * 任务完成后，在一定的时间供查询，之后为释放资源节约内存，需要定期处理过期的任务
 */
public class CheckJobProcessor {
    private static DelayQueue<ItemVO<String>> queue = new DelayQueue<>();// 存放任务的队列

    // 单例模式
    private CheckJobProcessor() {
    }

    private static class ProcessorHolder {
        public static CheckJobProcessor processor = new CheckJobProcessor();
    }

    public static CheckJobProcessor getInstance() {
        return ProcessorHolder.processor;
    }

    // 处理队列中到期的任务
    private static class FetchJob implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    ItemVO<String> item = queue.take();
                    System.out.println(item);
                    String jobName = item.getData();
                    PendingJobPool.getJobInfoMap().remove(jobName);
                    System.out.println(jobName + "is out of date, remove");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    任务完成后，放入队列，经过expireTime后，从框架移除
     */
    public void putJob(String jobName, long expireTime) {
        ItemVO<String> item = new ItemVO<>(expireTime, jobName);
        queue.offer(item);
        System.out.println("Job: " + jobName + "已放入过期检查，过期时间: " + jobName);
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程。。。。。");
    }

}
