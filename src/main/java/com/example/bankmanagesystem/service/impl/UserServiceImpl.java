package com.example.bankmanagesystem.service.impl;

import com.example.bankmanagesystem.dto.user.UserLoginDTO;
import com.example.bankmanagesystem.dto.user.UserRegisterDTO;
import com.example.bankmanagesystem.dto.user.UserResponseDTO;
import com.example.bankmanagesystem.entity.User;
import com.example.bankmanagesystem.exception.BusinessException;
import com.example.bankmanagesystem.exception.ErrorCode;
import com.example.bankmanagesystem.repository.UserRepository;
import com.example.bankmanagesystem.service.UserService;
import com.example.bankmanagesystem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDTO register(UserRegisterDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER"); // 默认普通用户

        userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole());
    }

    @Override
    public String login(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        // 生成 JWT
        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
