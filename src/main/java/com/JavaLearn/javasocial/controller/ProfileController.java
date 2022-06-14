package com.JavaLearn.javasocial.controller;

import com.JavaLearn.javasocial.model.User;
import com.JavaLearn.javasocial.model.Views;
import com.JavaLearn.javasocial.repository.UserDetailsRepository;
import com.JavaLearn.javasocial.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public ProfileController(ProfileService profileService, UserDetailsRepository userDetailsRepository) {
        this.profileService = profileService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable("channelId") User channel
    ) {
        User subscriber = userDetailsRepository.findByEmail(oidcUser.getEmail()).stream().findFirst().orElse(null);

        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }
}
