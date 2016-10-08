package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.LoginBean;
import com.aristotle.admin.controller.beans.LoginResultBean;
import com.aristotle.admin.controller.beans.UserBean;
import com.aristotle.admin.service.ControllerService;
import com.aristotle.admin.service.LoginService;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class LoginRestController extends BaseRestController {

    @Autowired
    private ControllerService controllerService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/service/login", method = RequestMethod.POST)
    public LoginResultBean adminLogin(HttpServletRequest httpServletRequest, @RequestBody LoginBean loginBean) throws AppException {
        log.info("Login : {}", loginBean);
        User user = loginService.login(httpServletRequest, loginBean.getLoginName(), loginBean.getPassword());
        LoginResultBean loginResultBean = new LoginResultBean();
        loginResultBean.setSuccess(true);
        UserBean userBean = convertUserForLoginResult(user);
        loginResultBean.setLoggedInUser(userBean);
        return loginResultBean;
    }
}
