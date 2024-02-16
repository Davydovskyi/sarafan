package edu.jcourse.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.jcourse.sarafan.dto.CommentDto;
import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.FullMessage.class)
    public CommentDto create(@RequestBody CommentDto commentDto,
                             @AuthenticationPrincipal OidcUser oidcUser) {
        return getUser(oidcUser)
                .map(userDto -> commentService.create(commentDto.toBuilder().user(userDto).build()))
                .orElseThrow();
    }

    private Optional<UserDto> getUser(OidcUser oidcUser) {
        return Optional.ofNullable(oidcUser)
                .map(OidcUser::getUserInfo)
                .map(OidcUserInfo::getClaims)
                .map(claims -> (UserDto) claims.get("user"));
    }
}