package com.lxh.enjoy.ch8.vo;

/**
 * 任务
 *
 * @author lixiaohao
 * @since 2020/4/29 16:47
 */
public interface ITaskProcessor<T, R> {

    TaskResult<R> taskExecute(T data);
}
