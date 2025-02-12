package com.example.demo.Model;

import com.example.demo.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private Role role;

    @DBRef
    private List<Course> courses = new ArrayList<>();

    @DBRef
    private List<Favo> favorites = new ArrayList<>();
}