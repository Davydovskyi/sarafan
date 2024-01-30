package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.mapper.UserMapper;
import edu.jcourse.sarafan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<UserDto> findById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow();
    }
}