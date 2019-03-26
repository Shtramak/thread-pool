package com.shtramak.glprocamp.threadpool.exception;

public class WorkQueueIsFullException extends RuntimeException {
    public WorkQueueIsFullException() {
    }

    public WorkQueueIsFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
