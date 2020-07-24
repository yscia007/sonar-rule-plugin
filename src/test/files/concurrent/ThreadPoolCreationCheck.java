package com.alibaba.idea.pmd.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
public class Executors2Test {
    private ExecutorService executorService = newSingleThreadExecutor(); // Noncompliant

    private ExecutorService executorService1 = Executors.newSingleThreadExecutor(); // Noncompliant

    private ThreadFactory abc = Executors.defaultThreadFactory();

    private ExecutorService executorServic2 = executorService;

    //org.apache.commons.lang3.concurrent.BasicThreadFactory
    ScheduledExecutorService executorService3 = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    public SimpleDateFormatRule2Test(){
        executorService1 = newSingleThreadExecutor(); // Noncompliant
        newSingleThreadExecutor().submit(() -> System.out.println("xxx")); // Noncompliant
        java.util.concurrent.Executors.newSingleThreadExecutor().submit(() -> { // Noncompliant

        });
    }
}