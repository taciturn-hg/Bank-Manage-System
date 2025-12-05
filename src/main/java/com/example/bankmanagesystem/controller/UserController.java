package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.user.UserLoginDTO;
import com.example.bankmanagesystem.dto.user.UserRegisterDTO;
import com.example.bankmanagesystem.dto.user.UserResponseDTO;
import com.example.bankmanagesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // 允许跨域
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO dto) {
        return userService.login(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
