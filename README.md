###Task description

Implement Thread pool without java.util.concurrent  framework (but you can use it for testing in your unit tests).

```java
public class MyExecutors {

  /**
   * Creates a new ThreadPool with the given initial number of threads and work queue size
   *
   * @param poolSize the number of threads to keep in the pool, even
   *        if they are idle
   * @param workQueueSize the queue to use for holding tasks before they are
   *        executed.  This queue will hold only the {@code Runnable}
   *        tasks submitted by the {@code execute} method.
   */
  public static MyExecutorService newFixedThreadPool(int poolSize, int workQueueSize) {
     return null;
  }

  public static MyExecutorService newFixedThreadPool(int poolSize) {
     return newFixedThreadPool(poolSize, 10);
  }

}


public interface MyExecutorService {

  /**
   * Executes the given command in a thread from thread pool
   *
   * @param command the runnable task
   *
   * @throws WorkQueueIsFullException if this task cannot be accepted for execution due
   *                                  to workQueue is full
   */
  void execute(Runnable command);

  /**
   * Attempts to stop all actively executing tasks.
   */
  void shutdownNow();
}
```

To implement this task you should also implement a thread-safe queue for holding submitted Runnable commands. 
