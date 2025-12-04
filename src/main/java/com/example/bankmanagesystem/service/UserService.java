package com.example.bankmanagesystem.service;

import com.example.bankmanagesystem.dto.user.UserLoginDTO;
import com.example.bankmanagesystem.dto.user.UserRegisterDTO;
import com.example.bankmanagesystem.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(UserRegisterDTO dto);

    String login(UserLoginDTO dto);

    UserResponseDTO getUserById(Long id);
}
