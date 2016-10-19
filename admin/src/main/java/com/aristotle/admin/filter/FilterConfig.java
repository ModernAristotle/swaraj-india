package com.aristotle.admin.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter());
        registration.addUrlPatterns("/home");
        registration.setName("loginFilter");
        registration.setOrder(1);
        return registration;
    }

    // @Bean
    public FilterRegistrationBean loginViewFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginViewFilter());
        registration.addUrlPatterns("/me/*", "/admin/*");
        registration.setName("loginViewFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public Filter loginFilter() {
        return new LoginFilter();
    }

    //@Bean
    public Filter loginViewFilter() {
        return new LoginViewFilter();
    }


}
