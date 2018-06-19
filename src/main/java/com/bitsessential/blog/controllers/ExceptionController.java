package com.bitsessential.blog.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {
	
    @ExceptionHandler(value = Exception.class)
    public String handleError(HttpServletRequest request, Exception e, Model model)   {
        //Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
    	model.addAttribute("code", e.getMessage());
    	e.printStackTrace();
        return "error";
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Exception e)   {
    	
    	return "error";
    }
}