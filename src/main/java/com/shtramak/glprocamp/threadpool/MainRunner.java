package com.shtramak.glprocamp.threadpool;

import com.shtramak.glprocamp.threadpool.executor.MyExecutorService;
import com.shtramak.glprocamp.threadpool.executor.MyExecutors;

public class MainRunner {
    public static void main(String[] args) {
        MyExecutorService executorService = MyExecutors.newFixedThreadPool(5);
        executorService.execute(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println("Thread "+threadName+" starts");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted");
            }
            System.out.println("Thread "+threadName+" finish its work");
        });

        executorService.shutdownNow();
    }

}
