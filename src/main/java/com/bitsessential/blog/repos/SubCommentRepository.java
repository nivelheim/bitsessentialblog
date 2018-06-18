package com.bitsessential.blog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bitsessential.blog.entities.SubComment;

@Repository
public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
	SubComment findByCommentCommentId(long commentId);
	public List<SubComment> findAllByCommentCommentId(long commentId);
}
