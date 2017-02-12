package com.next.aristotle;

import com.aristotle.core.config.DatabaseConfig;
import com.next.dynamo.context.DynamoServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@SpringBootApplication
@Import({DatabaseConfig.class, DynamoServiceContext.class})
public class App extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(new Object[]{App.class, DatabaseConfig.class}, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            // System.out.println(beanName + " , " + ctx.getBean(beanName));
            System.out.println(beanName);
        }
    }
}

