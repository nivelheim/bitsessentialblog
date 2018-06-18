package com.bitsessential.blog.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postId;
	
	@NotNull
	@Size(min = 1, max = 255)
	@Column
	private String postTitle;
	
	@Size(min = 1, max = 100000000)
	@Column
	private String postDescription;
	
	@NotNull
	@Size(min = 1, max = 100000000)
	@Column
	private String postContent;

	@Column
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime postDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category postCategory;
	
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
	
	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public LocalDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public Category getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(Category postCategory) {
		this.postCategory = postCategory;
	}
}