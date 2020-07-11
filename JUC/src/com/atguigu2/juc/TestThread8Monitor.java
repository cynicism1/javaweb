package com.atguigu2.juc;

/**
 * 题目：判断打印的“one” or “two”？
 *
 * 1.两个普通同步方法：两个线程，标准打印，打印？ //one two
 * 2.新增Thread.sleep 给getOne() ,打印？ //one two
 * 3.新增普通方法getThree()，打印？ // three one two
 * 4.两个普通同步方法，两个number()对象 打印？ // two one
 * 5.修改getOne()为静态方法，打印？ // two one
 * 6.修改两个方法均为静态方法，一个number 对象 打印？ //one two
 * 7.一个是静态同步方法，一个是非静态同步方法，两个number() 打印？ // two one
 * 8.二个是静态同步方法,两个number()对象？打印？ //one two
 *
 * 线程八锁的关键：
 *      1.非静态方法的锁默认为 this，静态方法的锁为 对应Class 实例
 *      2.某一时刻内，只能有一个线程持有锁，无论几个方法。
 *
 * @author Cynicism
 * @Package_name
 * @since 2020/7/4 13:19
 */
public class TestThread8Monitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number number1 = new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number1.getTwo();
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                number.getThree();
//            }
//        }).start();
    }
}
class Number{
    public static synchronized void getOne(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }
    public static synchronized void  getTwo(){
        System.out.println("two");
    }
    public void getThree(){
        System.out.println("three");
    }
}