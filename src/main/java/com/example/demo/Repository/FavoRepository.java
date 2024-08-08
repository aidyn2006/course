package com.example.demo.Repository;

import com.example.demo.Model.Course;
import com.example.demo.Model.Favo;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoRepository extends JpaRepository<Favo,Long> {


}
