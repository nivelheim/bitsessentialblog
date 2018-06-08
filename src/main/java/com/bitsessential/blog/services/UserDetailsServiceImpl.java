package com.bitsessential.blog.services;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bitsessential.blog.entities.ApplicationUser;
import com.bitsessential.blog.repos.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userDao;

	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userDao.findByUserName(username);
             
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        else {
        	GrantedAuthority authority = new SimpleGrantedAuthority(applicationUser.getUserType());
        	UserDetails userDetails = (UserDetails) new User(applicationUser.getUserName(), applicationUser.getUserPassword(), Arrays.asList(authority));
            return userDetails;
        }   
    }

}
