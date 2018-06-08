package com.bitsessential.blog.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitsessential.blog.entities.Post;
import com.bitsessential.blog.repos.PostRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	private Facebook facebook;
	
	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private PostRepository postDao;
	
	@RequestMapping(value = "editor", method = RequestMethod.GET)
	public String getEditor(Post post) {
		return "editor";
	}
	
	@RequestMapping(value = "editor", method = RequestMethod.POST)
	public String createPost(Post post) {
		post.setPostDate(LocalDate.now());
		postDao.save(post);
		return "redirect:/posts";
	}
	
	@RequestMapping(value = "editor/{id}", method = RequestMethod.GET)
	public String getEditorToEdit(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		return "editor_edit";
	}
	

	@RequestMapping("")
	public String listAll(Model model) {
		List<Post> posts = postDao.findAll();
		model.addAttribute("posts", posts);
		return "index";
	}

	@RequestMapping(value = "post/{id}", method = RequestMethod.GET)
	public String getPost(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		return "post";
	}
	
	@RequestMapping(value="post/{id}", method = RequestMethod.DELETE)
	public String deletePost(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		return "post";
	}
	
	@RequestMapping(value="post/{id}", method = RequestMethod.PUT)
	public String editPost(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			model.addAttribute("facebook", null);
		}
		else {
			model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
	        PagedList<org.springframework.social.facebook.api.Post> feed = facebook.feedOperations().getFeed();
	        model.addAttribute("feed", feed);
		}
		
		return "post";
	}
	
}
