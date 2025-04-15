package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.UserDTO;
import com.dev.yank.ecommerce.exception.UserNotFoundException;
import com.dev.yank.ecommerce.mapper.UserMapper;
import com.dev.yank.ecommerce.model.User;
import com.dev.yank.ecommerce.model.enums.Role;
import com.dev.yank.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Injection
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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

    public void registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new RuntimeException("Email is already registered.");
        }

        User user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
    }
}
