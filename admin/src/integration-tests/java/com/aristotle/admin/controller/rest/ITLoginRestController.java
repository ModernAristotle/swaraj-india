package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.LoginBean;
import com.aristotle.core.persistance.LoginAccount;
import com.aristotle.core.persistance.User;
import com.aristotle.core.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ITLoginRestController extends AbstractBaseControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void login_whenSendingEmptyLoginBean() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                .content(toJson(new LoginBean()))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("User name can not be null or Empty")));
    }

    @Test
    public void login_whenSendingLoginBeanWithaNonNullUserNameAndNullPassword() throws Exception {
        LoginBean loginBean = new LoginBean();
        loginBean.setLoginName("SomeLoginName");
        mockMvc.perform(post(LOGIN_URL)
                .content(toJson(loginBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Password can not be null or Empty")));
    }

    @Test
    public void login_whenSendingLoginBeanWithNonExistentUserName() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                .content(toJson(new LoginBean("NonExistentUserName", "SomePassword I dont care for this test")))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.message", is("Invalid user name/password")));
    }

    @Test
    public void login_whenALoginUserExistsButSendingLoginBeanWithNonExistentUserName() throws Exception {
        User user = createUser();
        user = userService.saveUser(user);
        LoginAccount loginAccount = userService.saveLoginAccount(user.getId(), "ravi", "password");
        mockMvc.perform(post(LOGIN_URL)
                .content(toJson(new LoginBean("NonExistentUserName", "SomePassword I dont care for this test")))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.message", is("Invalid user name/password")));
    }

    @Test
    public void login_whenALoginUserExistsAndSendingLoginBeanWithExistentUserNameButWrongPassword() throws Exception {
        User user = createUser();
        user = userService.saveUser(user);
        LoginAccount loginAccount = userService.saveLoginAccount(user.getId(), "ravi", "password");
        mockMvc.perform(post(LOGIN_URL)
                .content(toJson(new LoginBean("ravi", "IncorrectPassword")))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.message", is("Invalid user name/password")));
    }

    @Test
    public void login_whenALoginUserExistsAndSendingLoginBeanWithCorrectUserNameAndPassword() throws Exception {
        User user = createUser();
        user = userService.saveUser(user);
        userService.saveLoginAccount(user.getId(), "ravi", "password");
        mockMvc.perform(post(LOGIN_URL)
                .content(toJson(new LoginBean("ravi", "password")))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.loggedInUser.name", is("Ravi Sharma")))
                .andExpect(jsonPath("$.loggedInUser.id", is(user.getId().intValue())));
    }

}
