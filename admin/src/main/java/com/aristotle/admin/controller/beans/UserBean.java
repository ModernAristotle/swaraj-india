package com.aristotle.admin.controller.beans;

import com.aristotle.core.enums.CreationType;
import lombok.Data;

import java.util.Date;

@Data
public class UserBean extends BaseBean {

    private String membershipNumber;
    private String name;
    private String fatherName;
    private String motherName;
    private String referenceMobileNumber;
    private String address;
    private String gender;
    private CreationType creationType;
    private Date dateOfBirth;
    private boolean nri;
    private String profile;
    private String profilePic;
    private boolean superAdmin;
    private boolean member;
    private boolean donor;
    private boolean volunteer;
    private String membershipStatus;
    private String identityNumber;
    private String identityType;
    private String voterId;
}
