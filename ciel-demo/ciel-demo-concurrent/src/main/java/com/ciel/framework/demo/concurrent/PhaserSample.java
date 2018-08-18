/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: PropertiesConfigureGetterTest.java
 * Author:   18063410
 * Date:     2018/8/7 17:14
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.ciel.framework.demo.concurrent;

import java.util.concurrent.Phaser;

/**
 * <br>
 *
 * @author 18063410
 * @date: 2018/8/7 17:14
 * @see
 * @since 盘古/大润发
 */
public class PhaserSample {

    public static void main(String[] args) {

        Phaser phaser = new Phaser(0);
        for (int i = 0; i < 3; i++) {
            new Thread(new LongRunningAction("Thread1-" + i, phaser)).start();
            new Thread(new LongRunningAction2("Thread2-" + i, phaser)).start();
        }
    }
}

class LongRunningAction implements Runnable {
    private String threadName;
    private Phaser ph;

    LongRunningAction(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        ph.register();
    }

    @Override
    public void run() {
        try {
            System.out.printf("步骤1. phase：%s. thread name: %s\n", ph.getPhase(), threadName);
            ph.arriveAndAwaitAdvance();
            System.out.printf("步骤2. phase：%s. thread name: %s\n", ph.getPhase(), threadName);
            ph.arriveAndAwaitAdvance();
            System.out.printf("步骤3. phase：%s. thread name: %s\n", ph.getPhase(), threadName);
            ph.arriveAndAwaitAdvance();
            System.out.printf("步骤4. phase：%s. thread name: %s\n", ph.getPhase(), threadName);
            ph.arriveAndDeregister();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class LongRunningAction2 implements Runnable {
    private String threadName;
    private Phaser ph;

    LongRunningAction2(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        ph.register();
    }

    @Override
    public void run() {
        try {
            System.out.printf("步骤1. phase：%s. thread name: %s\n", ph.getPhase(), threadName);
            ph.arriveAndAwaitAdvance();
            System.out.printf("步骤2. phase：%s. thread name: %s\n", ph.getPhase(), threadName);
            ph.arriveAndDeregister();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}