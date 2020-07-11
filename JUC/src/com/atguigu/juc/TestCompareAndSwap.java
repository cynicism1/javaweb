package com.atguigu.juc;

import java.rmi.UnexpectedException;
import java.util.HashMap;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/2 14:36
 */
public class TestCompareAndSwap {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }
    }
}

class CompareAndSwap {
    private int values;

    public synchronized int get() {
        return values;
    }

    public synchronized int compareAndSwap(int expecteValues, int newValues) {
        int oldValues = values;
        if (oldValues == expecteValues) {
            this.values = newValues;
        }
        return oldValues;
    }

    public synchronized boolean compareAndSet(int expecteValues, int newValues) {
        return expecteValues == compareAndSwap(expecteValues, newValues);
    }
}