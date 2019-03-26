package com.shtramak.glprocamp.threadpool.concurrent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyBlockingQueueTest {

    private MyBlockingQueue queue;

    @BeforeEach
    void setUp() {
        queue = new MyBlockingQueue();
    }

    @Test
    void testAddOneElement() {
        TestRunnable element = new TestRunnable(1);
        queue.add(element);
        assertEquals(1,queue.size);
        assertEquals(element, queue.head.element);
        assertNull(queue.head.next);
        assertNull(queue.head.previous);
    }

    @Test
    void testAddThreeElement() {
        TestRunnable element1 = new TestRunnable(1);
        TestRunnable element2 = new TestRunnable(2);
        TestRunnable element3 = new TestRunnable(3);
        queue.add(element1);
        queue.add(element2);
        queue.add(element3);
        assertEquals(3,queue.size);
        assertEquals(element1, queue.head.element);
        assertEquals(element2,queue.head.previous.element);
        assertEquals(element3, queue.tail.element);
        assertNull(queue.head.next);
        assertNull(queue.tail.previous);

        MyBlockingQueue.Node secondNode = queue.head.previous;
        assertEquals(element2,secondNode.element);
        assertEquals(element1,secondNode.next.element);
        assertEquals(element3,secondNode.previous.element);
    }

    @Test
    void pollWhenEmptyReturnsNull() {
        assertNull(queue.poll());
    }

    @Test
    void pollWhenOneElement() {
        TestRunnable element = new TestRunnable(1);
        queue.add(element);
        Runnable actual = queue.poll();
        assertEquals(element,actual);
        assertEquals(0,queue.size);
    }

    @Test
    void pollWhenTwoElement() {
        TestRunnable element1 = new TestRunnable(1);
        TestRunnable element2 = new TestRunnable(2);
        queue.add(element1);
        queue.add(element2);
        Runnable actual = queue.poll();
        assertEquals(element1,actual);
        assertEquals(element2,queue.head.element);
        assertEquals(1,queue.size);
    }

    @Test
    void testSizeWhenEmpty() {
        assertEquals(0,queue.size);
    }

    @Test
    void testSizeWithThreeElement() {
        addThreeElements();
        assertEquals(3,queue.size);
    }

    @Test
    void isEmptyWhenEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    void isEmptyWhenNotEmpty() {
        addThreeElements();
        assertFalse(queue.isEmpty());
    }

    private void addThreeElements(){
        TestRunnable element1 = new TestRunnable(1);
        TestRunnable element2 = new TestRunnable(2);
        TestRunnable element3 = new TestRunnable(3);
        queue.add(element1);
        queue.add(element2);
        queue.add(element3);
    }

    static class TestRunnable implements Runnable{
        int value;

        TestRunnable(int value) {
            this.value = value;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}