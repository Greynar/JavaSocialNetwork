package com.JavaLearn.javasocial.service;

import com.JavaLearn.javasocial.model.Comment;
import com.JavaLearn.javasocial.model.User;
import com.JavaLearn.javasocial.model.Views;
import com.JavaLearn.javasocial.dto.EventType;
import com.JavaLearn.javasocial.dto.ObjectType;
import com.JavaLearn.javasocial.util.WsSender;
import com.JavaLearn.javasocial.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepository.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);
        return commentFromDb;
    }
}
