package com.bitsessential.blog.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bitsessential.blog.entities.Category;
import com.bitsessential.blog.entities.Comment;
import com.bitsessential.blog.entities.FacebookUser;
import com.bitsessential.blog.entities.Post;
import com.bitsessential.blog.entities.SubComment;
import com.bitsessential.blog.repos.CategoryRepository;
import com.bitsessential.blog.repos.CommentRepository;
import com.bitsessential.blog.repos.PostRepository;
import com.bitsessential.blog.repos.SubCommentRepository;
import com.bitsessential.blog.services.AmazonS3Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/posts")
public class PostController {
	private Connection<Facebook> facebookConn;
	private PostRepository postDao;
	private CommentRepository commentDao;
	private SubCommentRepository subCommentDao;
	private CategoryRepository categoryDao;
	private AmazonS3Service amzServ;
	
	public PostController(Connection<Facebook> conn, PostRepository repo, CommentRepository commentDao, SubCommentRepository subCommentDao, CategoryRepository catDao, AmazonS3Service amzServ) {
        this.facebookConn = conn;
        this.postDao = repo;
        this.commentDao = commentDao;
    	this.subCommentDao = subCommentDao;
    	this.categoryDao = catDao;
    	this.amzServ = amzServ;
    }
	
	@RequestMapping("")
	public String listAllPosts(Model model, @PageableDefault(sort = {"postId"}, direction = Direction.DESC, size = 6) Pageable pageable) {
		Page<Post> posts = postDao.findAll(pageable);
		model.addAttribute("posts", posts);
		return "index";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String createPost(@RequestParam("postContent") String postContent,
							 @RequestParam("postTitle") String postTitle,
							 @RequestParam("postDescription") String postDesc,
							 @RequestParam("postImage") MultipartFile postImage,
							 @RequestParam("postCategory") long postCat
							) {
		Optional<Category> cat = categoryDao.findById(postCat);
		
		Post post = new Post();
		post.setPostContent(postContent);
		post.setPostTitle(postTitle);
		post.setPostDescription(postDesc);
		post.setPostCategory(cat.get());
		post.setPostImage(amzServ.uploadFile(postImage));
		postDao.save(post);
		return "redirect:/admin/main";
	}

	@RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	public String getPost(Model model, @PathVariable long id) throws Exception {
		Optional<Post> post = postDao.findById(id);
		
		List<Comment> comments = commentDao.findAllByPostPostId(id);
		if (comments != null) {
			Collections.sort(comments);
			for(Comment c : comments) {
				List<SubComment> subComments = subCommentDao.findAllByCommentCommentId(c.getCommentId());
				if (subComments != null) {
					Collections.sort(subComments);
					c.setSubComments(subComments);
				}
			}
			
			model.addAttribute("comments", comments);
		}
		
		
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		} else {
			throw new Exception();
		}
		
		try {
			FacebookUser user = getFacebookUser();
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", null);
	        System.out.println("user null");
		}
		
		return "post";
	}
	
	@RequestMapping(value="/post/delete/{id}", method = RequestMethod.GET)
	public String deletePost(@PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		
		if (post.isPresent()) {
			if (post.get().getPostImage() != null) {
				amzServ.deleteFileFromS3Bucket(post.get().getPostImage());
			}
			postDao.delete(post.get());
		}
		
		return "redirect:/admin/main";
	}
	
	@RequestMapping(value="/post/edit/{id}", method = RequestMethod.GET)
	public String getEditor(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		List<Category> categories = categoryDao.findAll();
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
			model.addAttribute("categories", categories);
		}
		
		return "admin/edit";
	}
	
	@RequestMapping(value="/post/edit", method = RequestMethod.POST)
	public String editPost(@RequestParam("postId") long postId,
						   @RequestParam("postContent") String postContent,
						   @RequestParam("postTitle") String postTitle,
						   @RequestParam("postDescription") String postDesc,
						   @RequestParam("postImage") MultipartFile postImage,
						   @RequestParam("postCategory") long postCat) {
		Optional<Post> post = postDao.findById(postId);
		Post newPost;
		Optional<Category> cat = categoryDao.findById(postCat);
		
		if (post.isPresent()) {
			newPost = post.get();
			System.out.println(newPost.getPostImage());
			newPost.setPostTitle(postTitle);
			newPost.setPostContent(postContent);
			newPost.setPostDescription(postDesc);
			
			if (!postImage.isEmpty()) {
				newPost.setPostImage(amzServ.uploadFile(postImage));
			}
			
			newPost.setPostCategory(cat.get());
			
			postDao.save(newPost);
		}
		
		return "redirect:/admin/main";
		
	}
	
	private FacebookUser getFacebookUser() {
		try {
			ConnectionData data = facebookConn.createData();
			return new FacebookUser(data.getProviderId(), data.getDisplayName());
		} catch(Exception e) {
			return null;
		}
	}
	
}
