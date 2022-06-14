package com.JavaLearn.javasocial.repository;

import com.JavaLearn.javasocial.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
