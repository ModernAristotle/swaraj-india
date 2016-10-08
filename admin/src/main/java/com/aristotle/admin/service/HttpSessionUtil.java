package com.aristotle.admin.service;

import com.aristotle.core.persistance.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Component
public class HttpSessionUtil {

    private static final String LOGGED_IN_USER_SESSION_PARAM_NAME = "_u";
    private static final String LOGGED_IN_USER_LOCATION_SESSION_PARAM_NAME = "_ul";
    private static final String LOGGED_IN_USER_LOCATION_COOLKIE_NAME = "_l";

    public void logoff(HttpServletRequest httpServletRequest, User user) {
        httpServletRequest.getSession().invalidate();
    }


    public void setLoggedInUser(HttpServletRequest httpServletRequest, User user) {
        httpServletRequest.getSession(true).setAttribute(LOGGED_IN_USER_SESSION_PARAM_NAME, user);
    }

    public User getLoggedInUser(HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getSession(true).getAttribute(LOGGED_IN_USER_SESSION_PARAM_NAME);
    }

    public void setLoggedInUserLocations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Set<Long> userLocations) {
        httpServletRequest.getSession(true).setAttribute(LOGGED_IN_USER_LOCATION_SESSION_PARAM_NAME, userLocations);
        Cookie cookie = new Cookie(LOGGED_IN_USER_LOCATION_COOLKIE_NAME, StringUtils.collectionToCommaDelimitedString(userLocations));
        cookie.setMaxAge(31536000);
        cookie.setPath("/");
        cookie.setDomain(".swarajabhiyan.org");
        httpServletResponse.addCookie(cookie);
    }

    public Set<Long> getLoggedInUserLocations(HttpServletRequest httpServletRequest) {
        Set<Long> locationIds = (Set<Long>) httpServletRequest.getSession(true).getAttribute(LOGGED_IN_USER_LOCATION_SESSION_PARAM_NAME);
        if (locationIds == null && httpServletRequest.getCookies() != null) {
            for (Cookie oneCookie : httpServletRequest.getCookies()) {
                if (oneCookie.getName().equals(LOGGED_IN_USER_LOCATION_COOLKIE_NAME)) {
                    Set<String> locationIdsStr = StringUtils.commaDelimitedListToSet(oneCookie.getValue());
                    locationIds = new HashSet<Long>();
                    for (String oneLocation : locationIdsStr) {
                        try {
                            locationIds.add(Long.parseLong(oneLocation));
                        } catch (Exception ex) {

                        }
                    }
                    break;
                }
            }
        }
        return locationIds;
    }
}
