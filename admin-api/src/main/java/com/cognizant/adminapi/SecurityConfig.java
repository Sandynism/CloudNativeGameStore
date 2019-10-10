package com.cognizant.adminapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        //order them with most specific to most general
        httpSecurity.authorizeRequests()
//                .mvcMatchers("/login").authenticated()
                .mvcMatchers(HttpMethod.PUT, "/products/*", "/customers/*", "/levelup/*", "/invoices/*", "/inventory/*").hasAnyRole("ADMIN", "MANAGER", "TEAMLEAD")
                .mvcMatchers(HttpMethod.PUT, "/inventory/*").hasRole("EMPLOYEE")
                .mvcMatchers(HttpMethod.DELETE, "/products/*", "/customers/*", "/levelup/*", "/invoices/*", "/inventory/*").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/products", "/levelup", "/invoices", "/inventory", "/customers").hasAnyRole("MANAGER, ADMIN")
                .mvcMatchers(HttpMethod.POST, "/customers").hasRole("TEAMLEAD")
                .anyRequest().permitAll();


        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }
}



//The security rules for the Admin API Service are:
//
//All Admin API endpoints require authentication.
//Admin Role
//
//Can access all endpoints.
//
//
//Manager Role
//
//Can Create, Read, and Update all items in the system.
//
//
//Team Lead Role
//
//Can Read and Update all items in the system.
//Can Create Customers in the system.
//
//
//Employee Role
//
//Can read all items in the system.
//Can Update Inventory in the system.