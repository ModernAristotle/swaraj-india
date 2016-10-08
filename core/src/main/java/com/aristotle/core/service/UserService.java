package com.aristotle.core.service;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.*;

public interface UserService {

    User login(String userName, String password) throws AppException;

    User saveUser(User user) throws AppException;

    LoginAccount saveLoginAccount(Long userId, String userName, String password) throws AppException;

    Email saveEmail(String emailId) throws AppException;

    Phone savePhone(String countryCode, String number) throws AppException;

    void addEmailToUser(Long emailId, Long userId) throws AppException;

    void addPhoneToUser(Long phoneId, Long userId) throws AppException;

    void saveUserLocations(Long userId, Long livingAcId, Long livingDistrictId, Long livingPcId, Long livingStateId, Long votingAcId, Long votingDistrictId, Long votingPcId, Long votingStateId, Long nriCountryId, Long nriCountryRegionId, Long nriCountryRegionAreaId) throws AppException;

    void saveUserVolunteerData(Long userId, Volunteer volunteer, Long[] interests);

    void sendEmailConfirmtionEmail(String emailId) throws AppException;

    void sendMemberForIndexing(Long userId) throws AppException;
}
