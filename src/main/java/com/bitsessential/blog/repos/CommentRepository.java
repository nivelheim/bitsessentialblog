package com.bitsessential.blog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsessential.blog.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	public Comment findByPostPostId(long postId);
	public List<Comment> findAllByPostPostId(long postId);
}
