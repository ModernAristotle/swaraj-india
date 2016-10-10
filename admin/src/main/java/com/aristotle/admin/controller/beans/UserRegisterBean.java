package com.aristotle.admin.controller.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserRegisterBean {

    private String name;
    private String password;
    private String gender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private boolean nri;
    private String emailId;
    private String mobileNumber;
    private String countryCode;
    private String nriMobileNumber;
    private String nriCountryCode;
    private String gRecaptchaResponse;
}
