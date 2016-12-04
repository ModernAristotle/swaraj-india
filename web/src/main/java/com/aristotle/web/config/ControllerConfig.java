package com.aristotle.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import java.text.DateFormat;

public class ControllerConfig {

    //@Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    private DateFormat iSO8601DateFormat = new ISO8601DateFormat();

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(iSO8601DateFormat);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, false);

        return objectMapper;
    }

    /**
     * Register dispatcherServlet programmatically
     *
     * @return ServletRegistrationBean
     */
//    @Bean
//    public ServletRegistrationBean dispatcherServletRegistration() {
//
//        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/abc.html", "/content/news/10");
//
//        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
//
//        return registration;
//    }

//    @Bean
//    public ViewResolver getViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/jsps/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }

}
