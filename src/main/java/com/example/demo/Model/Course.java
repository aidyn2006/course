package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    private String id;

    @TextIndexed
    private String title;

    @TextIndexed
    private String description;

    private String photoPath;

    @DBRef
    private List<User> users = new ArrayList<>();

    @DBRef
    private List<InnerCourse> innerCourses = new ArrayList<>();

}