package com.aristotle.core.service;

import com.aristotle.core.enums.AppPermission;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.*;

import java.util.List;
import java.util.Set;

public interface UserService {

    User login(String userName, String password) throws AppException;

    User saveUser(User user) throws AppException;

    User findUserById(Long userId) throws AppException;

    LoginAccount saveLoginAccount(Long userId, String userName, String password) throws AppException;

    Email saveEmail(String emailId) throws AppException;

    Phone savePhone(String countryCode, String number) throws AppException;

    void addEmailToUser(Long emailId, Long userId) throws AppException;

    void addPhoneToUser(Long phoneId, Long userId) throws AppException;

    void linkPhoneToEmail(Long phoneId, Long emailId) throws AppException;

    void saveUserLocations(Long userId, Long livingAcId, Long livingDistrictId, Long livingPcId, Long livingStateId, Long votingAcId, Long votingDistrictId, Long votingPcId, Long votingStateId, Long nriCountryId, Long nriCountryRegionId, Long nriCountryRegionAreaId) throws AppException;

    void saveUserVolunteerData(Long userId, Volunteer volunteer, Long[] interests);

    void sendEmailConfirmtionEmail(String emailId) throws AppException;

    void sendMemberForIndexing(Long userId) throws AppException;

    List<Location> getUserAdminLocations(Long userId) throws AppException;

    Set<AppPermission> getLocationPermissionsOfUser(Long userId, Long locationId) throws AppException;

}
