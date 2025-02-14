# Project Documentation

## Overview

This project provides a **Course Management System** where two types of users, **Teachers** and **Students**, interact with courses. The **Teacher** role allows users to manage (add, update, delete) courses and their content, while the **Student** role allows users to enroll in courses, view their details, and add them to their favorites.

---

## Features

### 1. **Login & Registration**:
- **Login**: Users can log in with their credentials.
- **Registration**: New users can register by providing necessary details.

### 2. **Course Management** (For Teachers):
- **Create Course**: Teachers can create new courses and add their contents.
- **Update Course**: Teachers can edit existing courses, including the content and photo.
- **Delete Course**: Teachers can delete courses that are no longer needed.
- **View Course Details**: Teachers and students can view detailed information about courses.

### 3. **Course Interaction** (For Students):
- **View Courses**: Students can browse through the available courses.
- **Enroll in Courses**: Students can enroll in courses and start learning.
- **Add to Favorites**: Students can add courses to their favorites for easy access.
- **Remove from Favorites**: Students can remove courses from their favorites.
- **Group Courses**: Students and Teachers can filter and view courses grouped by title or description.

### 4. **Course Content**:
- **Inner Courses**: Courses can have sub-courses (Inner Courses), which can also be viewed and managed by teachers.

---

## User Roles

### Teacher:
- **Course Management**: Teachers can add, update, and delete courses.
- **Inner Courses**: Teachers can manage individual lessons inside each course.
- **Favorite Courses**: Teachers can view courses in their favorites.

### Student:
- **Enroll in Courses**: Students can choose to enroll in courses and start learning.
- **View Courses**: Students can explore a list of available courses.
- **Add to Favorites**: Students can mark courses as favorites for later access.
- **Manage Favorites**: Students can remove courses from their favorites list.

---

## Endpoints

### **/auth** (Authentication)
- **GET /auth/login**: Displays the login page.
- **POST /auth/login**: Logs the user in.
- **GET /auth/register**: Displays the registration page.
- **POST /auth/register**: Registers a new user.

### **/courses** (Course Management)
- **GET /courses/new**: Displays the form to create a new course (for teachers).
- **POST /courses**: Creates a new course (for teachers).
- **GET /courses/{id}**: Displays the details of a specific course.
- **GET /courses**: Displays the list of all courses.
- **GET /courses/inner-courses/{courseId}**: Displays the inner courses of a specific course.
- **POST /courses/add-to-favorites**: Adds a course to the student's favorites.
- **GET /courses/show-favo**: Displays the list of favorite courses for the logged-in user.
- **POST /courses/remove-favorite/{courseId}**: Removes a course from the student's favorites.
- **GET /courses/edit/{id}**: Displays the form to edit an existing course.
- **PUT /courses/update/{id}**: Updates the details of an existing course (for teachers).
- **POST /courses/delete/{id}**: Deletes a course (for teachers).
- **GET /courses/findByTitle**: Searches for courses by title.
- **GET /courses/findByDescription**: Searches for courses by description.
- **GET /courses/groupBy**: Groups courses by title or description.

---

## Technologies Used
- **Spring Boot**: Backend framework for building REST APIs and web pages.
- **Spring Security**: Secures the application with user authentication and role-based access.
- **MongoDB**: Database used to store course and user data.
- **Cloudinary**: Used for handling image uploads (course photos).
- **Thymeleaf**: Template engine for rendering HTML views.

---

## How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repository-url.git
   ```

2. **Setup the database**:
    - Install and configure MongoDB.

3. **Run the application**:
    - Use the following command to run the Spring Boot application:
      ```bash
      ./mvnw spring-boot:run
      ```

4. **Access the application**:
    - Navigate to `http://localhost:8080` in your browser to access the app.

---

## Contribution Guidelines

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push to your forked repository.
4. Create a pull request with a description of your changes.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

