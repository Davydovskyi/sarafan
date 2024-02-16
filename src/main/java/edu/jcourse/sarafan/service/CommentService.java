package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.CommentDto;
import edu.jcourse.sarafan.mapper.CommentMapper;
import edu.jcourse.sarafan.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto create(CommentDto commentDto) {
        return Optional.of(commentDto)
                .map(commentMapper::toEntity)
                .map(commentRepository::save)
                .map(commentMapper::toDto)
                .orElseThrow();
    }
}