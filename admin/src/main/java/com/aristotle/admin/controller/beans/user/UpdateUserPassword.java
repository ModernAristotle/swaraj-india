package com.aristotle.admin.controller.beans.user;

import lombok.Data;

@Data
public class UpdateUserPassword {

    private String currentPassword;
    private String newPassword;
    private String newPassword2;
}
