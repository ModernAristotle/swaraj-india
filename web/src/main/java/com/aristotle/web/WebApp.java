package com.aristotle.web;

import com.aristotle.core.config.DatabaseConfig;
import com.next.dynamo.context.DynamoServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aristotle.web"})
@EnableAutoConfiguration
@EnableCaching
@Import({DatabaseConfig.class, DynamoServiceContext.class})
@EntityScan(basePackages = {"com.aristotle.core.persistance"})
public class WebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApp.class);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Object[]{WebApp.class}, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            // System.out.println(beanName + " , " + ctx.getBean(beanName));
            System.out.println(beanName);
        }
    }

//    @Bean
//    public CacheManager cacheManager() {
//        CacheBuilder cacheBuilder = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES);
//        GuavaCacheManager guavaCacheManager = new GuavaCacheManager("events", "videos", "news", "blogs");
//        guavaCacheManager.setCacheBuilder(cacheBuilder);
//        return guavaCacheManager;
//    }

}
