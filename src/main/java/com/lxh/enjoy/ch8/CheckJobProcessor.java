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

    // 处理队列中到期的任务
    private static class FetchJob implements Runnable {
        @Override
        public void run() {

        }
    }

    /*
    任务完成后，放入队列，经过expireTime后，从框架移除
     */
    public void putJob(String jobName,long expireTime){

    }

}
