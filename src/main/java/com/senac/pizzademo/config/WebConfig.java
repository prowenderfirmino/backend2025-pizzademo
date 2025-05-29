package com.senac.pizzademo.config;

import com.senac.pizzademo.security.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/pizza/*");
        registrationBean.setOrder(1); // Garante prioridade do filtro se houver outros
        return registrationBean;
    }
}
