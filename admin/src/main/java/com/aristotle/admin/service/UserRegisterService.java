package com.aristotle.admin.service;


import com.aristotle.admin.controller.beans.UserRegisterBean;
import com.aristotle.core.enums.CreationType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Email;
import com.aristotle.core.persistance.Phone;
import com.aristotle.core.persistance.User;
import com.aristotle.core.persistance.Volunteer;
import com.aristotle.core.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserRegisterService {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSessionUtil httpSessionUtil;

    public User login(HttpServletRequest httpServletRequest, String userName, String password) throws AppException {
        User user = userService.login(userName, password);
        httpSessionUtil.setLoggedInUser(httpServletRequest, user);
        return user;
    }

    public UserRegisterBean register(HttpServletRequest httpServletRequest, UserRegisterBean userRegisterBean) throws AppException {
        Email email = userService.saveEmail(userRegisterBean.getEmailId());
        if (email == null) {
            throw new IllegalArgumentException("Valid Email must be provided");
        }
        Phone phone;
        if (userRegisterBean.isNri()) {
            phone = userService.savePhone(userRegisterBean.getNriCountryCode(), userRegisterBean.getNriMobileNumber());
        } else {
            phone = userService.savePhone(userRegisterBean.getCountryCode(), userRegisterBean.getMobileNumber());
        }
        if (phone == null) {
            throw new IllegalArgumentException("Valid Mobile number must be provided");
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterBean, user);
        user.setCreationType(CreationType.OnlineUser);
        user = userService.saveUser(user);

        if (email != null) {
            userService.addEmailToUser(email.getId(), user.getId());
        }
        if (phone != null) {
            userService.addPhoneToUser(phone.getId(), user.getId());
        }
        userService.linkPhoneToEmail(phone.getId(), email.getId());
        userService.saveUserLocations(user.getId(), userRegisterBean.getAssemblyConstituencyLivingId(), userRegisterBean.getDistrictLivingId(), userRegisterBean.getParliamentConstituencyLivingId(), userRegisterBean.getStateLivingId(), userRegisterBean.getAssemblyConstituencyVotingId(), userRegisterBean.getDistrictVotingId(), userRegisterBean.getParliamentConstituencyVotingId(), userRegisterBean.getStateVotingId(), userRegisterBean.getNriCountryId(), userRegisterBean.getNriCountryRegionId(), userRegisterBean.getNriCountryRegionAreaId());

        if (user.isVolunteer()) {
            Volunteer volunteer = new Volunteer();
            BeanUtils.copyProperties(userRegisterBean, volunteer);
            userService.saveUserVolunteerData(user.getId(), volunteer, userRegisterBean.getInterests());
        }
        userService.saveLoginAccount(user.getId(), userRegisterBean.getEmailId(), userRegisterBean.getPassword());
        userService.sendEmailConfirmtionEmail(userRegisterBean.getEmailId());
        //TODO userService.sendMemberForIndexing(user.getId());
        httpSessionUtil.setLoggedInUser(httpServletRequest, user);
        return null;
    }

}
