package mobi.mixiong.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread Utility
 */

@Slf4j
public class Threads {
  private static final AtomicInteger poolNumber = new AtomicInteger(1);
  /**
   * Utility method that sets name, daemon status and starts passed thread.
   * @param t thread to run
   * @return Returns the passed Thread <code>t</code>.
   */
  public static Thread setDaemonThreadRunning(final Thread t) {
    return setDaemonThreadRunning(t, t.getName());
  }

  /**
   * Utility method that sets name, daemon status and starts passed thread.
   * @param t thread to frob
   * @param name new name
   * @return Returns the passed Thread <code>t</code>.
   */
  public static Thread setDaemonThreadRunning(final Thread t,
    final String name) {
    return setDaemonThreadRunning(t, name, null);
  }

  /**
   * Utility method that sets name, daemon status and starts passed thread.
   * @param t thread to frob
   * @param name new name
   * @param handler A handler to set on the thread.  Pass null if want to
   * use default handler.
   * @return Returns the passed Thread <code>t</code>.
   */
  public static Thread setDaemonThreadRunning(final Thread t,
    final String name, final UncaughtExceptionHandler handler) {
    t.setName(name);
    if (handler != null) {
      t.setUncaughtExceptionHandler(handler);
    }
    t.setDaemon(true);
    t.start();
    return t;
  }

  /**
   * Shutdown passed thread using isAlive and join.
   * @param t Thread to shutdown
   */
  public static void shutdown(final Thread t) {
    shutdown(t, 0);
  }

  /**
   * Shutdown passed thread using isAlive and join.
   * @param joinwait Pass 0 if we're to wait forever.
   * @param t Thread to shutdown
   */
  public static void shutdown(final Thread t, final long joinwait) {
    if (t == null) return;
    while (t.isAlive()) {
      try {
        t.join(joinwait);
      } catch (InterruptedException e) {
        log.warn(t.getName() + "; joinwait=" + joinwait, e);
      }
    }
  }


  /**
   * If interrupted, just prints out the interrupt on STDOUT, resets interrupt and returns
   * @param millis How long to sleep for in milliseconds.
   */
  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Sleeps for the given amount of time even if interrupted. Preserves
   * the interrupt status.
   * @param msToWait the amount of time to sleep in milliseconds
   */
  public static void sleepWithoutInterrupt(final long msToWait) {
    long timeMillis = System.currentTimeMillis();
    long endTime = timeMillis + msToWait;
    boolean interrupted = false;
    while (timeMillis < endTime) {
      try {
        Thread.sleep(endTime - timeMillis);
      } catch (InterruptedException ex) {
        interrupted = true;
      }
      timeMillis = System.currentTimeMillis();
    }

    if (interrupted) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Create a new CachedThreadPool with a bounded number as the maximum 
   * thread size in the pool.
   * 
   * @param maxCachedThread the maximum thread could be created in the pool
   * @param timeout the maximum time to wait
   * @param unit the time unit of the timeout argument
   * @param threadFactory the factory to use when creating new threads
   * @return threadPoolExecutor the cachedThreadPool with a bounded number 
   * as the maximum thread size in the pool. 
   */
  public static ThreadPoolExecutor getBoundedCachedThreadPool(
      int maxCachedThread, long timeout, TimeUnit unit,
      ThreadFactory threadFactory) {
    ThreadPoolExecutor boundedCachedThreadPool =
      new ThreadPoolExecutor(maxCachedThread, maxCachedThread, timeout,
        unit, new LinkedBlockingQueue<Runnable>(), threadFactory);
    // allow the core pool threads timeout and terminate
    boundedCachedThreadPool.allowCoreThreadTimeOut(true);
    return boundedCachedThreadPool;
  }
  
  
  /**
   * Returns a {@link java.util.concurrent.ThreadFactory} that names each created thread uniquely,
   * with a util prefix.
   * @param prefix The prefix of every created Thread's name
   * @return a {@link java.util.concurrent.ThreadFactory} that names threads
   */
  public static ThreadFactory getNamedThreadFactory(final String prefix) {
    SecurityManager s = System.getSecurityManager();
    final ThreadGroup threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread()
        .getThreadGroup();

    return new ThreadFactory() {
      final AtomicInteger threadNumber = new AtomicInteger(1);
      private final int poolNumber = Threads.poolNumber.getAndIncrement();
      final ThreadGroup group = threadGroup;

      @Override
      public Thread newThread(Runnable r) {
        final String name = prefix + "-pool" + poolNumber + "-t" + threadNumber.getAndIncrement();
        return new Thread(group, r, name);
      }
    };
  }

  /**
   * Same as {#newDaemonThreadFactory(String, UncaughtExceptionHandler)},
   * without setting the exception handler.
   */
  public static ThreadFactory newDaemonThreadFactory(final String prefix) {
    return newDaemonThreadFactory(prefix, null);
  }

  /**
   * Get a named {@link ThreadFactory} that just builds daemon threads.
   * @param prefix name prefix for all threads created from the factory
   * @param handler unhandles exception handler to set for all threads
   * @return a thread factory that creates named, daemon threads with
   *         the supplied exception handler and normal priority
   */
  public static ThreadFactory newDaemonThreadFactory(final String prefix,
      final UncaughtExceptionHandler handler) {
    final ThreadFactory namedFactory = getNamedThreadFactory(prefix);
    return new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = namedFactory.newThread(r);
        if (handler != null) {
          t.setUncaughtExceptionHandler(handler);
        }
        if (!t.isDaemon()) {
          t.setDaemon(true);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
          t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
      }

    };
  }
}
