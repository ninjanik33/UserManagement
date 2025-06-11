package com.demo.usermanagement.app.controller;

import java.util.Set;

import jakarta.validation.Validator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usermanagement.app.dto.request.UserCreateRequest;
import com.demo.usermanagement.app.dto.request.UserUpdateRequest;
import com.demo.usermanagement.app.service.UserService;

import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    final Validator validator;

    UserService userService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@RequestParam("id") String id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@RequestParam("id") String id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateUser(UserUpdateRequest request) {
        validateRequest(request);
        return userService.updateUser(request);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(UserCreateRequest request) {
        validateRequest(request);
        return userService.createUser(request);
    }


    private <T> void validateRequest(T request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
