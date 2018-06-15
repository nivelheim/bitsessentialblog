package com.bitsessential.blog.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="subcomment")
public class SubComment implements Comparable<SubComment> {

	@Id
	@NotNull
	@Column
	private long subCommentId;
	
	@NotNull
	@Size(min = 1, max = 255)
	@Column
	private String subCommenterId;
	
	@NotNull
	@Size(min = 1, max = 30)
	@Column
	private String subCommenterName;
	
	@NotNull
	@Size(min = 1, max = 100000000)
	@Column
	private String subCommentContent;
	
	@Column
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime subCommentDate;
	
	@Column
	private String subCommenterPhoto;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Comment comment;

	public long getSubCommentId() {
		return subCommentId;
	}

	public void setSubCommentId(long subCommentId) {
		this.subCommentId = subCommentId;
	}

	public String getSubCommenterId() {
		return subCommenterId;
	}

	public void setSubCommenterId(String subCommenterId) {
		this.subCommenterId = subCommenterId;
	}

	public String getSubCommenterName() {
		return subCommenterName;
	}

	public void setSubCommenterName(String subCommenterName) {
		this.subCommenterName = subCommenterName;
	}

	public String getSubCommentContent() {
		return subCommentContent;
	}

	public void setSubCommentContent(String subCommentContent) {
		this.subCommentContent = subCommentContent;
	}

	public LocalDateTime getSubCommentDate() {
		return subCommentDate;
	}

	public void setSubCommentDate(LocalDateTime subCommentDate) {
		this.subCommentDate = subCommentDate;
	}

	public String getSubCommenterPhoto() {
		return subCommenterPhoto;
	}

	public void setSubCommenterPhoto(String subCommenterPhoto) {
		this.subCommenterPhoto = subCommenterPhoto;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	@Override
	public int compareTo(SubComment o) {
		return getSubCommentDate().compareTo(o.getSubCommentDate());
	}

}
