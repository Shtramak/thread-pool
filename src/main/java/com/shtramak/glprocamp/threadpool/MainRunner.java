package com.shtramak.glprocamp.threadpool;

import com.shtramak.glprocamp.threadpool.executor.MyExecutorService;
import com.shtramak.glprocamp.threadpool.executor.MyExecutors;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class MainRunner {
    public static void main(String[] args) throws InterruptedException {
        MyExecutorService executorService = MyExecutors.newFixedThreadPool(10, 10);
        for (int i = 0; i < 10; i++) {
            Task task = new Task(i);
            executorService.execute(task);
        }

        Thread.sleep(5000);
        executorService.shutdownNow();
    }


    static class Task implements Runnable {
        int id;

        Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " start task " + id);
            Double result = doWork();
            System.out.println(threadName + " end task " + id + " result: " + result.intValue());
        }

        private Double doWork() {
            return DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble())
                    .limit(10000000)
                    .boxed()
                    .map(Math::cos)
                    .reduce(0d, Double::sum);
        }
    }

}
