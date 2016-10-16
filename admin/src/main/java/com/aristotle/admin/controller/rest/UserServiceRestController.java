package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.user.UpdateUserDetails;
import com.aristotle.admin.controller.beans.user.UpdateUserPassword;
import com.aristotle.admin.controller.beans.user.UpdateUserPasswordResponse;
import com.aristotle.admin.service.UserRegisterService;
import com.aristotle.core.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserServiceRestController extends BaseRestController {

    @Autowired
    private UserRegisterService userRegisterService;

    UpdateUserPasswordResponse updateUserPasswordResponse = new UpdateUserPasswordResponse("Password updated succesfully");


    @RequestMapping(value = {"/service/s/me/basic"}, method = RequestMethod.POST)
    public UpdateUserDetails updateUserDetails(HttpServletRequest httpServletRequest, @RequestBody UpdateUserDetails updateUserDetails) throws AppException {
        return userRegisterService.updateUserDetails(httpServletRequest, updateUserDetails);
    }

    @RequestMapping(value = {"/service/s/me/password"}, method = RequestMethod.POST)
    public UpdateUserPasswordResponse UpdateUserPassword(HttpServletRequest httpServletRequest, @RequestBody UpdateUserPassword updateUserPassword) throws AppException {
        userRegisterService.updateUserPassword(httpServletRequest, updateUserPassword);
        return updateUserPasswordResponse;
    }


}
