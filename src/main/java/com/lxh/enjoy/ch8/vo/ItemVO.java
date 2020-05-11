package com.lxh.enjoy.ch8.vo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaohao
 * @since 2020/4/30 18:05
 */
public class ItemVO<T> implements Delayed {
    private long activeTime; // 到期时间
    private T data;

    public ItemVO(long activeTime, T data) {
        this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime, TimeUnit.MILLISECONDS) + System.nanoTime();
        this.data = data;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getData() {
        return data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        return d;
    }

    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d == 0) ? 0 : ((d > 0) ? 1 : -1);
    }

}
