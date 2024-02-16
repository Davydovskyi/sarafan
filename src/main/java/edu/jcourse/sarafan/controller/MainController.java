package edu.jcourse.sarafan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final ObjectMapper objectMapper;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> data = new HashMap<>();
        ObjectWriter writer = objectMapper.writerWithView(View.FullMessage.class);

        getProfile(oidcUser).ifPresentOrElse(userDto -> {
                    data.put("profile", userDto);
                    model.addAttribute("messages", toJson(messageService.findAll(), writer));
                },
                () -> model.addAttribute("messages", "[]"));

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(activeProfile));
        return "index";
    }

    private Optional<UserDto> getProfile(OidcUser oidcUser) {
        return Optional.ofNullable(oidcUser)
                .map(OidcUser::getUserInfo)
                .map(OidcUserInfo::getClaims)
                .map(claims -> (UserDto) claims.get("user"));
    }

    @SneakyThrows
    private String toJson(Object payload, ObjectWriter writer) {
        return writer.writeValueAsString(payload);
    }
}