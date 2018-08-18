/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: PropertiesConfigureGetterTest.java
 * Author:   18063410
 * Date:     2018/8/3 14:56
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.ciel.framework.demo.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br>
 *
 * @author 18063410
 * @date: 2018/8/3 14:56
 * @see
 * @since 盘古/大润发
 */
public class SemaphoreSample {


    public static void main(String[] args) {
        ResourceManage resourceManage = new ResourceManage();
        Thread[] users = new Thread[100];
        for (int i = 0; i < 100; i++) {
            users[i] = new Thread(new ResourceUser(i, resourceManage));
        }
        for (int i = 0; i < users.length; i++) {
            try {
                users[i].start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

class ResourceManage {
    private final ReentrantLock reentrantLock;
    private final Semaphore semaphore;
    private boolean[] resourceArray;

    public ResourceManage() {
        this.reentrantLock = new ReentrantLock(true);
        this.semaphore = new Semaphore(10, true);
        this.resourceArray = new boolean[10];
        for (int i = 0; i < resourceArray.length; i++) {
            resourceArray[i] = true;
        }
    }

    public void useResource(int userId) {
        try {
            this.semaphore.acquire();
            int id = getResourceId(userId);
            System.out.println("userId：" + userId + " is using resource: " + id);
            Thread.sleep(10);
            resourceArray[id] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.semaphore.release();
        }

    }

    public int getResourceId(int userId) {
        int id = -1;
        reentrantLock.lock();
        try {
            for (int i = 0; i < resourceArray.length; i++) {
                if (resourceArray[i]) {
                    id = i;
                    resourceArray[i] = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return id;
    }
}

class ResourceUser implements Runnable {
    private int userId;
    private ResourceManage resourceManage;

    public ResourceUser(int userId, ResourceManage resourceManage) {
        this.userId = userId;
        this.resourceManage = resourceManage;
    }

    @Override
    public void run() {
        System.out.println("userId: " + userId + " attempt to use resource.");
        resourceManage.useResource(userId);
        System.out.println("userId: " + userId + " used resource!");
    }
}