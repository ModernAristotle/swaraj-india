package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.UserBean;
import com.aristotle.admin.controller.rest.error.ApiErrorResponse;
import com.aristotle.admin.exception.NotAllowedException;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Transactional(rollbackFor = Exception.class)
@Slf4j
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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse test(IllegalArgumentException e) {
        log.error("", e);
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


    @ExceptionHandler(AppException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse appExceptionHandler(AppException appException) {
        log.error("", appException);
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), appException.getMessage());
    }

    @ExceptionHandler(NotAllowedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse notAllowedExceptionHandler(NotAllowedException notAllowedException) {
        log.error("", notAllowedException);
        return new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), notAllowedException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse allOtherExceptions(Exception exception) {
        log.error("", exception);
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
