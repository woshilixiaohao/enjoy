package com.lxh.enjoy.ch8.vo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaohao
 * @since 2020/4/30 18:05
 */
public class ItemVO<R> implements Delayed {

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
