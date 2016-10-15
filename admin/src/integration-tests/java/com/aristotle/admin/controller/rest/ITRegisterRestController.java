package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.UserRegisterBean;
import com.aristotle.core.enums.CreationType;
import com.aristotle.core.persistance.Email;
import com.aristotle.core.persistance.LoginAccount;
import com.aristotle.core.persistance.Phone;
import com.aristotle.core.persistance.User;
import com.aristotle.core.persistance.repo.EmailRepository;
import com.aristotle.core.persistance.repo.LoginAccountRepository;
import com.aristotle.core.persistance.repo.PhoneRepository;
import com.aristotle.core.persistance.repo.UserRepository;
import com.aristotle.core.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ITRegisterRestController extends AbstractBaseControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginAccountRepository loginAccountRepository;

    /*
    No EMail provided, null
     */
    @Test
    public void register_whenSendingEmptyUserRegisterBean() throws Exception {
        UserRegisterBean userRegisterBean = new UserRegisterBean();
        permformPost(REGISTER_WEB_URL, userRegisterBean)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Valid Email must be provided")));
    }


    /*
    Not Null but InValid email address syntax wise
     */
    @Test
    public void register_whenSendingUserRegisterBeanWithNonNullButInvalidEmail() throws Exception {
        UserRegisterBean userRegisterBean = new UserRegisterBean();
        userRegisterBean.setEmailId("NotAValidEmailId");
        permformPost(REGISTER_WEB_URL, userRegisterBean)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Invalid Email id")));
    }

    /*
    Valid email address syntax wise
    But no mobile number provided
     */
    @Test
    public void register_NoMobileNumberProvided() throws Exception {
        UserRegisterBean userRegisterBean = new UserRegisterBean();
        userRegisterBean.setEmailId("abc@gmail.com");
        permformPost(REGISTER_WEB_URL, userRegisterBean)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Valid Mobile number must be provided")));
    }

    /*
    Valid email address syntax wise provided
    mobile number is provided
    but no Country Code Provided
     */
    @Test
    public void register_NoCountryCodeProvided() throws Exception {
        UserRegisterBean userRegisterBean = new UserRegisterBean();
        userRegisterBean.setEmailId("abc@gmail.com");
        userRegisterBean.setMobileNumber("9876543210");
        permformPost(REGISTER_WEB_URL, userRegisterBean)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Country Code must be provided")));
    }

    /*
    Valid email address syntax wise provided
    mobile number is provided
    Country Code is Provided
    But Name is missing
     */
    @Test
    public void register_NoNameProvided() throws Exception {
        UserRegisterBean userRegisterBean = new UserRegisterBean();
        userRegisterBean.setEmailId("abc@gmail.com");
        userRegisterBean.setMobileNumber("9876543210");
        userRegisterBean.setCountryCode("91");
        permformPost(REGISTER_WEB_URL, userRegisterBean)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Name must be provided")));
    }

    /*
    Valid email address syntax wise provided
    mobile number is provided
    Country Code is Provided
    Name is Provided
     */
    @Test
    public void register_minimumRequiredDataIsProvided() throws Exception {
        UserRegisterBean userRegisterBean = new UserRegisterBean();
        userRegisterBean.setEmailId("abc@gmail.com");
        userRegisterBean.setMobileNumber("9876543210");
        userRegisterBean.setCountryCode("91");
        userRegisterBean.setName("Ravi Sharma");
        permformPost(REGISTER_WEB_URL, userRegisterBean)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType));


        List<User> usersInDb = userRepository.findAll();
        assertEquals(1, usersInDb.size());
        User dbUser = usersInDb.get(0);
        assertEquals(userRegisterBean.getName(), dbUser.getName());
        assertEquals(CreationType.OnlineUser, dbUser.getCreationType());
        assertFalse(dbUser.isSuperAdmin());


        List<Phone> phonesInDb = phoneRepository.findAll();
        assertEquals(1, phonesInDb.size());
        Phone dbPhone = phonesInDb.get(0);
        assertEquals(userRegisterBean.getMobileNumber(), dbPhone.getPhoneNumber());
        assertEquals(userRegisterBean.getCountryCode().toUpperCase(), dbPhone.getCountryCode());
        assertEquals(Phone.PhoneType.MOBILE, dbPhone.getPhoneType());
        assertEquals(dbUser.getId(), dbPhone.getUser().getId());
        assertFalse(dbPhone.isConfirmed());

        List<Email> emailsInDb = emailRepository.findAll();
        assertEquals(1, emailsInDb.size());
        Email dbEmail = emailsInDb.get(0);
        assertEquals(userRegisterBean.getEmailId(), dbEmail.getEmail());
        assertEquals(userRegisterBean.getEmailId().toUpperCase(), dbEmail.getEmailUp());
        assertEquals(dbUser.getId(), dbEmail.getUser().getId());
        assertEquals(dbPhone.getId(), dbEmail.getPhone().getId());
        assertFalse(dbEmail.isConfirmed());
        assertTrue(dbEmail.isNewsLetter());

        List<LoginAccount> loginAccountsInDB = loginAccountRepository.findAll();
        assertEquals(1, loginAccountsInDB.size());
        LoginAccount dbLoginAccount = loginAccountsInDB.get(0);
        assertEquals(userRegisterBean.getEmailId(), dbLoginAccount.getUserName());
        assertNotNull(dbLoginAccount.getPassword());
        assertEquals(dbUser.getId(), dbLoginAccount.getUser().getId());

    }

}
