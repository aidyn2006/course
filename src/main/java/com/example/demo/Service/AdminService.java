package com.example.demo.Service;



import com.example.demo.Dto.CourseDto;
import com.example.demo.Model.Course;
import com.example.demo.Model.InnerCourse;
import com.example.demo.Repository.CourseRepository;
import com.example.demo.Repository.FavoRepository;
import com.example.demo.Repository.InnerCourseRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService; // Используем CloudinaryService
    private final CourseRepository courseRepository;
    private final InnerCourseRepository innerCourseRepository;
    private final FavoRepository favoRepository;

    @Autowired
    public AdminService(UserRepository userRepository, CloudinaryService cloudinaryService,
                        CourseRepository courseRepository, InnerCourseRepository innerCourseRepository,
                        FavoRepository favoRepository) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.courseRepository = courseRepository;
        this.innerCourseRepository = innerCourseRepository;
        this.favoRepository = favoRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('Teacher')")
    public Course createCourseWithInnerCourses(Course course, List<InnerCourse> innerCourses, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            String photoUrl = cloudinaryService.uploadFile(photo); // Загружаем в Cloudinary
            course.setPhotoPath(photoUrl); // Сохраняем URL
        }

        Course savedCourse = courseRepository.save(course);
        for (InnerCourse innerCourse : innerCourses) {
            innerCourse.setCourse(savedCourse);
            innerCourseRepository.save(innerCourse);
        }

        savedCourse.setInnerCourses(innerCourses);
        return courseRepository.save(savedCourse);
    }

    @Transactional
    @PreAuthorize("hasRole('Teacher')")
    public void updateCourse(String id, CourseDto courseDto, MultipartFile photo) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setTitle(courseDto.getTitle());
            course.setDescription(courseDto.getDescription());

            List<InnerCourse> innerCourses = new ArrayList<>();
            for (int i = 0; i < courseDto.getInnerCoursesTitles().size(); i++) {
                InnerCourse innerCourse = new InnerCourse();
                innerCourse.setTitle(courseDto.getInnerCoursesTitles().get(i));
                innerCourse.setContent(courseDto.getInnerCoursesContents().get(i));
                innerCourses.add(innerCourse);
            }
            course.setInnerCourses(innerCourses);

            if (photo != null && !photo.isEmpty()) {
                String photoUrl = cloudinaryService.uploadFile(photo); // Загружаем в Cloudinary
                course.setPhotoPath(photoUrl);
            }

            courseRepository.save(course);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }

    }
    @Transactional
    @PreAuthorize("hasRole('Teacher')")
    public void deleteCourse(String id) {
        favoRepository.deleteById(id);
        innerCourseRepository.deleteById(id);
        courseRepository.deleteById(id);
    }
}

