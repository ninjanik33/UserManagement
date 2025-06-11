package com.demo.usermanagement.app.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.demo.usermanagement.app.dto.request.UserCreateRequest;
import com.demo.usermanagement.app.dto.request.UserUpdateRequest;
import com.demo.usermanagement.app.dto.response.ErrorResponse;
import com.demo.usermanagement.app.entity.UserModel;
import com.demo.usermanagement.app.repository.UserRepository;

public class UserService {
    UserRepository userRepository;
    
    public ResponseEntity<Object> getAllUsers() {
        var userList =  userRepository.findAll();

        if (userList.isEmpty()) {
            var errorRes = ErrorResponse.builder()
                .message("No users found")
                .statusCode(404)
                .build();

            return ResponseEntity.status(404)
                    .body(errorRes);
        }

        return ResponseEntity.ok()
                .body(userList);
    }
    
    public ResponseEntity<Object> getUser(String id) {
        var userOptional =  userRepository.findById(id);

        if (userOptional.isEmpty()) {
            var errorRes = ErrorResponse.builder()
                .message("No users found")
                .statusCode(404)
                .build();

            return ResponseEntity.status(404)
                    .body(errorRes);
        }

        return ResponseEntity.ok()
                .body(userOptional.get());
    }
    
    public ResponseEntity<Object> deleteUser(String id) {
        var userOptional =  userRepository.findById(id);

        if (userOptional.isEmpty()) {
            var errorRes = ErrorResponse.builder()
                .message("No users found")
                .statusCode(404)
                .build();

            return ResponseEntity.status(404)
                    .body(errorRes);
        } else {
            userRepository.deleteById(id);
        }

        return ResponseEntity.status(200).body("successfully deleted user with id: " + id);
    }

    public ResponseEntity<Object> updateUser(UserUpdateRequest request) {
        var userOptional =  userRepository.findById(request.getId());

        if (userOptional.isEmpty()) {
            var errorRes = ErrorResponse.builder()
                .message("No users found")
                .statusCode(404)
                .build();

            return ResponseEntity.status(404)
                    .body(errorRes);
        } else {
            userRepository.save(new UserModel(request));
        }
        return ResponseEntity.ok().body("User updated successfully");
    }

    public ResponseEntity<Object> createUser(UserCreateRequest request) {
        var userModel = new UserModel(request);
        userRepository.save(userModel);

        return ResponseEntity.ok().body("User created successfully");
    }
}
