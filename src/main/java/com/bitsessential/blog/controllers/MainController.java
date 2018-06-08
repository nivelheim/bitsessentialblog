package com.bitsessential.blog.controllers;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/")
public class MainController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "redirect:/posts";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "error";
	}

}
