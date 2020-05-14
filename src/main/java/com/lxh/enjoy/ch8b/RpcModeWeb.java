package com.lxh.enjoy.ch8b;

import com.lxh.enjoy.ch8b.assist.Consts;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixiaohao
 * @since 2020/5/11 14:16
 * rpc服务端，采用生产者消费者模式，生产者消费者级联
 */
public class RpcModeWeb {


    // 负责生成文档
    private static ExecutorService docMakeService = Executors.newFixedThreadPool(Consts.CPU_COUNT * 2);

    // 负责上传文档
    private static ExecutorService docUploadService = Executors.newFixedThreadPool(Consts.CPU_COUNT * 2);

    // 先做完先提交
    CompletionService<String> docCs = new ExecutorCompletionService<>(docMakeService);

    CompletionService<String> docUploadCs = new ExecutorCompletionService<>(docUploadService);

}
