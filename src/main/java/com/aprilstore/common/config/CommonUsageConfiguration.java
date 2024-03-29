package com.aprilstore.common.config;

import com.aprilstore.common.aspect.DelayReturnAspect;
import com.aprilstore.common.aspect.HibernateValidatorAspect;
import com.aprilstore.common.aspect.ManualExceptionAspect;
import com.aprilstore.common.aspect.RequestIdStuffAspect;
import com.aprilstore.common.aspect.RequestLoggingAspect;
import com.aprilstore.common.web.filter.ResettableRequestFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author Zhao Junjian
 */
@Configuration
@EnableConfigurationProperties({DelayProperties.class, ManualExceptionProperties.class})
public class CommonUsageConfiguration {
    @Bean
    public DelayReturnAspect delayReturnAspect(DelayProperties properties) {
        return new DelayReturnAspect(Ordered.LOWEST_PRECEDENCE, properties);
    }

    @Bean
    public ManualExceptionAspect manualExceptionAspect(ManualExceptionProperties properties) {
        return new ManualExceptionAspect(Ordered.LOWEST_PRECEDENCE - 1, properties);
    }

    @Bean
    public ResettableRequestFilter resettableRequestFilter() {
        return new ResettableRequestFilter();
    }

    @Bean
    public RequestIdStuffAspect idStuffAspect() {
        final int order = Byte.MAX_VALUE;
        return new RequestIdStuffAspect(order);
    }

    @Bean
    public RequestLoggingAspect logsAspect() {
        final int order = Byte.MAX_VALUE + 1;
        return new RequestLoggingAspect(order);
    }

    @Bean
    @ConditionalOnMissingBean
    public HibernateValidatorAspect hibernateValidatorAspect() {
        final int order = Byte.MAX_VALUE + 2;
        return new HibernateValidatorAspect(order);
    }

}
