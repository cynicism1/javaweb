package com.atguigu.juc;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/2 13:59
 */

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 * 		  int i = 10;
 * 		  i = i++; //10
 *
 * 		  int temp = i;
 * 		  i = i + 1;
 * 		  i = temp;
 *
 * 二、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 * 		1. volatile 保证内存可见性
 * 		2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 * 			CAS 算法是硬件对于并发操作的支持
 * 			CAS 包含了三个操作数：
 * 			①内存值  V
 * 			②预估值  A
 * 			③更新值  B
 * 			当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {

    //private volatile int Serializable = 0;
    private AtomicInteger Serializable = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }


    public int getSerialNumber() {
        return Serializable.getAndIncrement();
    }
}