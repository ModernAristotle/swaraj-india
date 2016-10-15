package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.*;
import com.aristotle.admin.service.LoginService;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Slf4j
public class LoginRestController extends BaseRestController {

    @Autowired
    private LoginService loginService;

    private UserPermissionBean emptyUserPermissionBean = new UserPermissionBean();
    private Location emptyLocationBean = new Location();

    @RequestMapping(value = "/service/us/login", method = POST)
    public LoginResultBean userLogin(HttpServletRequest httpServletRequest, @RequestBody LoginBean loginBean) throws AppException {
        log.info("Login : {}", loginBean);
        User user = loginService.login(httpServletRequest, loginBean.getLoginName(), loginBean.getPassword());
        LoginResultBean loginResultBean = new LoginResultBean();
        loginResultBean.setSuccess(true);
        UserBean userBean = convertUserForLoginResult(user);
        loginResultBean.setLoggedInUser(userBean);
        return loginResultBean;
    }

    @RequestMapping(value = "/service/s/permission", method = GET)
    public UserPermissionBean userPermissions(HttpServletRequest httpServletRequest) throws AppException {
        UserSessionObject userSessionObject = loginService.getUserSessionObject(httpServletRequest);
        if (userSessionObject == null) {
            return emptyUserPermissionBean;
        }
        return userSessionObject.getUserPermissionBean();
    }

    @RequestMapping(value = "/service/s/selectedlocation", method = GET)
    public Location userSelectedLocation(HttpServletRequest httpServletRequest) throws AppException {
        UserSessionObject userSessionObject = loginService.getUserSessionObject(httpServletRequest);
        if (userSessionObject == null) {
            return emptyLocationBean;
        }
        return userSessionObject.getSelectedAdminLocation();
    }

    @RequestMapping(value = "/service/s/adminlocations", method = GET)
    public List<Location> userAdminLocations(HttpServletRequest httpServletRequest) throws AppException {
        UserSessionObject userSessionObject = loginService.getUserSessionObject(httpServletRequest);
        if (userSessionObject == null) {
            return Collections.emptyList();
        }
        return userSessionObject.getUserAdminLocations();
    }

}
