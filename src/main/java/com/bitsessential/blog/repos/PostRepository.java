package com.bitsessential.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bitsessential.blog.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
