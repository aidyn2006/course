package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "favo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favo {
    @Id
    private String id;

    @DBRef
    private Course course;

    @DBRef
    private List<User> users = new ArrayList<>();
}
