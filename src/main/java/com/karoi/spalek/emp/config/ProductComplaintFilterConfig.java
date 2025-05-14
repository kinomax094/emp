package com.karoi.spalek.emp.config;

import com.karoi.spalek.emp.filter.ProductComplainFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProductComplaintFilterConfig {

    private final ProductComplainFilter complainFilter;

    @Bean
    public FilterRegistrationBean<ProductComplainFilter> registerProductComplainFilter() {
        FilterRegistrationBean<ProductComplainFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(complainFilter);
        registration.addUrlPatterns("/productComplaint/*");
        registration.setOrder(1);
        return registration;
    }
}
