package com.lxh.enjoy.ch8;

import com.lxh.enjoy.ch8.vo.ITaskProcessor;
import com.lxh.enjoy.ch8.vo.TaskResult;
import com.lxh.enjoy.ch8.vo.TaskResultType;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaohao
 * @since 2020/5/11 10:01
 */
public class MyTask implements ITaskProcessor<Integer, Integer> {

    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random r = new Random();
        int flag = r.nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(flag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (flag <= 300) {// 正常
            Integer returnValue = data.intValue() + flag;
            return new TaskResult<>(TaskResultType.Success, returnValue);
        } else if (flag <= 400) {// 失败
            return new TaskResult<>(TaskResultType.Failure, -1, "fail");
        } else { // 发生异常
            try {
                throw new RuntimeException("异常发生了!");
            } catch (Exception e) {
                return new TaskResult<>(TaskResultType.Exception, -1, e.getMessage());
            }
        }
    }
}
