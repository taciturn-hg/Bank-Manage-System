package com.example.bankmanagesystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "id_card", nullable = false, length = 18)
    private String idCard; // 驼峰映射到数据库 id_card

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();
    }
}
