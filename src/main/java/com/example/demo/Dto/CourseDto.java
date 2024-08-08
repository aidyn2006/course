package com.example.demo.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class  CourseDto {
    private String title;
    private String description;
    private MultipartFile photo;
    private List<String> innerCoursesTitles=new ArrayList<>();;
    private List<String> innerCoursesContents=new ArrayList<>();;
}
