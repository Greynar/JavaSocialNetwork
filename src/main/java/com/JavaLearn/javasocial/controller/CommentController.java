package com.JavaLearn.javasocial.controller;

import com.JavaLearn.javasocial.model.Comment;
import com.JavaLearn.javasocial.model.User;
import com.JavaLearn.javasocial.model.Views;
import com.JavaLearn.javasocial.repository.UserDetailsRepository;
import com.JavaLearn.javasocial.service.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserDetailsRepository userDetailsRepository;

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(@RequestBody Comment comment, @AuthenticationPrincipal OidcUser oidcUser) {
        User user = userDetailsRepository.findByEmail(oidcUser.getEmail()).stream().findFirst().orElse(null);
        return commentService.create(comment, user);
    }
}
