package com.demo.usermanagement.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.usermanagement.app.dto.request.UserCreateRequest;
import com.demo.usermanagement.app.dto.request.UserUpdateRequest;
import com.demo.usermanagement.app.entity.UserModel;
import com.demo.usermanagement.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Validation;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(userController, "validator", Validation.buildDefaultValidatorFactory().getValidator());
    }

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
                .name("mockUser")
                .username("mockUsername")
                .email("mockEmail")
                .address(UserCreateRequest.Address.builder()
                        .street("mockStreet")
                        .suite("mockSuite")
                        .city("mockCity")
                        .zipcode("mockZipcode")
                        .geo(UserCreateRequest.Address.Geo.builder()
                                .lat("mockLat")
                                .lng("mockLng")
                                .build())
                        .build())
                .phone("1234567890")
                .website("www.mockwebsite.com")
                .company(UserCreateRequest.Company.builder()
                        .name("mockCompanyName")
                        .catchPhrase("mockCatchPhrase")
                        .bs("mockBS")
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
    void getAllUsersTest() throws Exception {
        // when(userService.getAllUsers()).thenReturn(ResponseEntity.ok(List.of(getUserCreateRequestMock())));
        var responseEntity = userController.getAllUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    void getUsersTest() throws Exception {
        // when(userService.getUserById(any())).thenReturn(ResponseEntity.ok(List.of(getUserCreateRequestMock())));
        var responseEntity = userController.getUser("1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    void deleteUsersTest() throws Exception {
        // when(userService.getUserById(any())).thenReturn(ResponseEntity.ok(List.of(getUserCreateRequestMock())));
        var responseEntity = userController.getUser("1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
