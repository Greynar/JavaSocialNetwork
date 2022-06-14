package com.JavaLearn.javasocial.controller;

import com.JavaLearn.javasocial.dto.MessagePageDto;
import com.JavaLearn.javasocial.model.User;
import com.JavaLearn.javasocial.model.Views;
import com.JavaLearn.javasocial.repository.UserDetailsRepository;
import com.JavaLearn.javasocial.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final UserDetailsRepository userDetailsRepository;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(MessageService messageService, UserDetailsRepository userDetailsRepository, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userDetailsRepository = userDetailsRepository;

        ObjectMapper objectMapper = mapper
                .setConfig(mapper.getSerializationConfig());

        this.messageWriter = objectMapper
                .writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal OidcUser oidcUser) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        User user = null;
        if (!(oidcUser == null)) {
            user = userDetailsRepository.findByEmail(oidcUser.getEmail()).stream().findFirst().orElse(null);
        }

        if (user != null) {
            User userFromDb = userDetailsRepository.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findAll(pageRequest);

            String messages = messageWriter.writeValueAsString(messagePageDto.getMessages());

            model.addAttribute("messages", messages);
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
