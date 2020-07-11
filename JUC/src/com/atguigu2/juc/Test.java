package com.atguigu2.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/4 10:58
 */
public class Test {
    public static void main(String[] args) {
        Test2 t = new Test2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    t.loppA(i);
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    t.loppB(i);
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    t.loppC(i);
                }
            }
        }, "C").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    t.loppD(i);
                    System.out.println("*****************分隔2****************");
                    
                }
            }
        }, "D").start();
        

    }

}

class Test2 {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private Condition condition4 = lock.newCondition();

    void loppA(int total) {
        lock.lock();
        try {
            if (number != 1) {
                condition1.await();
            }
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i + "\t" + total);
            }
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    void loppB(int total) {
        lock.lock();
        try {
            if (number != 2) {
                condition2.await();
            }
            for (int i = 1; i <= 2; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i + "\t" + total);
            }
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    void loppC(int total) {
        lock.lock();
        try {
            if (number != 3) {
                condition3.await();
            }
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i + "\t" + total);
            }
            number = 4;
            condition4.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    void loppD(int total) {
        lock.lock();
        try {
            if (number != 4) {
                condition4.await();
            }
            for (int i = 1; i <= 4; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i + "\t" + total);
            }
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
