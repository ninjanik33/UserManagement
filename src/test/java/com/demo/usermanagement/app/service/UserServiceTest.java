package com.demo.usermanagement.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.LruCache.CreateAction;
import org.springframework.http.HttpStatus;

import com.demo.usermanagement.app.dto.request.UserCreateRequest;
import com.demo.usermanagement.app.dto.request.UserUpdateRequest;
import com.demo.usermanagement.app.entity.UserModel;
import com.demo.usermanagement.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    private UserModel getUserModelMock() {
        return UserModel.builder()
        .id("1")
        .name("John Doe")
        .username("johndoe")
        .email("johndoe@example.com")
        .phone("123-456-7890")
        .website("www.johndoe.com")
        .street("123 Main St")
        .suite("Apt 1")
        .city("Metropolis")
        .zipcode("12345")
        .lat("40.7128")
        .lng("-74.0060")
        .companyName("Doe Inc.")
        .catchPhrase("Innovation for the future")
        .bs("business solutions")
        .build();
    }

    private UserCreateRequest getUserCreateRequestMock() {
        return UserCreateRequest.builder()
            .id("1")
            .name("John Doe")
            .username("johndoe")
            .email("johndoe@example.com")
            .phone("123-456-7890")
            .website("www.johndoe.com")
            .address(UserCreateRequest.Address.builder()
                .street("123 Main St")
                .suite("Apt 1")
                .city("Metropolis")
                .zipcode("12345")
                .geo(UserCreateRequest.Address.Geo.builder()
                    .lat("40.7128")
                    .lng("-74.0060")
                    .build())
                .build())
            .company(UserCreateRequest.Company.builder()
                .name("Doe Inc.")
                .catchPhrase("Innovation for the future")
                .bs("business solutions")
                .build())
            .build();
    }

    private UserUpdateRequest getUserUpdateRequestMock() {
        return UserUpdateRequest.builder()
            .id("1")
            .name("John Doe")
            .username("johndoe")
            .email("johndoe@example.com")
            .phone("123-456-7890")
            .website("www.johndoe.com")
            .address(UserUpdateRequest.Address.builder()
                .street("123 Main St")
                .suite("Apt 1")
                .city("Metropolis")
                .zipcode("12345")
                .geo(UserUpdateRequest.Address.Geo.builder()
                    .lat("40.7128")
                    .lng("-74.0060")
                    .build())
                .build())
            .company(UserUpdateRequest.Company.builder()
                .name("Doe Inc.")
                .catchPhrase("Innovation for the future")
                .bs("business solutions")
                .build())
            .build();
    }

    @Test
    void getUserByIdTestSuccess() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.of(getUserModelMock()));
        var result = userService.getUserById(userId);

        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void getUserByIdTestFailedNoContent() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        var result = userService.getUserById(userId);

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCode().value());
    }

    @Test
    void getUserByIdTestFailedInternalError() {
        String userId = "1";
        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));
        var result = userService.getUserById(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode().value());
    }

    @Test
    void getAllUserTestSuccess() {
        when(userRepository.findAll()).thenReturn(List.of(getUserModelMock()));
        var result = userService.getAllUsers();

        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void getAllUserTestFailedNoContent() {
        when(userRepository.findAll()).thenReturn(List.of());
        var result = userService.getAllUsers();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCode().value());
    }
    
    @Test
    void getAllUsersTestFailedInternalError() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        var result = userService.getAllUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode().value());
    }

    @Test
    void deleteUserTestSuccess() {
        String userId = "1";
        doNothing().when(userRepository).deleteById(any());
        when(userRepository.findById(userId)).thenReturn(Optional.of(getUserModelMock()));
        var result = userService.deleteUser(userId);

        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void deleteUserTestFailedNoContent() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        var result = userService.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCode().value());
    }
    
    @Test
    void deleteUserTestFailedInternalError() {
        String userId = "1";
        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));
        var result = userService.deleteUser(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode().value());
    }

    @Test
    void createUserTestSuccess() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        var result = userService.createUser(getUserCreateRequestMock());

        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void createUserTestSuccessCaseGenId() {
        var request = getUserCreateRequestMock();
        request.setId(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(getUserModelMock());
        var result = userService.createUser(request);

        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void createUserTestFailedBadRequest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(getUserModelMock()));
        var result = userService.createUser(getUserCreateRequestMock());

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
    }
    
    @Test
    void createUserTestFailedInternalError() {
        when(userRepository.findById(any())).thenThrow(new RuntimeException("Database error"));
        var result = userService.createUser(getUserCreateRequestMock());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode().value());
    }

    @Test
    void updateUserTestSuccess() {
        when(userRepository.findById(any())).thenReturn(Optional.of(getUserModelMock()));
        var result = userService.updateUser(getUserUpdateRequestMock());

        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void updateUserTestFailedNoContent() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        var result = userService.updateUser(getUserUpdateRequestMock());

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatusCode().value());
    }
    
    @Test
    void updateUserTestFailedInternalError() {
        when(userRepository.findById(any())).thenThrow(new RuntimeException("Database error"));
        var result = userService.updateUser(getUserUpdateRequestMock());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode().value());
    }
}
