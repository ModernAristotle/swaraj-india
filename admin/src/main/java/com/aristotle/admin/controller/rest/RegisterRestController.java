package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.UserRegisterBean;
import com.aristotle.admin.controller.beans.UserRegisterResultBean;
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
public class RegisterRestController extends BaseRestController {

    @Autowired
    private UserRegisterService userRegisterService;

    @RequestMapping(value = {"/service/us/register/web"}, method = RequestMethod.POST)
    public UserRegisterResultBean registerWebUser(HttpServletRequest httpServletRequest, @RequestBody UserRegisterBean userRegisterBean) throws AppException {
        return registerUser(httpServletRequest, userRegisterBean);
    }

    @RequestMapping(value = {"/service/us/register/mobile"}, method = RequestMethod.POST)
    public UserRegisterResultBean registerMobileUser(HttpServletRequest httpServletRequest, @RequestBody UserRegisterBean userRegisterBean) throws AppException {
        return registerUser(httpServletRequest, userRegisterBean);
    }

    private UserRegisterResultBean registerUser(HttpServletRequest httpServletRequest, UserRegisterBean userRegisterBean) throws AppException {
        log.info("userRegisterBean : {}", userRegisterBean);
        UserRegisterResultBean userRegisterResultBean = userRegisterService.register(httpServletRequest, userRegisterBean);
        return userRegisterResultBean;
    }

}
