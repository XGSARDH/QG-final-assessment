package com.qg.util.threadPool;

import com.qg.util.jdbcUtils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.*;

public class QgThreadPool {
    private static ExecutorService threadPool;
    private static InputStream inputStream = null;
    private static Properties properties = new Properties();

    static {
        Logger LOGGER = LoggerFactory.getLogger(QgThreadPool.class);
        try {
            inputStream = JdbcUtils.class.getResourceAsStream("/threadpool.properties");
            if (inputStream == null) {
                LOGGER.error("无法加载资源文件，inputStream为null");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("读取配置文件时发生IO异常: ", e);
        }

        int corePoolSize = Integer.parseInt(properties.getProperty("corePoolSize"));
        int maximumPoolSize = Integer.parseInt(properties.getProperty("maximumPoolSize"));
        int keepAliveTime = Integer.parseInt(properties.getProperty("keepAliveTime"));
        int workQueue = Integer.parseInt(properties.getProperty("workQueue"));

        threadPool = new ThreadPoolExecutor(
                corePoolSize ,
                maximumPoolSize ,
                keepAliveTime ,
                TimeUnit.SECONDS ,
                new ArrayBlockingQueue<Runnable>(workQueue) ,
                Executors.defaultThreadFactory() ,
                new ThreadPoolExecutor.AbortPolicy()
        ) ;
    }

    public static void submit(Runnable runnable) {
        threadPool.submit(runnable);
    }

}
