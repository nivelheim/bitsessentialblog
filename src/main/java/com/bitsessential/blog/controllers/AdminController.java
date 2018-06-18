package com.bitsessential.blog.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitsessential.blog.entities.Category;
import com.bitsessential.blog.entities.Post;
import com.bitsessential.blog.repos.CategoryRepository;
import com.bitsessential.blog.repos.PostRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/admin")
public class AdminController {
	private CategoryRepository cateRepo;
	private PostRepository postRepo;
	
	public AdminController(CategoryRepository crepo, PostRepository prepo) {
		this.cateRepo = crepo;
		this.postRepo = prepo;
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String listAllPosts(Model model, Pageable pageable) {
		Page<Post> posts = postRepo.findAll(pageable);
		model.addAttribute("posts", posts);
		return "admin/main";
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String getCategory(Model model) {
		List<Category> categories = cateRepo.findAll();
		model.addAttribute("categories", categories);
		return "admin/category";
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String addCategory(Category category) {
		cateRepo.save(category);
		return "admin/category";
	}
	
	@RequestMapping(value = "/category/", method = RequestMethod.POST)
	public String deleteCategory(Category category) {
		cateRepo.save(category);
		return "admin/category";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Model model) {
		List<Category> categories = cateRepo.findAll();
		model.addAttribute("categories", categories);
		return "admin/write";
	}

}
