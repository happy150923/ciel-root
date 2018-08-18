package com.ciel.framework.demo.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {


    public static void main(String[] args) {
        ExecutorService executorService =
                new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new DIYThreadFactory(),
                        new RejectedExecutionHandler() {
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                                System.out.println("RejectedExecutionHandler. rejectedExecution. r: " + r);
//                                throw new RejectedExecutionException("Task " + r.toString() +
//                                        " rejected from " +
//                                        executor.toString());
                            }
                        });

        Thread thread;
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(5l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("running..." + this.getName());
                        Thread.sleep(30L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            if (i == 0 || i == 4) {
                thread = new Thread() {
                    @Override
                    public void run() {
                        System.out.println("running..." + this.getName());
                        throw new RuntimeException("hahahhahhahh");
                    }
                };
            }
            thread.setName("thread+" + i);
            System.out.println(".... submit thread " + thread.getName() + "....");
            executorService.submit(thread);
        }
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }


}

class DIYThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    DIYThreadFactory() {
        System.out.println("......DIYThreadFactory constructor......");
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";
        System.out.println("......DIYThreadFactory constructor end......");
    }

    public Thread newThread(Runnable r) {
        System.out.println("......DIYThreadFactory new thread......");
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        System.out.println("......DIYThreadFactory new thread " + t.getName() + "......");
        return t;
    }
}
