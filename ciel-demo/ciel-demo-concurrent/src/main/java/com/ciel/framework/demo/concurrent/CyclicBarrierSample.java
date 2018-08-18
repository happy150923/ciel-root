/*
/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: PropertiesConfigureGetterTest.java
 * Author:   18063410
 * Date:     2018/8/1 18:07
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.ciel.framework.demo.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * <br>
 *
 * @author 18063410
 * @date: 2018/8/1 18:07
 * @see
 * @since 盘古/大润发
 */
public class CyclicBarrierSample {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("........Barrier Action...........");
            }
        });

        System.out.println(
                "...." + cyclicBarrier.isBroken() + "...." + cyclicBarrier.getNumberWaiting() + "...." + cyclicBarrier.getParties());
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    System.out.println("Thread run start.");
                    System.out.println("Thread await : " + cyclicBarrier.await());
                    System.out.println("Thread run end.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Thread Exception");
                }
            }
        };
        System.out.println(
                "...." + cyclicBarrier.isBroken() + "...." + cyclicBarrier.getNumberWaiting() + "...." + cyclicBarrier.getParties());
        thread.start();
//        thread.interrupt();
        try {
            System.out.println("Main run start.");
            System.out.println("Main await : " + cyclicBarrier.await());
            System.out.println("Main run end.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(
                    "...." + cyclicBarrier.isBroken() + "...." + cyclicBarrier.getNumberWaiting() + "...." + cyclicBarrier.getParties());
            System.out.println("Main Exception");
        }

    }

}
