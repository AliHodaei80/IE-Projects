package ir.ie.mizdooni.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF if you are testing, but ensure to enable it in production with proper configurations
                .requiresChannel(channel ->
                        channel.anyRequest().requiresSecure()
                );
        return http.build();
    }
}
