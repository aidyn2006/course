package com.example.demo.Repository;

import com.example.demo.Model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    @Query("{ 'title' : { $regex: ?0, $options: 'i' } }")
    List<Course> findByTitle(String name);

    @Query("{ 'description' : { $regex: ?0, $options: 'i' } }")
    List<Course> findByDef(String name);
}

