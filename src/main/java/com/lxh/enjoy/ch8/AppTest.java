package com.lxh.enjoy.ch8;

import com.lxh.enjoy.ch8.vo.TaskResult;

import java.util.List;
import java.util.Random;

/**
 * @author lixiaohao
 * @since 2020/5/11 10:51
 */
public class AppTest {

    public static final String JOB_NAME = "计算数值";
    public static final int JOB_LENGTH = 1000;

    private static class QueryResult implements Runnable {
        private PendingJobPool pool;

        public QueryResult(PendingJobPool pool) {
            super();
            this.pool = pool;
        }

        @Override
        public void run() {
            int i = 0;
            while (i < 3500) {
                List<TaskResult<Object>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if (!taskDetail.isEmpty()) {
                    System.out.println(pool.getTaskProgress(JOB_NAME));
                    System.out.println(taskDetail);
                }
                SleepTool.ms(100);
                i++;
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        // 拿到框架
        PendingJobPool pool = PendingJobPool.getInstance();
        // 注册job
        pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 5000);
        Random random = new Random();
        for (int i = 0; i < JOB_LENGTH; i++) {
            pool.putTask(JOB_NAME, random.nextInt(1000));
        }
        Thread t = new Thread(new QueryResult(pool));
        t.start();
    }
}
