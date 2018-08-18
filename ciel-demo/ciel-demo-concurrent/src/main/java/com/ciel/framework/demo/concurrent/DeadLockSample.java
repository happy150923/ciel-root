/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: PropertiesConfigureGetterTest.java
 * Author:   18063410
 * Date:     2018/7/19 20:16
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.ciel.framework.demo.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <br>
 *
 * @author 18063410
 * @date: 2018/7/19 20:16
 * @see
 * @since 盘古/大润发
 */
public class DeadLockSample extends Thread {

    private String first;
    private String second;

    public DeadLockSample(String name, String first, String second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " obtained: " + first);
            try {
                Thread.sleep(1000L);
                synchronized (second) {
                    System.out.println(this.getName() + " obtained: " + second);
                }
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();

        Runnable dlCheck = () -> {
            long[] theads = mbean.findDeadlockedThreads();
            if (theads != null) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(theads);
                System.out.println("Detected dead lock threads:");
                for (ThreadInfo threadInfo : threadInfos) {
                    System.out.println(threadInfo.getThreadName());
                }
                System.out.println();
            }
        };
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(dlCheck, 5l, 10l, TimeUnit.SECONDS);

        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
        DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
