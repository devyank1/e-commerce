package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.UserDTO;
import com.dev.yank.ecommerce.exception.UserNotFoundException;
import com.dev.yank.ecommerce.mapper.UserMapper;
import com.dev.yank.ecommerce.model.User;
import com.dev.yank.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Methods
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO).toList();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO newUser) {
        User user = userMapper.toEntity(newUser);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }


}
