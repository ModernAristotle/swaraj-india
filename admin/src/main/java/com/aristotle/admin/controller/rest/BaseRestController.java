package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.UserBean;
import com.aristotle.core.persistance.User;

public abstract class BaseRestController {

    protected UserBean convertUserForLoginResult(User user) {
        UserBean userBean = new UserBean();
        userBean.setId(user.getId());
        userBean.setName(user.getName());
        userBean.setGender(user.getGender());
        userBean.setSuperAdmin(user.isSuperAdmin());
        userBean.setProfilePic(user.getProfilePic());
        return userBean;
    }
}
