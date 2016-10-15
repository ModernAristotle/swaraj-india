package com.aristotle.admin.controller.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(exclude = {"password"})
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
