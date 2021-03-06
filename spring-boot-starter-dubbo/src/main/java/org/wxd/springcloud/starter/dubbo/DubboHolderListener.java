package org.wxd.springcloud.starter.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Objects;


public class DubboHolderListener implements ApplicationListener {

    private static Logger logger = LoggerFactory.getLogger(DubboHolderListener.class);

    private static Thread holdThread;

    private static Boolean running = Boolean.FALSE;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationPreparedEvent) {
            if (Objects.equals(running,Boolean.FALSE)) running = Boolean.TRUE;

            if (Objects.isNull(holdThread)) {
                holdThread = new Thread(() -> {
                    logger.info(Thread.currentThread().getName());

                    while (running && !Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            logger.error("onApplicationEvent error={}",e);
                        }
                    }
                }, "dubbo-holder");

                holdThread.setDaemon(false);

                holdThread.start();
            }
        }

        if (event instanceof ContextClosedEvent) {
            running = Boolean.FALSE;
            if (Objects.nonNull(holdThread)) {
                holdThread.interrupt();
                holdThread = null;
            }
        }
    }
}
