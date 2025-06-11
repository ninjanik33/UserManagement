package com.demo.usermanagement.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String id;
    String name;
    String username;
    String email;
    Address address;
    String phone;
    String website;
    Company company;

    public class Address {
        String street;
        String suite;
        String city;
        String zipcode;
        Geo geo;
    
        public static class Geo {
            String lat;
            String lng;
        }
    }

    public class Company {
        String name;
        String catchPhrase;
        String bs;
    }
}
