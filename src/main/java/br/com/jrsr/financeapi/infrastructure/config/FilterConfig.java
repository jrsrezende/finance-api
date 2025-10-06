package br.com.jrsr.financeapi.infrastructure.config;

import br.com.jrsr.financeapi.infrastructure.filters.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Value("${jwt_key}")
    private String jwtKey;

    @Bean
    FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new JwtAuthFilter(jwtKey));
        registration.addUrlPatterns("/api/*");

        return registration;
    }

}
