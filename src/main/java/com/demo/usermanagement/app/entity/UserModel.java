package com.demo.usermanagement.app.entity;

import com.demo.usermanagement.app.dto.request.UserCreateRequest;
import com.demo.usermanagement.app.dto.request.UserUpdateRequest;
import com.demo.usermanagement.app.dto.request.UserCreateRequest.Address;
import com.demo.usermanagement.app.dto.request.UserCreateRequest.Company;
import com.demo.usermanagement.app.repository.UserRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = UserRepository.TABLE)
public class UserModel {
    @Id
    @Column(nullable = false, unique = true)
    private String id;
    
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    private String street;
    private String suite;
    private String city;
    private String zipcode;

    private String lat;
    private String lng;

    private String companyName;
    private String catchPhrase;
    private String bs;

    public UserModel(UserCreateRequest request) {
        this.name = request.getName();
        this.username = request.getUsername();
        this.email = request.getEmail();
        Address address = request.getAddress();
        Company company = request.getCompany();
        this.phone = request.getPhone();
        this.website = request.getWebsite();
        this.street = address.getStreet();
        this.suite = address.getSuite();
        this.city = address.getCity();
        this.zipcode = address.getZipcode();
        this.lat = address.getGeo().getLat();
        this.lng = address.getGeo().getLng();
        this.companyName = company.getName();
        this.catchPhrase = company.getCatchPhrase();
        this.bs = company.getBs();
    }

    public UserModel(UserUpdateRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.username = request.getUsername();
        this.email = request.getEmail();
        Address address = request.getAddress();
        Company company = request.getCompany();
        this.phone = request.getPhone();
        this.website = request.getWebsite();
        this.street = address.getStreet();
        this.suite = address.getSuite();
        this.city = address.getCity();
        this.zipcode = address.getZipcode();
        this.lat = address.getGeo().getLat();
        this.lng = address.getGeo().getLng();
        this.companyName = company.getName();
        this.catchPhrase = company.getCatchPhrase();
        this.bs = company.getBs();
    }
}

