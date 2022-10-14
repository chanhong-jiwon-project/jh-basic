package com.jhs.crud.auth.config;

import com.jhs.crud.auth.util.JwtAuthenticationEntryPoint;
import com.jhs.crud.auth.util.JwtAuthenticationFilter;
import io.swagger.models.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtRequestFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico");

        // swagger
        web.ignoring().antMatchers(
                "/v2/api-docs",  "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger/**","/user/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //다른 출처 리소스를 거부함
        http.headers().frameOptions().disable();

        http.authorizeRequests()

                .antMatchers(String.valueOf(HttpMethod.OPTIONS), "/**/*").permitAll()

                .antMatchers("/board/**").authenticated()
                .antMatchers("/issue/**").authenticated()
                .antMatchers("/user/changepw").authenticated()
//                .antMatchers("/").permitAll()


                // 모든 html 사용
                .antMatchers("/**.html").permitAll()

                .anyRequest().permitAll()

                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and()
//                .exceptionHandling();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    // 인증을 마침
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
