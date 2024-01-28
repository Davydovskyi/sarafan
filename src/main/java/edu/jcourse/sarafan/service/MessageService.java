package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.mapper.MessageMapper;
import edu.jcourse.sarafan.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public List<MessageDto> findAll() {
        return messageRepository
                .findAll()
                .stream()
                .map(messageMapper::toDto)
                .toList();
    }

    public Optional<MessageDto> findById(Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDto);
    }

    @Transactional
    public MessageDto create(MessageDto messageDto) {
        return Optional.of(messageDto)
                .map(messageMapper::toEntity)
                .map(messageRepository::save)
                .map(messageMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<MessageDto> update(Long id, MessageDto messageDto) {
//       1. MessageDto build = messageDto.toBuilder().id(id).build();
        // или так
        return messageRepository.findById(id)
                .map(entity -> {
//                    BeanUtils.copyProperties(messageDto, entity, MessageDto.Fields.id); // 2.
                    messageMapper.copy(messageDto, entity);  // 3
                    return entity;
                })
                .map(messageRepository::saveAndFlush)
                .map(messageMapper::toDto);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return messageRepository.findById(id)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}