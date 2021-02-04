package com.aib906.freyr.core.container;

import com.aib906.freyr.common.bean.PluginChain;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zjs
 * @Date: 2021/1/29 2:26 下午
 */
public class FreyrCommonContainer extends AbstractContainer {

    private final static Integer SHUTDOWN_TIME_AWAIT_SECONDS = 15;

    private ScheduledExecutorService scheduledExecutorService;

    public FreyrCommonContainer(PluginChain pluginChain) {
        super(pluginChain);
    }

    @Override
    public void start() {
        scheduledExecutorService =new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("container-"+getPluginChain().getName()+"-schedule-%d").daemon(true).build());
        scheduledExecutorService.scheduleAtFixedRate(getPluginChain(), 0, 15, TimeUnit.SECONDS);
//        getPluginChain().run();
    }

    @Override
    public void destroy() {
        try {
            if(null != scheduledExecutorService) {
                scheduledExecutorService.shutdown();
                if (scheduledExecutorService.awaitTermination(SHUTDOWN_TIME_AWAIT_SECONDS , TimeUnit.SECONDS)) {
                    scheduledExecutorService.shutdownNow();
                }
                getPluginChain().destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            scheduledExecutorService.shutdownNow();
        }
    }
}
