package com.example.master_app.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/utilisateur/**").hasRole("RH")
                        .requestMatchers("/candidat/register", "/candidat/login").permitAll()
                        .requestMatchers("/candidat/**").hasRole("CANDIDAT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/candidat/login")
                        .loginProcessingUrl("/process-login")
                        .defaultSuccessUrl("/candidat/home", true)
                        .permitAll()
                )

                .logout()
                .and()
                .csrf().disable(); // à adapter selon besoin (ex. pour REST API)

        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        var manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withUsername("rh")
//                        .password(passwordEncoder().encode("password"))
//                        .roles("RH") // rôle RH
//                        .build()
//        );
//        return manager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }
}

