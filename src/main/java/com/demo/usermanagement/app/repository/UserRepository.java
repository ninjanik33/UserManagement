package com.demo.usermanagement.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.usermanagement.app.entity.UserModel;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    String TABLE = "users";
    
    Optional<UserModel> findById(String id);
    List<UserModel> findAll();
    void deleteById(String id);
}
