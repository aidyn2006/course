package com.example.demo.Service;


import com.example.demo.Dto.UserDtoReg;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void save(UserDtoReg userDtoReg) {
        User user = User.builder()
                .username(userDtoReg.getUsername())
                .email(userDtoReg.getEmail())
                .phone(userDtoReg.getPhone())
                .password(passwordEncoder.encode(userDtoReg.getPassword()))
                .role(userDtoReg.getRole())
                .build();
        userRepository.save(user);
    }
}
