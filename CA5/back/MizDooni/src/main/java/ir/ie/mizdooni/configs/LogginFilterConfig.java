package ir.ie.mizdooni.configs;

import ir.ie.mizdooni.controllers.LogginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogginFilterConfig {
    @Bean
    public FilterRegistrationBean<LogginFilter> loggingFilter() {
        FilterRegistrationBean<LogginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogginFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
