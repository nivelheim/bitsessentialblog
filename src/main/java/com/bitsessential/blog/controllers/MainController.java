package com.bitsessential.blog.controllers;



import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitsessential.blog.entities.Post;
import com.bitsessential.blog.repos.PostRepository;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class MainController {
	private PostRepository postDao;
	
	public MainController(PostRepository repo) {
		this.postDao = repo;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "redirect:/posts";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "error";
	}
	
	@RequestMapping(value = "/editor", method = RequestMethod.GET)
	public String getEditor() {
		return "editor";
	}
	
	@RequestMapping(value = "/editor/{id}", method = RequestMethod.GET)
	public String getEditorToEdit(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		return "editor_edit";
	}

}
