package com.dev.yank.ecommerce.dto;

import com.dev.yank.ecommerce.model.enums.Role;

public record UserDTO(Long id,
                      String username,
                      String email,
                      String password,
                      Role role) {}
