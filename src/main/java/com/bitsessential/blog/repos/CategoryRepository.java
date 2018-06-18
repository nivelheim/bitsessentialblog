package com.bitsessential.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bitsessential.blog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
