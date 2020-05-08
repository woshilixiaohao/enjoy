package com.lxh.enjoy.ch8.vo;

import lombok.Getter;

/**
 * 返回结果
 * @author lixiaohao
 * @since 2020/4/29 16:51
 */
@Getter
public class TaskResult<R> {
    private final TaskResultType resultType;
    private final R returnValue;// 业务返回结果数据
    private final String reason;

    public TaskResult(TaskResultType resultType, R returnValue, String reason) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = reason;
    }

    public TaskResult(TaskResultType resultType, R returnValue) {
        this.resultType = resultType;
        this.returnValue = returnValue;
        this.reason = "success";
    }


}
