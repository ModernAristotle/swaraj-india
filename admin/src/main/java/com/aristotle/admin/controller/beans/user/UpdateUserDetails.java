package com.aristotle.admin.controller.beans.user;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserDetails {

    private String name;

    private String fatherName;

    private String motherName;

    private String address;

    private String gender;

    private Date dateOfBirth;

    private String profile;

    private String profilePic;

    private String passportNumber;

    private String identityNumber;

    private String identityType;

    private String voterId;

}
