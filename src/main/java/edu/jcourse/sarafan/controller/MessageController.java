package edu.jcourse.sarafan.controller;

import edu.jcourse.sarafan.dto.EventType;
import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.dto.ObjectType;
import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.MessageService;
import edu.jcourse.sarafan.util.WsSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final BiConsumer<EventType, MessageDto> wsSender;

    public MessageController(MessageService messageService, WsSender wsSender) {
        this.messageService = messageService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, View.IdName.class);
    }

    @GetMapping
    public List<MessageDto> getAll() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public MessageDto get(@PathVariable Long id) {
        return messageService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDto create(@RequestBody MessageDto message,
                             @AuthenticationPrincipal OidcUser oidcUser) {
        MessageDto filledMessage = fillMeta(message);
        return getUser(oidcUser)
                .map(userDto -> {
                    MessageDto messageDto = messageService.create(filledMessage.toBuilder()
                            .user(userDto)
                            .build());
                    wsSender.accept(EventType.CREATED, messageDto);
                    return messageDto;
                })
                .orElseThrow();
    }

    @PutMapping("/{id}")
    public MessageDto update(@PathVariable Long id,
                             @RequestBody MessageDto message) {
        message = fillMeta(message);
        MessageDto messageDto = messageService.update(id, message)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        wsSender.accept(EventType.UPDATED, messageDto);
        return messageDto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        wsSender.accept(EventType.DELETED, messageService.findById(id)
                .orElse(MessageDto.builder().build()));
        return messageService.deleteById(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    private MessageDto fillMeta(MessageDto message) {
        return messageService.fillMeta(message);
    }

    private Optional<UserDto> getUser(OidcUser oidcUser) {
        return Optional.ofNullable(oidcUser)
                .map(OidcUser::getUserInfo)
                .map(OidcUserInfo::getClaims)
                .map(claims -> (UserDto) claims.get("user"));
    }
}