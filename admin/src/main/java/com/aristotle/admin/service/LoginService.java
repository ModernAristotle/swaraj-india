package com.aristotle.admin.service;


import com.aristotle.admin.controller.beans.UserPermissionBean;
import com.aristotle.admin.controller.beans.UserSessionObject;
import com.aristotle.core.enums.AppPermission;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.User;
import com.aristotle.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.aristotle.core.enums.AppPermission.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSessionUtil httpSessionUtil;

    public User login(HttpServletRequest httpServletRequest, String userName, String password) throws AppException {
        User user = userService.login(userName, password);
        refreshUserSession(httpServletRequest, user.getId());
        return user;
    }

    public void refreshUserSession(HttpServletRequest httpServletRequest, Long userId) throws AppException {
        UserSessionObject userSessionObject = new UserSessionObject();

        User user = userService.findUserById(userId);
        userSessionObject.setUser(user);


        //now add user admin locations and
        UserPermissionBean userPermissionBean = new UserPermissionBean();
        List<Location> adminLocations = Collections.emptyList();
        Location selectedLocation = null;
        if (user.isSuperAdmin()) {
            userPermissionBean.setSuperAdmin(true);
        } else {
            adminLocations = userService.getUserAdminLocations(user.getId());
        }
        userSessionObject.setUserPermissionBean(userPermissionBean);
        userSessionObject.setUserAdminLocations(adminLocations);

        if (adminLocations.size() == 1) {
            selectLocation(user, userSessionObject, adminLocations.get(0));
        }
        httpSessionUtil.setLoggedInUserSessionObject(httpServletRequest, userSessionObject);
    }

    public UserSessionObject getUserSessionObject(HttpServletRequest httpServletRequest) {
        return httpSessionUtil.getLoggedInUserSessionObject(httpServletRequest);
    }

    private void selectLocation(User user, UserSessionObject userSessionObject, Location location) throws AppException {
        userSessionObject.setSelectedAdminLocation(location);
        UserPermissionBean userPermissionBean = userSessionObject.getUserPermissionBean();
        Set<AppPermission> appPermissions = userService.getLocationPermissionsOfUser(user.getId(), location.getId());
        for (AppPermission oneAppPermission : appPermissions) {
            userPermissionBean.setNews(userPermissionBean.isNews() || oneAppPermission == CREATE_NEWS || oneAppPermission == UPDATE_NEWS || oneAppPermission == DELETE_NEWS || oneAppPermission == APPROVE_NEWS);
            userPermissionBean.setBlogs(userPermissionBean.isBlogs() || oneAppPermission == CREATE_BLOG || oneAppPermission == UPDATE_BLOG || oneAppPermission == DELETE_BLOG || oneAppPermission == APPROVE_BLOG);
            userPermissionBean.setPoll(userPermissionBean.isPoll() || oneAppPermission == CREATE_POLL || oneAppPermission == UPDATE_POLL || oneAppPermission == DELETE_POLL || oneAppPermission == APPROVE_POLL);
            userPermissionBean.setEvents(userPermissionBean.isEvents() || oneAppPermission == CREATE_EVENT || oneAppPermission == UPDATE_EVENT || oneAppPermission == DELETE_EVENT || oneAppPermission == APPROVE_EVENT);
            userPermissionBean.setFacebook(userPermissionBean.isFacebook() || oneAppPermission == ADMIN_CAMPAIGN_FB);
            userPermissionBean.setTwitter(userPermissionBean.isTwitter() || oneAppPermission == ADMIN_CAMPAIGN_TWITTER);
            userPermissionBean.setSms(userPermissionBean.isSms() || oneAppPermission == ADMIN_SMS);
            userPermissionBean.setEmail(userPermissionBean.isEmail() || oneAppPermission == ADMIN_EMAIL);
            userPermissionBean.setCall(userPermissionBean.isCall() || oneAppPermission == CALL_CAMPAIGN_ADMIN);
            userPermissionBean.setMobileGroups(userPermissionBean.isMobileGroups() || oneAppPermission == ADMIN_MOBILE_GROUP);
            userPermissionBean.setDonation(userPermissionBean.isDonation() || oneAppPermission == ADMIN_DONATION);
            userPermissionBean.setCandidates(userPermissionBean.isCandidates() || oneAppPermission == ADMIN_CANDIDATE);

        }
    }


}
