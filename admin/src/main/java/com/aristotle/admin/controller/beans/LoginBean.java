package com.aristotle.admin.controller.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
@NoArgsConstructor
@AllArgsConstructor
public class LoginBean {

    private String loginName;
    private String password;
}
