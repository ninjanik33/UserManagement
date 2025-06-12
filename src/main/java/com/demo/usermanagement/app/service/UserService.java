package com.demo.usermanagement.app.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.usermanagement.app.dto.request.UserCreateRequest;
import com.demo.usermanagement.app.dto.request.UserUpdateRequest;
import com.demo.usermanagement.app.dto.response.ErrorResponse;
import com.demo.usermanagement.app.dto.response.UserResponse;
import com.demo.usermanagement.app.entity.UserModel;
import com.demo.usermanagement.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    
    public ResponseEntity<Object> getAllUsers() {
        try {
            var userList =  userRepository.findAll();

            if (userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No users found");
            }

            return ResponseEntity.ok()
                    .body(UserResponse.fromUserModelList(userList));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
    
    public ResponseEntity<Object> getUserById(String id) {
        try {
            var userOptional =  userRepository.findById(id);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No users found");
            }

            return ResponseEntity.ok()
                    .body(UserResponse.fromUserModel(userOptional.get()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
    
    public ResponseEntity<Object> deleteUser(String id) {
        try {
            var userOptional =  userRepository.findById(id);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No users found");
            } else {
                userRepository.deleteById(id);
            }

            return ResponseEntity.status(HttpStatus.OK).body("successfully deleted user with id: " + id);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> updateUser(UserUpdateRequest request) {
        try {
            var userOptional =  userRepository.findById(request.getId());

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No users found");
            } else {
                userRepository.save(new UserModel(request));
            }
            return ResponseEntity.ok().body("User updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> createUser(UserCreateRequest request) {
        try {
            var userModel = new UserModel(request);
            if (userModel.getId() == null || userModel.getId().isBlank()) {
                userModel.setId(UUID.randomUUID().toString());
            } else {
                var userOptional =  userRepository.findById(request.getId());

                if (userOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("User data already exists with id: " + request.getId());
                }
            }
            userRepository.save(userModel);

            return ResponseEntity.ok().body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
