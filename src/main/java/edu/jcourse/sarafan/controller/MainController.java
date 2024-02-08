package edu.jcourse.sarafan.controller;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final MessageService messageService;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> data = new HashMap<>();

        Optional.ofNullable(oidcUser)
                .map(OidcUser::getUserInfo)
                .map(OidcUserInfo::getClaims)
                .map(claims -> (UserDto) claims.get("user"))
                .ifPresent(userDto -> {
                    data.put("profile", userDto);
                    data.put("messages", messageService.findAll());
                });

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(activeProfile));
        return "index";
    }
}