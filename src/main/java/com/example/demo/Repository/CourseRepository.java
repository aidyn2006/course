package com.example.demo.Repository;

import com.example.demo.Model.Course;
import org.springframework.data.mongodb.repository.Aggregation;
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

    @Aggregation(pipeline = {
            "{ $match: { ?0: { $regex: ?1, $options: 'i' } } }",
            "{ $group: { _id: '$?0', total: { $sum: 1 }, photoPath: { $first: '$photoPath' } } }",
            "{ $sort: { ?0: 1 } }"
    })
    List<Course> aggregateCourseCount(String field);


}

