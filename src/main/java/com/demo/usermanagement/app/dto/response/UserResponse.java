package com.demo.usermanagement.app.dto.response;

import java.util.List;

import com.demo.usermanagement.app.entity.UserModel;

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

    @Data
    @Builder
    public static class Address {
        String street;
        String suite;
        String city;
        String zipcode;
        Geo geo;
    
        @Data
        @Builder
        public static class Geo {
            String lat;
            String lng;
        }
    }

    @Data
    @Builder
    public static class Company {
        String name;
        String catchPhrase;
        String bs;
    }

    public static List<UserResponse> fromUserModelList(List<UserModel> userModels) {
        return userModels.stream()
                .map(UserResponse::fromUserModel)
                .toList();
    }

    public static UserResponse fromUserModel(UserModel userModel) {
        return UserResponse.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .address(Address.builder()
                        .street(userModel.getStreet())
                        .suite(userModel.getSuite())
                        .city(userModel.getCity())
                        .zipcode(userModel.getZipcode())
                        .geo(Address.Geo.builder()
                                .lat(userModel.getLat())
                                .lng(userModel.getLng())
                                .build())
                        .build())
                .phone(userModel.getPhone())
                .website(userModel.getWebsite())
                .company(Company.builder()
                        .name(userModel.getCompanyName())
                        .catchPhrase(userModel.getCatchPhrase())
                        .bs(userModel.getBs())
                        .build())
                .build();
    }
}
