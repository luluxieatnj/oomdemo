package com.xll.oom2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  模拟内存泄漏
 *
 */
public class FullGCProblem {

    private static class CardInfo{
        BigDecimal price = new BigDecimal(0.0);
        String name = "张三";
        int age = 18;
        Date birthday = new Date();
        public void m(){}
    }

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50, new ThreadPoolExecutor.DiscardOldestPolicy());


    public static void main(String[] args) throws Exception{
        executor.setMaximumPoolSize(50);
        for (;;) {
            modelFit();
            Thread.sleep(190);
        }
    }

    public static void modelFit() {
        List<CardInfo> infoList = getCardInfoList();
        infoList.forEach(info -> {
            executor.scheduleWithFixedDelay(() -> {
                info.m();
            }, 2, 3, TimeUnit.SECONDS);
        });
    }

    private static List<CardInfo> getCardInfoList() {
        List<CardInfo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new CardInfo());
        }
        return list;
    }
}
