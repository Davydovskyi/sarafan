package edu.jcourse.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.dto.PageResponse;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static edu.jcourse.sarafan.dto.MessageDto.Fields.id;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    public static final int MESSAGE_PER_PAGE = 3;
    private final MessageService messageService;

    @GetMapping
    @JsonView(View.FullMessage.class)
    public PageResponse<MessageDto> getAll(
            @PageableDefault(size = MESSAGE_PER_PAGE, sort = id, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MessageDto> response = messageService.findAll(pageable);
        return PageResponse.of(response);
    }

    @GetMapping("/{id}")
    @JsonView(View.FullMessage.class)
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