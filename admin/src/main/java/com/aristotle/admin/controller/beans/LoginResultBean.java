package com.aristotle.admin.controller.beans;

import lombok.Data;

@Data
public class LoginResultBean {
    private boolean success;
    private UserBean loggedInUser;
}
