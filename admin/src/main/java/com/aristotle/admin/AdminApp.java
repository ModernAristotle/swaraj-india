package com.aristotle.admin;

import com.aristotle.admin.config.SwaggerConfig;
import com.aristotle.admin.filter.FilterConfig;
import com.aristotle.core.config.DatabaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
@SpringBootApplication
@ComponentScan(basePackages = {"com.aristotle.admin", "com.aristotle.core", "com.next.dynamo"})
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = {"com.aristotle.core.persistance.repo", "com.next.dynamo.persistance.repository"})
@EntityScan(basePackages = {"com.aristotle.core.persistance", "com.next.dynamo.persistance"})
@Import({SwaggerConfig.class, DatabaseConfig.class, FilterConfig.class})
public class AdminApp extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdminApp.class);
    }
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Object[]{AdminApp.class}, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            // System.out.println(beanName + " , " + ctx.getBean(beanName));
            System.out.println(beanName);
        }
    }
}

