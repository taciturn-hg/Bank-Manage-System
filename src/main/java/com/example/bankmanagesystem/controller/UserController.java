package com.example.bankmanagesystem.controller;

import com.example.bankmanagesystem.dto.user.UserLoginDTO;
import com.example.bankmanagesystem.dto.user.UserRegisterDTO;
import com.example.bankmanagesystem.dto.user.UserResponseDTO;
import com.example.bankmanagesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.bankmanagesystem.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // 允许跨域
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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

    // 新增：获取当前登录用户信息
    @GetMapping("/me")
    public UserResponseDTO getCurrentUser(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            Long userId = jwtUtil.getUserId(token);
            return userService.getUserById(userId);
        }
        throw new RuntimeException("未授权");
    }
}
