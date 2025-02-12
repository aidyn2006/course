package com.example.demo.Service;

import com.example.demo.Model.Course;
import com.example.demo.Model.Favo;
import com.example.demo.Model.User;
import com.example.demo.Repository.CourseRepository;
import com.example.demo.Repository.FavoRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    private UserRepository userRepository;

    private FavoRepository favoRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository, FavoRepository favoRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.favoRepository = favoRepository;
    }

    public Optional<Course> findById(String id) {
        return courseRepository.findById(id);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional
    public void addCourseToFavorites(String userId, String courseId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            User user = userOptional.get();
            Course course = courseOptional.get();


            Favo favo = new Favo();
            favo.setCourse(course);
            favo.setUsers(Collections.singletonList(user)); // Ошибка! Здесь user — это объект, а нужно List<User>
            favoRepository.save(favo);


            user.getFavorites().add(favo);
            userRepository.save(user);
        } else {
            throw new RuntimeException("НЕ НАЙДЕНО");
        }
    }

    public List<Favo> findFavoriteCoursesByUserId(String userId) {
        return userRepository.findById(userId)
                .map(User::getFavorites)
                .orElse(new ArrayList<>());
    }

    @Transactional
    public void removeFavoriteCourse(String userId, String courseId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getFavorites().removeIf(favo -> favo.getCourse().getId().equals(courseId));
            userRepository.save(user);
        }
    }


}
