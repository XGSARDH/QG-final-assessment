package com.qg.util;

import com.qg.util.connectPool.ConnectionPoolManager;

import java.sql.Connection;

public class TestConnectionPool {

    public static void main(String[] args) {
        ThreadConnection threadConnection = new ThreadConnection();
        for (int i = 1; i <=600; i++) {
            Thread thread = new Thread(threadConnection, "线程:" + i);
            thread.start();
        }
    }

}

class ThreadConnection implements Runnable {

    public void run() {
        Connection connection = ConnectionPoolManager.getConnection();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        ConnectionPoolManager.releaseConnection(connection);
    }

}
