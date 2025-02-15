package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.UserDTO;
import com.dev.yank.ecommerce.exception.UserNotFoundException;
import com.dev.yank.ecommerce.mapper.UserMapper;
import com.dev.yank.ecommerce.model.User;
import com.dev.yank.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // Injection
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Methods
    public Page<UserDTO> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
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

    @Transactional
    public UserDTO updateUser(Long id, UserDTO updateUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        existingUser.setUsername(updateUser.username());
        existingUser.setEmail(updateUser.email());
        existingUser.setPassword(updateUser.password());
        existingUser.setRole(updateUser.role());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }
}
