package com.aristotle.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

public class ControllerConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
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
