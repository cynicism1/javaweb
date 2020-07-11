//package com.atguigu2.juc;
//
///**
// * 生产者与消费者问题
// *
// * @author Cynicism
// * @Package_name
// * @since 2020/7/3 14:15
// */
//public class TestProductorAndConsumer {
//    public static void main(String[] args) {
//
//        Clerk clerk = new Clerk();
//
//        productor productor = new productor(clerk);
//        Consumer consumer = new Consumer(clerk);
//
//        new Thread(productor, "生产者A").start();
//        new Thread(consumer, "消费者B").start();
//        new Thread(productor, "生产者C").start();
//        new Thread(consumer, "消费者D").start();
//
//    }
//}
//
////店员
//class Clerk {
//    private int product = 0;
//
//    public synchronized void get() {
//        while (product >= 1) {//为了避免虚假唤醒，应该总是在循环中
//            System.out.println("产品已满！");
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//            }
//        }
//        System.out.println(Thread.currentThread().getName() + ":" + ++product);
//        this.notifyAll();
//    }
//
//    public synchronized void sale() {
//        while (product <= 0) {
//            System.out.println("缺货！");
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//            }
//        }
//        System.out.println(Thread.currentThread().getName() + ":" + --product);
//        this.notifyAll();
//    }
//}
//
////生产者
//class productor implements Runnable {
//
//    private Clerk clerk;
//
//    public productor(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//            }
//            clerk.get();
//        }
//    }
//}
//
////消费者
//class Consumer implements Runnable {
//
//    private Clerk clerk;
//
//    public Consumer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            clerk.sale();
//        }
//    }
//}
