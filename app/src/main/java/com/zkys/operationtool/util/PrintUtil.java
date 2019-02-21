package com.zkys.operationtool.util;

/**
 * Created by DGDL-08 on ${DATA}
 */
public class PrintUtil {
    public static boolean printEnable = true;// 可以控制全局是否可以打印log
    public static void printLog(String log) {
        if (printEnable) System.out.println(log);
    }
}
