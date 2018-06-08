package com.bitsessential.blog.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="post-uuid")
	//@GenericGenerator(name="post-uuid", strategy = "uuid")
	//@Size(max = 6)
	long postId;
	
	@NotNull
	@Size(min = 1, max = 255)
	@Column
	String postTitle;
	
	@NotNull
	@Size(min = 1, max = 100000000)
	@Column
	String postContent;

	@Column
	LocalDate postDate;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long id) {
		this.postId = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String title) {
		this.postTitle = title;
	}
	
	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public LocalDate getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDate postDate) {
		this.postDate = postDate;
	}
}