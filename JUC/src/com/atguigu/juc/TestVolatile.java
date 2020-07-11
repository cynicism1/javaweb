package com.atguigu.juc;

/**
 * 一.volatile关键字:当多个线程操作概念共享数据时
 *                  相较于synchronized是一种较轻量级的同步策略
 *注意：
 *      1.volatile不具备“互斥性”
 *      2.volatile 不能保证原子性
 *
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/7/2 10:54
 */
public class TestVolatile {
    public  static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            if (td.isFalg()) {
                System.out.println("******************************");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private volatile boolean falg = false;

    public boolean isFalg() {
        return falg;
    }

    public void setFalg(boolean falg) {
        this.falg = falg;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        falg = true;
        System.out.println("falg = " + falg);
    }
}