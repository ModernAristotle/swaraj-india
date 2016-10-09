package com.aristotle.core.service.impl;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.*;
import com.aristotle.core.persistance.repo.*;
import com.aristotle.core.service.EmailManager;
import com.aristotle.core.service.UserService;
import com.aristotle.core.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aristotle.core.persistance.UserLocation.LIVING;
import static com.aristotle.core.persistance.UserLocation.VOTING;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private LoginAccountRepository loginAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserLocationRepository userLocationRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private EmailConfirmationRequestRepository emailConfirmationRequestRepository;
    @Autowired
    private EmailManager emailManager;
    @Autowired
    private PasswordUtil passwordUtil;

    @Value("${registration_mail_id:NA}")
    private String regsitrationEmailId;

    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public User login(String userName, String password) throws AppException {
        Assert.notNull(userName, "User name can not be null or Empty");
        Assert.notNull(password, "Password can not be null or Empty");

        LoginAccount loginAccount = loginAccountRepository.getLoginAccountByUserName(userName.toLowerCase());
        if (loginAccount == null) {
            throw new AppException("Invalid user name/password");
        }
        if (!passwordUtil.checkPassword(password, loginAccount.getPassword())) {
            throw new AppException("Invalid user name/password");
        }
        return loginAccount.getUser();
    }

    @Override
    public User saveUser(User user) throws AppException {

        User dbUser = new User();
        BeanUtils.copyProperties(user, dbUser);
        Assert.notNull(dbUser.getName(), "Name must be provided");
        log.info("saving DB User : {}, \n copied from : {}", dbUser, user);

        dbUser = userRepository.save(dbUser);
        return dbUser;
    }

    private UserLocation addLocationsTouser(User dbUser, Long locationId, String type) throws AppException {
        if (locationId == null || locationId <= 0) {
            return null;
        }
        UserLocation userLocation = new UserLocation();
        userLocation.setUser(dbUser);
        Location location = locationRepository.findOne(locationId);
        if (location == null) {
            throw new AppException("Invalid Location id [" + locationId + "]");
        }
        userLocation.setLocation(location);
        userLocation.setUserLocationType(type);

        userLocation = userLocationRepository.save(userLocation);
        return userLocation;
    }

    @Override
    public LoginAccount saveLoginAccount(Long userId, String userName, String password) throws AppException {
        // create user login account
        if (StringUtils.isEmpty(password)) {
            password = passwordUtil.generateRandompassword();
        }
        User dbUser = userRepository.findOne(userId);
        LoginAccount loginAccount = new LoginAccount();
        loginAccount.setUser(dbUser);
        loginAccount.setUserName(userName);
        loginAccount.setPassword(passwordUtil.encryptPassword(password));
        loginAccount = loginAccountRepository.save(loginAccount);
        return loginAccount;
    }

    @Override
    public Phone savePhone(String countryCode, String mobileNumber) throws AppException {
        return getOrCreateMobile(mobileNumber, countryCode, true);
    }

    @Override
    public void addEmailToUser(Long emailId, Long userId) {
        if (emailId == null || userId == null) {
            return;
        }
        Email email = emailRepository.findOne(emailId);
        User user = userRepository.findOne(userId);
        email.setUser(user);
    }

    @Override
    public void addPhoneToUser(Long phoneId, Long userId) {
        if (phoneId == null || userId == null) {
            return;
        }
        Phone phone = phoneRepository.findOne(phoneId);
        User user = userRepository.findOne(userId);
        phone.setUser(user);
    }

    @Override
    public void linkPhoneToEmail(Long phoneId, Long emailId) throws AppException {
        Phone phone = phoneRepository.findOne(phoneId);
        Email email = emailRepository.findOne(emailId);
        email.setPhone(phone);
    }

    @Override
    public void saveUserLocations(Long userId, Long livingAcId, Long livingDistrictId, Long livingPcId, Long livingStateId, Long votingAcId, Long votingDistrictId, Long votingPcId, Long votingStateId, Long nriCountryId, Long nriCountryRegionId, Long nriCountryRegionAreaId) throws AppException {
        User dbUser = userRepository.findOne(userId);
        addLocationsTouser(dbUser, livingAcId, LIVING);
        addLocationsTouser(dbUser, votingAcId, VOTING);
        addLocationsTouser(dbUser, livingDistrictId, LIVING);
        addLocationsTouser(dbUser, votingDistrictId, VOTING);
        addLocationsTouser(dbUser, nriCountryId, LIVING);
        addLocationsTouser(dbUser, nriCountryRegionId, LIVING);
        addLocationsTouser(dbUser, nriCountryRegionAreaId, LIVING);
        addLocationsTouser(dbUser, livingPcId, LIVING);
        addLocationsTouser(dbUser, votingPcId, VOTING);
        addLocationsTouser(dbUser, livingStateId, LIVING);
        addLocationsTouser(dbUser, votingStateId, VOTING);
    }

    @Override
    public void saveUserVolunteerData(Long userId, Volunteer volunteer, Long[] interests) {
        Volunteer dbVolunteer = volunteerRepository.getVolunteersByUserId(userId);
        User user = userRepository.findOne(userId);
        if (dbVolunteer == null) {
            dbVolunteer = new Volunteer();
        }
        BeanUtils.copyProperties(volunteer, dbVolunteer);
        volunteer.setUser(user);
        volunteer = volunteerRepository.save(volunteer);

        volunteer.setInterests(new HashSet<>());
        if (interests != null) {
            for (Long oneInterestId : interests) {
                Interest oneInterest = interestRepository.findOne(oneInterestId);
                if (oneInterest != null) {
                    volunteer.getInterests().add(oneInterest);
                }
            }
        }

    }

    private Phone getOrCreateMobile(String mobileNumber, String countryCode, boolean failIfExists) throws AppException {
        if (StringUtils.isEmpty(mobileNumber)) {
            return null;
        }
        if (StringUtils.isEmpty(countryCode)) {
            throw new IllegalArgumentException("Country Code must be provided");
        }
        Phone mobile = phoneRepository.getPhoneByPhoneNumberAndCountryCode(mobileNumber, countryCode);
        if (mobile == null) {
            mobile = new Phone();
            mobile.setCountryCode(countryCode);
            mobile.setPhoneNumber(mobileNumber);
            if (countryCode.equals("91")) {
                mobile.setPhoneType(Phone.PhoneType.MOBILE);
            } else {
                mobile.setPhoneType(Phone.PhoneType.NRI_MOBILE);
            }

            mobile = phoneRepository.save(mobile);
        }
        if (failIfExists && (mobile.getUser() != null || mobile.isConfirmed())) {
            throw new AppException("Mobile [" + mobile.getCountryCode() + " - " + mobile.getPhoneNumber() + "] already registered");
        }
        return mobile;
    }

    @Override
    public Email saveEmail(String emailId) throws AppException {
        return getOrCreateEmail(emailId, true);
    }

    private Email getOrCreateEmail(String emailId, boolean failIfExists) throws AppException {
        if (StringUtils.isEmpty(emailId)) {
            return null;
        }
        Matcher matcher = EMAIL_COMPILED_PATTERN.matcher(emailId);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Email id");
        }
        Email email = emailRepository.getEmailByEmailUp(emailId.toUpperCase());
        if (email == null) {
            email = new Email();
            email.setConfirmed(false);
            email.setEmail(emailId);
            email.setEmailUp(emailId.toUpperCase());
            email.setNewsLetter(true);
            email.setConfirmationType(Email.ConfirmationType.UN_CONFIRNED);
            email = emailRepository.save(email);
        }
        if (failIfExists && (email.isConfirmed() || email.getUser() != null)) {
            throw new IllegalArgumentException("Email is already registered");
        }
        email.setNewsLetter(true);
        return email;
    }

    @Override
    public void sendEmailConfirmtionEmail(String emailId) throws AppException {
        if ("all".equals(emailId)) {
            confirmAllEmail();
            return;
        }
        Email email = emailRepository.getEmailByEmailUp(emailId.toUpperCase());
        if (email == null) {
            throw new AppException("No accounts exists for email " + emailId);
        }
        EmailConfirmationRequest emailConfirmationRequest = emailConfirmationRequestRepository.getEmailConfirmationRequestByEmail(emailId.toLowerCase());
        if (emailConfirmationRequest == null) {
            emailConfirmationRequest = new EmailConfirmationRequest();
        }
        emailConfirmationRequest.setEmail(emailId.toLowerCase());
        emailConfirmationRequest.setToken(UUID.randomUUID().toString());
        emailConfirmationRequest = emailConfirmationRequestRepository.save(emailConfirmationRequest);
        sendEmailConfirmationEmail(emailId.toLowerCase(), emailConfirmationRequest);

    }

    private void confirmAllEmail() {

        int totalFailed = 0;
        int totalSuccess = 0;
        Pageable pageRequest = new PageRequest(0, 100);
        Page<Email> emails;
        while (true) {
            emails = emailRepository.findAll(pageRequest);
            if (!emails.hasContent()) {
                break;
            }
            for (Email oneEmail : emails.getContent()) {
                if (oneEmail.isConfirmed()) {
                    continue;
                }
                try {
                    sendEmailConfirmtionEmail(oneEmail.getEmail());
                    totalSuccess++;
                } catch (Exception ex) {
                    totalFailed++;
                    ex.printStackTrace();
                }
            }
            pageRequest = pageRequest.next();
        }

        log.info("Total Success : {}, , Total Failed : {}", totalSuccess, totalFailed);
    }

    private void sendEmailConfirmationEmail(String emailId, EmailConfirmationRequest emailConfirmationRequest) throws AppException {
        String emailValidationUrl = "http://www.swarajabhiyan.org/email/verify?email=" + emailId + "&token=" + emailConfirmationRequest.getToken();
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<p>Thankyou for registering at <a href=\"http://www.swarajabhiyan.org\">www.swarajabhiyan.org</a></p>");
        sb.append("<br>");
        sb.append("<p>As part of registration process you need to validate your email by clicking <a href=\"" + emailValidationUrl + "\" >here</a> or copy following url and open it in a browser.</p>");
        sb.append("<br>");
        sb.append("<p>" + emailValidationUrl + "</p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>Thanks");
        sb.append("<br>Swaraj Abhiyan Team");
        sb.append("<br><br>++++++++++++++++++++++++++++");
        sb.append("<br>Website : www.swarajabhiyan.org");
        sb.append("<br>Email Id: contact@swarajabhiyan.org");
        sb.append("<br>Helpline no : +91-7210222333");
        sb.append("<br>Twitter Handle : https://twitter.com/swaraj_abhiyan");
        sb.append("<br>Facebook Pages : https://www.facebook.com/swarajabhiyan");
        sb.append("<br>Facebook group : https://www.facebook.com/groups/swarajabhiyan/");
        sb.append("<br>Volunteer Registration : http://www.swarajabhiyan.org/register");
        sb.append("<br>Swaraj Abhiyan Channel https://www.youtube.com/SwarajAbhiyanTV");
        sb.append("<br>Head Office : A-189, Sec-43, Noida UP");

        // now send Email
        String contentWithOutHtml = sb.toString();
        contentWithOutHtml = contentWithOutHtml.replaceAll("<br>", "\n");
        contentWithOutHtml = contentWithOutHtml.replaceAll("\\<[^>]*>", "");
        emailManager.sendEmail(emailId, "Registration", regsitrationEmailId, "Swaraj Abhiyan Email Verification", contentWithOutHtml, sb.toString());

    }

    private void sendMembershipConfirmationEmail(String emailId, User user, EmailConfirmationRequest emailConfirmationRequest) throws AppException {
        String emailValidationUrl = "http://www.swarajabhiyan.org/email/verify?email=" + emailId + "&token=" + emailConfirmationRequest.getToken();
        StringBuilder sb = new StringBuilder();
        sb.append("Hello " + user.getName());
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<p>Thankyou for registering at <a href=\"http://www.swarajabhiyan.org\">Swaraj Abhiyan</a> and becoming its valuable member</p>");
        sb.append("<br>");
        sb.append("<p>Your Membership ID is : " + user.getMembershipNumber() + "</p>");
        sb.append("<br>");
        sb.append("<p>Also as part of registration process you need to validate your email by clicking <a href=\"" + emailValidationUrl + "\" >here</a> or copy following url and open it in a browser.</p>");
        sb.append("<br>");
        sb.append("<p>" + emailValidationUrl + "</p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>Thanks");
        sb.append("<br>Swaraj Abhiyan Team");
        sb.append("<br><br>++++++++++++++++++++++++++++");
        sb.append("<br>Website : www.swarajabhiyan.org");
        sb.append("<br>Email Id: contact@swarajabhiyan.org");
        sb.append("<br>Helpline no : +91-7210222333");
        sb.append("<br>Twitter Handle : https://twitter.com/swaraj_abhiyan");
        sb.append("<br>Facebook Pages : https://www.facebook.com/swarajabhiyan");
        sb.append("<br>Facebook group : https://www.facebook.com/groups/swarajabhiyan/");
        sb.append("<br>Volunteer Registration : http://www.swarajabhiyan.org/register");
        sb.append("<br>Swaraj Abhiyan Channel https://www.youtube.com/SwarajAbhiyanTV");
        sb.append("<br>Head Office : A-189, Sec-43, Noida UP");

        // now send Email
        String contentWithOutHtml = sb.toString();
        contentWithOutHtml = contentWithOutHtml.replaceAll("<br>", "\n");
        contentWithOutHtml = contentWithOutHtml.replaceAll("\\<[^>]*>", "");
        emailManager.sendEmail(emailId, "Member Registration", regsitrationEmailId, "Welcome to Swaraj Abhiyan", contentWithOutHtml, sb.toString());

    }

    @Override
    public void sendMemberForIndexing(Long userId) throws AppException {
        throw new AppException("Not implemented yet");
//        if(user.isMember()){
//            userSearchService.sendUserForIndexing(user.getId().toString());
//
//        }
    }
}
