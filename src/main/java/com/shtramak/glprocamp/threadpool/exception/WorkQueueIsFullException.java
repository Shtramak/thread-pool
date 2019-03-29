package com.shtramak.glprocamp.threadpool.exception;

public class WorkQueueIsFullException extends RuntimeException {
    public WorkQueueIsFullException(String message) {
        super(message);
    }
}
