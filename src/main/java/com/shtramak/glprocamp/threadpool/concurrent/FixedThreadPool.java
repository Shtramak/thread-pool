package com.shtramak.glprocamp.threadpool.concurrent;

import com.shtramak.glprocamp.threadpool.exception.WorkQueueIsFullException;
import com.shtramak.glprocamp.threadpool.executor.MyExecutorService;

public class FixedThreadPool implements MyExecutorService {
    private int workQueueSize;
    private WorkThread[] workThreads;

    private final MyBlockingQueue workQueue;

    public FixedThreadPool(int poolsize, int workQueueSize) {
        this.workQueueSize = workQueueSize;
        workQueue = new MyBlockingQueue();
        fillWorkThreads(poolsize);
    }

    private void fillWorkThreads(int poolsize) {
        workThreads = new WorkThread[poolsize];
        for (int i = 0; i < poolsize; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    @Override
    public void execute(Runnable command) {
        verifyWorkQueueSize();
        workQueue.add(command);
        workQueue.notify();
    }

    private void verifyWorkQueueSize() {
        if (workQueue.size() == workQueueSize-1){
            throw new WorkQueueIsFullException("The task cannot be accepted due to workQueue is full");
        }
    }

    @Override
    public void shutdownNow() {
        for (WorkThread workThread : workThreads) {
            workThread.interrupt();
        }
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (workQueue) {
                    while (workQueue.isEmpty()) {
                        try {
                            workQueue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("InterruptedException occurred while waiting a task...");
                        }
                    }
                    task = workQueue.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Exception occurred while running the task. Reason: " + e.getMessage());
                }
            }
        }
    }
}
