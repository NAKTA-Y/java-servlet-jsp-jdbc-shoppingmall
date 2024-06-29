package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;

public class PointThreadInitializer implements ServletContainerInitializer {
    private static final int DEFAULT_QUEUE_SIZE = 10;

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {

        RequestChannel requestChannel = new RequestChannel(DEFAULT_QUEUE_SIZE);
        //todo#14-1 servletContext에 requestChannel을 등록합니다.
        ctx.setAttribute("requestChannel", requestChannel);


        //todo#14-2 WorkerThread 사작합니다.
        WorkerThread workerThread = new WorkerThread(requestChannel);
        workerThread.start();
    }
}
