package com.bitsessential.blog.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitsessential.blog.entities.Comment;
import com.bitsessential.blog.entities.Post;
import com.bitsessential.blog.entities.SubComment;
import com.bitsessential.blog.repos.CommentRepository;
import com.bitsessential.blog.repos.PostRepository;
import com.bitsessential.blog.repos.SubCommentRepository;

import java.util.Optional;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/comments")
public class CommentController {
	private Connection<Facebook> facebookConn;
	private CommentRepository commentDao;
	private SubCommentRepository subCommentDao;
	private PostRepository postDao;
	
	public CommentController(Connection<Facebook> conn, CommentRepository commentDao, SubCommentRepository subCommentDao, PostRepository postDao) {
        this.facebookConn = conn;
        this.commentDao = commentDao;
        this.subCommentDao = subCommentDao;
        this.postDao = postDao;
    }
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String createComment(@RequestParam("commentContent") String content, @RequestParam("postId") long postId) {
		System.out.println(postId);
		ConnectionData data = facebookConn.createData();

		Comment cmt = new Comment();
		cmt.setCommentContent(content);
		cmt.setCommenterId(data.getProviderUserId());
		cmt.setCommenterName(data.getDisplayName());
		cmt.setCommenterPhoto(data.getImageUrl());
		cmt.setPost(postDao.findById(postId).get());
		commentDao.save(cmt);
		
		return "redirect:/posts/post/" + postId;
	}
	
	@RequestMapping(value = "/subcomment", method = RequestMethod.POST)
	public String createSubComment(@RequestParam("subCommentContent") String content, @RequestParam("commentId") long commentId) {
		System.out.println(commentId);
		ConnectionData data = facebookConn.createData();

		SubComment scmt = new SubComment();
		scmt.setSubCommentContent(content);
		scmt.setSubCommenterId(data.getProviderUserId());
		scmt.setSubCommenterName(data.getDisplayName());
		scmt.setSubCommenterPhoto(data.getImageUrl());
		
		Comment cmt = commentDao.findById(commentId).get();
		scmt.setComment(commentDao.findById(commentId).get());
		subCommentDao.save(scmt);
		
		return "redirect:/posts/post/" + cmt.getPost().getPostId();
	}
	
	@RequestMapping(value="/comment", method = RequestMethod.DELETE)
	public String deleteComment(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		return "post";
	}
	
	@RequestMapping(value="/comment", method = RequestMethod.PUT)
	public String editComment(Model model, @PathVariable long id) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());
		}
		
		return "post";
	}
	
}
