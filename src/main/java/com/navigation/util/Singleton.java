package com.navigation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singleton {
    private static ExecutorService baseStationsExecutor;
    private static ExecutorService mobileStationsExecutor;
    private static Logger logger;

    private Singleton() {}

    public static ExecutorService getBaseStationsExecutor() {
        if (baseStationsExecutor == null) {
            baseStationsExecutor = Executors.newFixedThreadPool(99);
        }
        return baseStationsExecutor;
    }

    public static ExecutorService getMobileStationsExecutor() {
        if (mobileStationsExecutor == null) {
            mobileStationsExecutor = Executors.newFixedThreadPool(99);
        }
        return mobileStationsExecutor;
    }

    public static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(Singleton.class);
        }
        return logger;
    }
}