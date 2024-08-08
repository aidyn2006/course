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
    private UserRepository userRepository;
    private FileStorageService fileStorageService;
    private CourseRepository courseRepository;
    private InnerCourseRepository innerCourseRepository;
    private FavoRepository favoRepository;

    @Autowired
    public AdminService(UserRepository userRepository, FileStorageService fileStorageService, CourseRepository courseRepository, InnerCourseRepository innerCourseRepository, FavoRepository favoRepository) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.courseRepository = courseRepository;
        this.innerCourseRepository = innerCourseRepository;
        this.favoRepository = favoRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('Teacher')")
    public Course createCourseWithInnerCourses(Course course, List<InnerCourse> innerCourses, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            String photoPath = fileStorageService.storeFile(photo);
            course.setPhotoPath(fileStorageService.getFilePath(photoPath));
        }

        Course savedCourse = courseRepository.save(course);
        System.out.println("Saved course ID: " + savedCourse.getId());
        System.out.println("Number of inner courses: " + innerCourses.size());

        for (InnerCourse innerCourse : innerCourses) {
            innerCourse.setCourse(savedCourse);
            InnerCourse savedInnerCourse = innerCourseRepository.save(innerCourse);
            System.out.println("Saved inner course ID: " + savedInnerCourse.getId());
        }

        savedCourse.setInnerCourses(innerCourses);
        return courseRepository.save(savedCourse);
    }



    @Transactional
    @PreAuthorize("hasRole('Teacher')")
    public void updateCourse(Long id, CourseDto courseDto, MultipartFile photo) {
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
                String photoPath = fileStorageService.storeFile(photo);
                course.setPhotoPath(fileStorageService.getFilePath(photoPath));
            }

            courseRepository.save(course);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('Teacher')")
    public void deleteCourse(Long id) {
        favoRepository.deleteById(id);
        innerCourseRepository.deleteById(id);
        courseRepository.deleteById(id);
    }

}
