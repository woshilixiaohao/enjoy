package com.lxh.enjoy.ch8.vo;

/**
 * 返回结果类型
 *
 * @author lixiaohao
 * @since 2020/4/29 16:48
 */
public enum TaskResultType {
    Success, Failure, Exception
    // 方法返回了业务人员需要的结果
    // 方法返回了业务人员不需要的结果
    // 方法执行抛出
}
