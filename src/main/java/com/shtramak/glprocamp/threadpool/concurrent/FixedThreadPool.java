package com.shtramak.glprocamp.threadpool.concurrent;

import com.shtramak.glprocamp.threadpool.exception.WorkQueueIsFullException;
import com.shtramak.glprocamp.threadpool.executor.MyExecutorService;

import java.util.stream.Stream;

public class FixedThreadPool implements MyExecutorService {
    private final int workQueueSize;
    private final int poolSize;
    private final WorkerThread[] pool;
    private final ThreadSafeDeque workQueue;

    private volatile boolean isRunning = true;

    public FixedThreadPool(int poolSize, int workQueueSize) {
        this.workQueueSize = workQueueSize;
        this.poolSize = poolSize;
        workQueue = new ThreadSafeDeque();
        pool = new WorkerThread[poolSize];
        initPool();
    }

    private void initPool() {
        for (int i = 0; i < poolSize; i++) {
            pool[i] = new WorkerThread();
            pool[i].start();
        }
    }

    @Override
    public void execute(Runnable command) {
        synchronized (workQueue) {
            verifyWorkQueueSize();
            workQueue.add(command);
            workQueue.notify();
        }
    }

    private void verifyWorkQueueSize() {
        if (workQueue.size() == workQueueSize) {
            throw new WorkQueueIsFullException("The task cannot be accepted due to workQueue is full");
        }
    }

    @Override
    public void shutdownNow() {
        Stream.of(pool).forEach(WorkerThread::interrupt);
        isRunning = false;
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (isRunning) {
                synchronized (workQueue) {

                    if (workQueue.isEmpty()) {
                        try {
                            workQueue.wait();
                        } catch (InterruptedException e) {
                            String threadName = Thread.currentThread().getName();
                            System.out.println(threadName+" was interrupted...");
                        }
                    }

                    task = workQueue.poll();
                }

                try {
                    if (task != null) {
                        task.run();
                    }
                } catch (RuntimeException e) {
                    //To save current WorkerThread in ThreadPool
                    System.out.println("Exception occurred while running the task. Reason: " + e);
                }

            }
        }
    }

}
