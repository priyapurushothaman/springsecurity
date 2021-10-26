package com.hackerrank.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john_doe")
                .password("{noop}student_password")
                .authorities("ROLE_STUDENT_USER")
                .and()
                .withUser("jane_doe")
                .password("{noop}admin_password")
                .authorities("ROLE_OFFICE_ADMIN");
    }
//stateless
//  @Override
//    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/course")
//                .hasRole("OFFICE_ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable();
//      http.authorizeRequests()
//              .antMatchers(HttpMethod.POST, "/course")
//              .hasRole("OFFICE_ADMIN").anyRequest().permitAll().and().csrf().disable();
//      http.authorizeRequests()
//              .antMatchers(HttpMethod.POST, "/course").hasRole("OFFICE_ADMIN")
//              .anyRequest()
//
//              .authenticated()
//              .and()
//              .httpBasic();

//      http
//              .authorizeRequests()
//              .anyRequest()
//              .authenticated()
//              .and()
//              .httpBasic();
//
//  }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    {
//        http
//                .csrf().disable()
//                .authorizeRequests().anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/course").hasAnyRole("OFFICE_ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//
//    }
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/course").hasRole("OFFICE_ADMIN")
            .anyRequest().authenticated()
            .and()
            .httpBasic().and().formLogin();
}
}
