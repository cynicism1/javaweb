package com.atguigu.juc;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/2 21:50
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArraylist/CopyOnWriteArraySet : “写入并复制“
 * 注意：添加操作多时，效率低，因为每次添加是都会进行复制，开销非常大。并发迭代操作多时可以选择。
 */
public class TestCopyOnWriteArraylist {
    public static void main(String[] args) {
       HelloThread ht = new HelloThread();
        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable {

    //private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("AA");
        }
    }
}
