package com.example.bankmanagesystem;

import com.example.bankmanagesystem.entity.User;
import com.example.bankmanagesystem.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankManageSystemApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("测试User和UserRespository接口是否能正常运行")
    void contextLoads() {
        User user = User.builder()
                .username("123")
                .password("123")
                .name("小王")
                .id_card("123123123")
                .phone("123123123")
                .build();

        userRepository.save(user);

        assertTrue(userRepository.findByUsername("123").isPresent());
        assertTrue(userRepository.existsByUsername("123"));
    }


}
