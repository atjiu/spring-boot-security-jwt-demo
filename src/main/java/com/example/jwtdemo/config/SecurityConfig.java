package com.example.jwtdemo.config;

import com.example.jwtdemo.jwt.JwtAuthenticationTokenFilter;
import com.example.jwtdemo.jwt.JwtFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by tomoya at 2018/8/10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("jwtUserDetailsService")
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtFilterSecurityInterceptor jwtFilterSecurityInterceptor;

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
        .userDetailsService(this.userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
    return new JwtAuthenticationTokenFilter();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // 由于使用的是JWT，我们这里不需要csrf
        .csrf().disable()
        // 基于token，所以不需要session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        // 允许对于网站静态资源的无授权访问
        .antMatchers(
            HttpMethod.GET,
            "/",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
        ).permitAll()
        // 登录注册要给予访问
        .antMatchers("/login", "/register").permitAll()
        .anyRequest().authenticated();

    // 禁用缓存
    http.headers().cacheControl();

    //添加JWT filter
    http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterAfter(jwtFilterSecurityInterceptor, FilterSecurityInterceptor.class);

  }
}
