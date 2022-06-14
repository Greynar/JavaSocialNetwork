package com.JavaLearn.javasocial.controller;

import com.JavaLearn.javasocial.dto.MessagePageDto;
import com.JavaLearn.javasocial.model.Message;
import com.JavaLearn.javasocial.model.User;
import com.JavaLearn.javasocial.model.Views;
import com.JavaLearn.javasocial.repository.UserDetailsRepository;
import com.JavaLearn.javasocial.service.MessageService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("message")
public class MessageController {
    private final UserDetailsRepository userDetailsRepository;
    public static final int MESSAGES_PER_PAGE = 3;
    private final MessageService messageService;

    public MessageController(MessageService messageService, UserDetailsRepository userDetailsRepository) {
        this.messageService = messageService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@PageableDefault(size = MESSAGES_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return messageService.findAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOneMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    @JsonView(Views.FullMessage.class)
    public Message createMessage(@RequestBody Message message, @AuthenticationPrincipal OidcUser oidcUser) throws IOException {
        User user = userDetailsRepository.findByEmail(oidcUser.getEmail()).stream().findFirst().orElse(null);
        return messageService.create(message, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message updateMessage(@PathVariable("id") Message messageFromDb, @RequestBody Message message) throws IOException {
        return messageService.update(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        messageService.delete(message);
    }
}
