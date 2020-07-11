package com.atguigu2.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.ReadWriteLock： 读写锁
 * 2. 写写/读写   互斥
 * 读读  不需要互斥
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/7/4 11:23
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLock = new ReadWriteLockDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLock.set((int) (Math.random() * 101));
            }
        }, "Write").start();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLock.get();
                }
            }).start();
        }
    }
}

class ReadWriteLockDemo {
    private int number = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void get() {
        lock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void set(int number) {
        lock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
            this.number = number;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }
}
