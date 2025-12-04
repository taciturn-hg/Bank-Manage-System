package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.user.UserLoginDTO;
import com.example.bankmanagesystem.dto.user.UserRegisterDTO;
import com.example.bankmanagesystem.dto.user.UserResponseDTO;
import com.example.bankmanagesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO dto) {
        return userService.login(dto);
    }

    /**
     * 获取用户详情（JWT 登录后测试）
     */
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
