package com.example.demo.Controller;

import com.example.demo.Model.Course;
import com.example.demo.Dto.CourseDto;
import com.example.demo.Model.Favo;
import com.example.demo.Model.InnerCourse;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.CloudinaryService;
import com.example.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;
    private AdminService adminService;
    private UserRepository userRepository;
    private CloudinaryService cloudinaryService;


    @Autowired
    public CourseController(CourseService courseService, AdminService adminService, UserRepository userRepository, CloudinaryService cloudinaryService) {
        this.courseService = courseService;
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
    }



    @GetMapping("/new")
    public String showCourseForm(Model model) {
        model.addAttribute("courseDto", new CourseDto());
        return "course_form";
    }

    @PostMapping
    public String createCourse(@ModelAttribute CourseDto courseDto,
                               @RequestParam("photo") MultipartFile photo) {
        List<InnerCourse> innerCourses = new ArrayList<>();
        for (int i = 0; i < courseDto.getInnerCoursesTitles().size(); i++) {
            InnerCourse innerCourse = new InnerCourse();
            innerCourse.setTitle(courseDto.getInnerCoursesTitles().get(i));
            innerCourse.setContent(courseDto.getInnerCoursesContents().get(i));
            innerCourses.add(innerCourse);
        }

        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());

        // Загружаем фото в Cloudinary
        String photoUrl = cloudinaryService.uploadFile(photo);
        course.setPhotoPath(photoUrl); // Сохраняем URL в базе данных

        adminService.createCourseWithInnerCourses(course, innerCourses, photo);
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String getCourse(@PathVariable String id, Model model) {
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            return "course_detail";
        } else {
            return "error";
        }
    }

    @GetMapping
    public String listCourses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userId = userRepository.findUserIdByUsername(username);

        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("КУРС ЖОК");
        }
        model.addAttribute("courses", courses);
        model.addAttribute("userId", userId.get().getId());
        return "course_list";
    }

    @GetMapping("/inner-courses/{courseId}")
    public String listInnerCourses(@PathVariable String courseId, Model model) {
        Optional<Course> course = courseService.findById(courseId);
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            return "inner_course_list";
        } else {
            return "error";
        }
    }

    @PostMapping("/add-to-favorites")
    public String addToFavorites(@RequestParam("userId") String userId,
                                 @RequestParam("courseId") String courseId) {
        try {
            courseService.addCourseToFavorites(userId, courseId);
            return "redirect:/courses/" + courseId;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @GetMapping("/show-favo")
    public String showFavos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            String userId = userOptional.get().getId();
            List<Favo> favoriteCourses = courseService.findFavoriteCoursesByUserId((userId));

            List<Course> courses = new ArrayList<>();
            for (Favo favo : favoriteCourses) {
                if (favo.getCourse() != null) {  // Проверяем, что курс не null
                    courses.add(favo.getCourse());
                }
            }

            model.addAttribute("courses", courses);
        } else {
            model.addAttribute("courses", new ArrayList<>());
        }

        return "main";
    }


    @PostMapping("/remove-favorite/{courseId}")
    public String removeFavorite(@PathVariable("courseId") Long courseId, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            String userId = userOptional.get().getId();
            courseService.removeFavoriteCourse(userId, String.valueOf(courseId));
            redirectAttributes.addFlashAttribute("message", "Course removed from favorites successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }

        return "redirect:/courses/show-favo";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()) {
            CourseDto courseDto = new CourseDto();
            Course existingCourse = course.get();
            courseDto.setTitle(existingCourse.getTitle());
            courseDto.setDescription(existingCourse.getDescription());

            List<String> titles = new ArrayList<>();
            List<String> contents = new ArrayList<>();
            for (InnerCourse innerCourse : existingCourse.getInnerCourses()) {
                titles.add(innerCourse.getTitle());
                contents.add(innerCourse.getContent());
            }
            courseDto.setInnerCoursesTitles(titles);
            courseDto.setInnerCoursesContents(contents);

            model.addAttribute("courseDto", courseDto);
            model.addAttribute("courseId", id);
            model.addAttribute("existingPhotoPath", existingCourse.getPhotoPath());
            return "course_form";
        } else {
            return "error";
        }
    }

    @PutMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute CourseDto courseDto,
                               @RequestParam(value = "photo", required = false) MultipartFile photo) {
        adminService.updateCourse(String.valueOf(id), courseDto, photo);
        return "redirect:/courses/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable String id) {
        adminService.deleteCourse(id);
        return "redirect:/courses";
    }

}
