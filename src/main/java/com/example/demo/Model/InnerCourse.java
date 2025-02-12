package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inner_course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InnerCourse {
    @Id
    private String id;
    private String title;
    private String content;

    @DBRef
    private Course course;
}
