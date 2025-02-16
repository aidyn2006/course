package com.example.demo;

import com.example.demo.Dto.UserDtoReg;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.util.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDtoReg userDtoReg;

    @BeforeEach
    void setUp() {
        userDtoReg = new UserDtoReg();
        userDtoReg.setUsername("testuser");
        userDtoReg.setEmail("test@example.com");
        userDtoReg.setPhone("123456789");
        userDtoReg.setPassword("plainPassword");
        userDtoReg.setRole(Role.Student);
    }

    @Test
    void save_ShouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        userService.save(userDtoReg);

        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("testuser") &&
                        user.getEmail().equals("test@example.com") &&
                        user.getPhone().equals("123456789") &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getRole() == Role.Student
        ));
    }
}
