package com.aristotle.admin.controller.beans;


import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
public class LoginBean {

    private String loginName;
    private String password;
}
