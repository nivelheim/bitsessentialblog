package com.bitsessential.blog.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="comment")
public class Comment implements Comparable<Comment> {

	@Id
	@NotNull
	@Column
	private long commentId;
	
	@NotNull
	@Size(min = 1, max = 255)
	@Column
	private String commenterId;
	
	@NotNull
	@Size(min = 1, max = 30)
	@Column
	private String commenterName;
	
	@NotNull
	@Size(min = 1, max = 100000000)
	@Column
	private String commentContent;
	
	@Column
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime commentDate;
	
	@Column
	private String commenterPhoto;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;
	
	@Transient
	private List<SubComment> subComments;


	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getCommenterId() {
		return commenterId;
	}

	public void setCommenterId(String commenterId) {
		this.commenterId = commenterId;
	}

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public LocalDateTime getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(LocalDateTime commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommenterPhoto() {
		return commenterPhoto;
	}

	public void setCommenterPhoto(String commenterPhoto) {
		this.commenterPhoto = commenterPhoto;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<SubComment> getSubComments() {
		return subComments;
	}

	public void setSubComments(List<SubComment> subComments) {
		this.subComments = subComments;
	}

	@Override
	public int compareTo(Comment o) {
		return getCommentDate().compareTo(o.getCommentDate());
	}

}
