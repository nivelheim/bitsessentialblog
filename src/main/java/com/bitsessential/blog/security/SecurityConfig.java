package com.bitsessential.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.bitsessential.blog.services.UserDetailsServiceImpl;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        		.authorizeRequests()
	                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
	                .antMatchers(HttpMethod.GET, "/*").permitAll()
	                .antMatchers(HttpMethod.GET, "/sources/**").permitAll()
	                .antMatchers(HttpMethod.GET, "/posts/post/*").permitAll()
	                .antMatchers(HttpMethod.GET, "/posts").permitAll()
	                .antMatchers(HttpMethod.GET, "/posts/editor/").hasAnyRole("admin")
	                .antMatchers(HttpMethod.GET, "/posts/editor/*/").hasAnyRole("admin")
	                .antMatchers(HttpMethod.GET, "/connect/facebook").permitAll()
	                .anyRequest().authenticated()
	                .and()
                .formLogin()
                	.loginPage("/user/login")
                	.loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/post/list")	
                	.permitAll()
                	.and()
                .logout()    //logout configuration
            		.logoutUrl("/app-logout") 
            		.logoutSuccessUrl("/app/login")
            		.and()
            	.exceptionHandling() //exception handling configuration
            		.accessDeniedPage("/error");
    }
    
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
