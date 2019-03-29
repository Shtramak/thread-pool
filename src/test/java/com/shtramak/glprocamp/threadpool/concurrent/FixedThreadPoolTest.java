package com.shtramak.glprocamp.threadpool.concurrent;

import com.shtramak.glprocamp.threadpool.exception.WorkQueueIsFullException;
import com.shtramak.glprocamp.threadpool.executor.MyExecutorService;
import com.shtramak.glprocamp.threadpool.executor.MyExecutors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedThreadPoolTest {
    private MyExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = MyExecutors.newFixedThreadPool(2, 4);
    }

    @Test
    void executeWhenWorkQueueSizeExceeded() {
        Assertions.assertThrows(WorkQueueIsFullException.class, () -> {
            for (int i = 0; i < 6; i++) {
                executorService.execute(new TestTasks(i));
            }
        });
    }

    @Test
    void executeRegularCase() {
        executorService = MyExecutors.newFixedThreadPool(2);
        TestTasks task1 = new TestTasks(1);
        TestTasks task2 = new TestTasks(2);
        TestTasks task3 = new TestTasks(3);
        TestTasks task4 = new TestTasks(4);
        Assertions.assertEquals(1,task1.value);
        Assertions.assertEquals(2,task2.value);
        Assertions.assertEquals(3,task3.value);
        Assertions.assertEquals(4,task4.value);
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);
        executorService.execute(task4);
        TestTasks.sleep(1000);
        Assertions.assertEquals(2,task1.value);
        Assertions.assertEquals(3,task2.value);
        Assertions.assertEquals(4,task3.value);
        Assertions.assertEquals(5,task4.value);
    }

    @Test
    void shutdownNowWhenPoolEqualsToQueueSize() {
        TestTasks task1 = new TestTasks(1);
        TestTasks task2 = new TestTasks(2);
        TestTasks task3 = new TestTasks(3);
        TestTasks task4 = new TestTasks(4);
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);
        executorService.execute(task4);
        TestTasks.sleep(50);
        executorService.shutdownNow();
        Assertions.assertTrue(task1.value == 1 || task2.value == 2 || task3.value == 3|| task4.value == 4);
    }

    static class TestTasks implements Runnable {
        int value;

        TestTasks(int value) {
            this.value = value;
        }

        @Override
        public void run() {
            sleep(250);
            value++;
        }

        private static void sleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // ¯\_(ツ)_/¯
            }
        }
    }
}