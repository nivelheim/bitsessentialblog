package com.bitsessential.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.bitsessential.blog.services.FacebookConnectionSignup;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
        Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
		environment.getProperty("spring.social.facebook.appId"), environment.getProperty("spring.social.facebook.appSecret")));

	}
	
	@Override
    public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}
     
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(new FacebookConnectionSignup());
        return repository;
    }
    
    
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
                                             ConnectionRepository connectionRepository, Environment environment) {
    	ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
        controller.setApplicationUrl(environment.getProperty("spring.application.url"));
        return controller;
    }

}
