package com.qg.util;

import com.qg.util.threadPool.QgThreadPool;

public class TestQgThreadPool {

    public static void main(String[] args) {
        for (int i = 0;i < 1000; i++) {
            QgThreadPool.submit(new TestMyRunnable());
        }
    }
}

class TestMyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i <= 0; i++) {
            System.out.println(Thread.currentThread().getName() + "---" + i);
        }
    }
}
