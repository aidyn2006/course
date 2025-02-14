package com.example.demo.Dto;

import com.example.demo.util.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDtoReg {

    @NotEmpty(message = "Username should not be empty!")
    @Size(min = 2, max = 10, message = "Username should be between 2 and 10 characters")
    private String username;

    @NotEmpty(message = "Phone number should not be empty!")
    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 characters")
    private String phone;

    @Email(message = "Email should be valid!")
    @NotEmpty(message = "Email should not be empty!")
    private String email;

    @NotEmpty(message = "Password should not be empty!")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;

    private Role role;
}
