package com.aristotle.admin.controller.beans;

import lombok.Data;

@Data
public class UserRegisterResultBean extends BaseBean {
    private boolean success;
    private UserRegisterBean registeredUser;
}
