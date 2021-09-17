package com.peasch.jeuxagogo.controller.security.config;

import com.peasch.jeuxagogo.controller.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService uds;
    /*@Autowired
    private PasswordEncoder passwordEncoder;*/
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String MEMBER = "MEMBER";
    private static final String MODERATOR = "MODERATOR";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uds).passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/user/sendResetMail").permitAll()
                .antMatchers("/user/resetPassword").permitAll()
                .antMatchers("/game/all/**").permitAll()
                .antMatchers("/game/id/**").permitAll()
                .antMatchers("/style/**").permitAll()
                .antMatchers("/user/**").hasAnyRole(USER, ADMIN, MODERATOR, MEMBER)
                .antMatchers("/game/add").hasAnyRole(ADMIN, MODERATOR)
                .antMatchers("/copy/**").hasAnyRole(ADMIN, MODERATOR)
                .antMatchers("/waitlist/**").hasAnyRole(ADMIN, MODERATOR, MEMBER, USER)
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        http.headers()
                .addHeaderWriter(
                        new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:4200")
                );
        http.cors();
    }


    @Override
    public void configure(WebSecurity web) throws Exception { // Nous configurons l'accès aux ressources statics du site.
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() { // Gestion des erreurs d'authentification ou des accès interdits
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized");
    }

    @Bean
    public UserDetailsService myUserDetails() { // Appel à CustomDetailsService.
        return new CustomUserDetailsService();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
