package com.alibaba.p3c.example.concurrent;

import java.util.concurrent.CountDownLatch;


public class CountDownTest {
    private CountDownLatch countDownLatch = new CountDownLatch(5);

    private void test(){
        try{
            System.out.println("dslkjf");
            countDownLatch.countDown();      // Noncompliant
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

public class CountDownTest1 {
    private CountDownLatch countDownLatch = new CountDownLatch(5);

    private void test(){
        countDownLatch.countDown();
        try{
            System.out.println("dslkjf");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

public class CountDownTest2 {
    private CountDownLatch countDownLatch = new CountDownLatch(5);

    private void test(){
        try{
            System.out.println("dslkjf");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
            System.out.println("dslkjf");
        }
    }
}