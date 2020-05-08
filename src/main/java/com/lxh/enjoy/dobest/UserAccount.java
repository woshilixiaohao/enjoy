package com.lxh.enjoy.dobest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lixiaohao
 * @since 2020/4/28 17:58
 */
public class UserAccount {
    private String name;
    private int money;

    private Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void flyMoney(int amount) {
        money -= amount;
    }
}
