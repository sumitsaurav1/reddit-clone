package com.javateam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password, enabled FROM users WHERE email = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT null,null from users WHERE email = ?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/posts/upvote/**","/posts/downvote/**",
                                        "/addComment/**","/replyOnComment/**","/profile/**","/create-post","/create-community",
                                        "/drafts","/comments/upvote/**","/comments/downvote/**").authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateUser")
                                .defaultSuccessUrl("/posts")
                                .permitAll()
                )
                .logout(logout ->
                        logout.permitAll());

        return http.build();
    }

}
