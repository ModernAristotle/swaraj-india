package com.aristotle.admin.controller.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
public class UserRegisterBean {

    private String name;
    private String password;
    private String password2;
    private String gender;
    //    @JsonFormat(pattern = "dd-MM-yyyy")
//    private Date dateOfBirth;
    private boolean nri;
    private String emailId;
    private String mobileNumber;
    private String countryCode;
    private String nriCountryCode;
    private String gRecaptchaResponse;
}
