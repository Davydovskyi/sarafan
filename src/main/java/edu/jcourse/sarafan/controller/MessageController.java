package edu.jcourse.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    @JsonView(View.FullMessage.class)
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
    @JsonView(View.FullMessage.class)
    public MessageDto create(@RequestBody MessageDto message) {
        return messageService.create(message);
    }

    @PutMapping("/{id}")
    public MessageDto update(@PathVariable Long id,
                             @RequestBody MessageDto message) {
        return messageService.update(id, message)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return messageService.deleteById(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}