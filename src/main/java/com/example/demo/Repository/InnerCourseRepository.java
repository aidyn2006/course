package com.example.demo.Repository;

import com.example.demo.Model.Course;
import com.example.demo.Model.InnerCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface InnerCourseRepository extends JpaRepository<InnerCourse,Long> {
    void deleteById(Long courseId);
}
