package com.multitrans.wasalliya.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingService {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void logInfo(String message){
        logger.info("[Custom Info] : " + message);
    }

//    public void logError(String message, Throwable error){
//        logger.error("[Custom Error] : " + message , error);
//    }

    public void logWarning(String message){
        logger.warn("[Custom Warning] : " + message);
    }
}
