package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.CommentDto;
import edu.jcourse.sarafan.dto.EventType;
import edu.jcourse.sarafan.dto.ObjectType;
import edu.jcourse.sarafan.entity.View;
import edu.jcourse.sarafan.mapper.CommentMapper;
import edu.jcourse.sarafan.repository.CommentRepository;
import edu.jcourse.sarafan.util.WsSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BiConsumer<EventType, CommentDto> wsSender;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, View.FullComment.class);
    }

    @Transactional
    public CommentDto create(CommentDto commentDto) {
        return Optional.of(commentDto)
                .map(commentMapper::toEntity)
                .map(commentRepository::save)
                .map(commentMapper::toDto)
                .map(dto -> {
                    wsSender.accept(EventType.CREATED, dto);
                    return dto;
                })
                .orElseThrow();
    }
}