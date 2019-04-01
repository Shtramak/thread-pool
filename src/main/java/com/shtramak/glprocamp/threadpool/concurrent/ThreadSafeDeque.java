package com.shtramak.glprocamp.threadpool.concurrent;

public class ThreadSafeDeque {
    static class Node {
        Runnable element;
        Node next;
        Node previous;

        Node(Runnable element) {
            this.element = element;
        }
    }

    Node head;
    Node tail;
    volatile int size;


    public synchronized void add(Runnable element) {
        Node newNode = new Node(element);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail.previous = newNode;
            newNode.next = tail;
            tail = newNode;
        }
        size++;
    }

    public synchronized Runnable poll() {
        if (size > 0) {
            Node currentNode = head;
            head = head.previous;
            if (head != null) {
                head.next = null;
            }
            size--;
            return currentNode.element;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public synchronized void clear() {
        head = tail = null;
        size = 0;
    }
}
