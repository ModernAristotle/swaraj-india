package com.aristotle.admin.dummy.impl;

import com.aristotle.core.service.EmailManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class IntegrationTestConfig {

    @Bean
    @Primary
    public EmailManager dummyEmailManager() {
        return new DummyEmailManagerImpl();
    }
}
