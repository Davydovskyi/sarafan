package edu.jcourse.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.jcourse.sarafan.dto.CommentDto;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.FullComment.class)
    public CommentDto create(@RequestBody CommentDto commentDto) {
        return commentService.create(commentDto);
    }
}