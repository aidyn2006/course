package com.example.demo.Repository;

import com.example.demo.Model.Course;
import com.example.demo.Model.InnerCourse;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InnerCourseRepository extends MongoRepository<InnerCourse,String> {
    void deleteById(Long courseId);
}
