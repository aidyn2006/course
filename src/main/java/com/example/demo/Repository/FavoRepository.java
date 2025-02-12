package com.example.demo.Repository;

import com.example.demo.Model.Course;
import com.example.demo.Model.Favo;
import com.example.demo.Model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoRepository extends MongoRepository<Favo,String> {


}
