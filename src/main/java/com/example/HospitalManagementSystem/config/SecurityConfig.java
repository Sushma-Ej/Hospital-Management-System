package com.example.HospitalManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        PasswordEncoder encoder = passwordEncoder();
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("tej")
                .password(encoder.encode("password"))
                .roles("USER", "PATIENT")
                .and()
                .withUser("vajra")
                .password(encoder.encode("password"))
                .roles("USER", "RECEPTIONIST")
                .and()
                .withUser("sush")
                .password(encoder.encode("password"))
                .roles("USER", "DOCTOR");
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/main").permitAll()
                .requestMatchers("/signup").permitAll()
                .requestMatchers("/doctors/**").hasRole("DOCTOR")
                .requestMatchers("/patients").hasRole("PATIENT")
                .requestMatchers("/patients/**").hasRole("PATIENT")
                .requestMatchers("/updateAppointment/**").hasRole("RECEPTIONIST")
                .requestMatchers("/rooms/**").permitAll()
                .requestMatchers("/receptionist").hasRole("RECEPTIONIST")
                .requestMatchers("/receptionist/**").hasRole("RECEPTIONIST")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // Explicitly set custom login page
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll();

        // .defaultSuccessUrl("/showPostLogin", false)
        // .permitAll()
        // .and()
        // .logout()
        // .logoutSuccessUrl("/")
        // .permitAll();

        return http.build();
    }
}





