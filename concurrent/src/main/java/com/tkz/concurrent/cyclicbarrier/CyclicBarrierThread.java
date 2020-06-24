package com.tkz.concurrent.cyclicbarrier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierThread extends Thread {
    private CyclicBarrier cb;
    private int sleepSecond;

    public CyclicBarrierThread(CyclicBarrier cb, int sleepSecond) {
        this.cb = cb;
        this.sleepSecond = sleepSecond;
    }

    public void run() {
        try {
            System.out.println(this.getName() + "运行了");
            Thread.sleep(sleepSecond * 1000);
            System.out.println(this.getName() + "准备等待了, 时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            cb.await();
            System.out.println(this.getName() + "结束等待了, 时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("CyclicBarrier的所有线程await()结束了，我运行了, 时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        };

        CyclicBarrier cb = new CyclicBarrier(3, runnable);

        CyclicBarrierThread cbt0 = new CyclicBarrierThread(cb, 3);
        CyclicBarrierThread cbt1 = new CyclicBarrierThread(cb, 6);
        CyclicBarrierThread cbt2 = new CyclicBarrierThread(cb, 9);
        cbt0.start();
        cbt1.start();
        cbt2.start();
    }
}

