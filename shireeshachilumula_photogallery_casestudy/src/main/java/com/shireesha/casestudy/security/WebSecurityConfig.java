package com.shireesha.casestudy.security;

import com.shireesha.casestudy.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SuppressWarnings("unused")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
   

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable().authorizeRequests()
                .antMatchers("/global/**", "/static/**").permitAll()
                .antMatchers("/galleries").authenticated()
                .anyRequest().permitAll()
                .and()
                //handling login if login is success it redirects to galleries
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/galleries")
                .permitAll()
               //.failureUrl("/login-error")
                .and()
             // Handle logout
    			.logout().invalidateHttpSession(true).clearAuthentication(true)
    			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    			.logoutSuccessUrl("/logoutsuccess").permitAll()
    			// Handle error page
                .and()
                .exceptionHandling().accessDeniedPage("/error");

    }
}
