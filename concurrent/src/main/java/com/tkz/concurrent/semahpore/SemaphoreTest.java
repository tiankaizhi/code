package com.tkz.concurrent.semahpore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {

        final Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 30; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获得了信号量,时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "释放了信号量,时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }

        System.out.println("----------------------------------end---------------------------------");
    }

}