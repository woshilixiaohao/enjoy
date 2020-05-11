package com.lxh.enjoy.ch8;

import java.util.concurrent.TimeUnit;

/**
 * @author lixiaohao
 * @since 2020/5/11 11:04
 */
public class SleepTool {

    public static void ms(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
