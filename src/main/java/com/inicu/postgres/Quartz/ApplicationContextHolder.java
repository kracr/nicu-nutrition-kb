package com.inicu.postgres.Quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
@EnableAutoConfiguration
public final class ApplicationContextHolder extends SpringBeanJobFactory implements ApplicationContextAware {

    private static ApplicationContext context;
    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        beanFactory = ctx.getAutowireCapableBeanFactory();
        context = ctx;
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}