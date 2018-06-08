package com.bitsessential.blog.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitsessential.blog.entities.ApplicationUser;
import com.bitsessential.blog.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userDao;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody ApplicationUser user) {
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        userDao.save(user);
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
	
}
