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
            userPermissionBean.setNewsEditor(userPermissionBean.isNewsEditor() || oneAppPermission == NEWS_EDITOR);
            userPermissionBean.setNewsReporter(userPermissionBean.isNewsReporter() || oneAppPermission == NEWS_REPORTER);

            userPermissionBean.setBlogEditor(userPermissionBean.isBlogEditor() || oneAppPermission == BLOG_EDITOR);
            userPermissionBean.setBlogReporter(userPermissionBean.isBlogReporter() || oneAppPermission == BLOG_REPORTER);

            userPermissionBean.setPressReleaseEditor(userPermissionBean.isPressReleaseEditor() || oneAppPermission == PRESS_RELEASE_EDITOR);
            userPermissionBean.setPressReleaseReporter(userPermissionBean.isPressReleaseReporter() || oneAppPermission == PRESS_RELEASE_REPORTER);

            userPermissionBean.setPollEditor(userPermissionBean.isPollEditor() || oneAppPermission == POLL_EDITOR);
            userPermissionBean.setPollReporter(userPermissionBean.isPollReporter() || oneAppPermission == POLL_REPORTER);

            userPermissionBean.setEventEditor(userPermissionBean.isEventEditor() || oneAppPermission == EVENT_EDITOR);
            userPermissionBean.setEventReporter(userPermissionBean.isEventReporter() || oneAppPermission == EVENT_REPORTER);

            userPermissionBean.setFacebook(userPermissionBean.isFacebook() || oneAppPermission == ADMIN_CAMPAIGN_FB);
            userPermissionBean.setTwitter(userPermissionBean.isTwitter() || oneAppPermission == ADMIN_CAMPAIGN_TWITTER);
            userPermissionBean.setSms(userPermissionBean.isSms() || oneAppPermission == ADMIN_SMS);
            userPermissionBean.setEmail(userPermissionBean.isEmail() || oneAppPermission == ADMIN_EMAIL);
            userPermissionBean.setCall(userPermissionBean.isCall() || oneAppPermission == CALL_CAMPAIGN_ADMIN);
            userPermissionBean.setMobileGroups(userPermissionBean.isMobileGroups() || oneAppPermission == ADMIN_MOBILE_GROUP);
            userPermissionBean.setDonation(userPermissionBean.isDonation() || oneAppPermission == ADMIN_DONATION);
            userPermissionBean.setCandidates(userPermissionBean.isCandidates() || oneAppPermission == ADMIN_CANDIDATE);
            userPermissionBean.setElection(userPermissionBean.isElection() || oneAppPermission == ADMIN_ELECTION);

        }
        userPermissionBean.setAppPermissions(appPermissions);
    }


}
