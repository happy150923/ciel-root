/*
 * Copyright (C), 2002-2018, 苏宁易购电子商务有限公司
 * FileName: PropertiesConfigureGetterTest.java
 * Author:   18063410
 * Date:     2018/8/27 17:26
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.ciel.framework.demo.interview;

/**
 * <br>
 *
 * @author 18063410
 * @date: 2018/8/27 17:26
 * @see
 * @since 盘古/大润发
 */
public class Calculator {

    public static Long calculate(String expression) {
        if (expression == null || "".equals(expression)) {
            return null;
        }
        long result = 0l;
        return 0l;
    }

    public static long calculateFirst3(String expression){
        expression.indexOf("{+-*/}");
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("1+23*345/234".indexOf("(+-*/)"));
    }

}
