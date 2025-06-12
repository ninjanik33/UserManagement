package com.demo.usermanagement.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "id cannot be blank")
    String id;
    
    @NotBlank(message = "name cannot be blank")
    String name;

    @NotBlank(message = "username cannot be blank")
    String username;

    @NotBlank(message = "email cannot be blank")
    String email;
    
    @NotNull(message = "address cannot be null")
    Address address;

    @NotBlank(message = "phone cannot be blank")
    String phone;

    @NotBlank(message = "website cannot be blank")
    String website;

    @NotNull(message = "company cannot be null")
    Company company;

    @Data
    @Builder
    public static class Address {
        @NotBlank(message = "street cannot be blank")
        String street;

        @NotBlank(message = "suite cannot be blank")
        String suite;

        @NotBlank(message = "city cannot be blank")
        String city;

        @NotBlank(message = "zipcode cannot be blank")
        String zipcode;

        @NotNull(message = "geo cannot be null")
        Geo geo;
    
        @Data
        @Builder
        public static class Geo {
            @NotBlank(message = "lat cannot be blank")
            String lat;

            @NotBlank(message = "lng cannot be blank")
            String lng;
        }
    }

    @Data
    @Builder
    public static class Company {
        @NotBlank(message = "name cannot be blank")
        String name;

        @NotBlank(message = "catchPhrase cannot be blank")
        String catchPhrase;

        @NotBlank(message = "bs cannot be blank")
        String bs;
    }
}
