package com.aristotle.admin.controller.rest;

import com.aristotle.admin.App;
import com.aristotle.core.enums.CreationType;
import com.aristotle.core.persistance.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@EnableWebMvc
@Transactional
public abstract class AbstractBaseControllerTest {

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;


    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    protected String toJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected <T> ResultActions permformPost(String url, T requestBody) throws Exception {
        return mockMvc.perform(post(url)
                .content(toJson(requestBody))
                .contentType(contentType))
                .andDo(print());
    }

    protected User createUser() {
        User user = createUser("Ravi Sharma", CreationType.OnlineUser);
        return user;
    }

    protected User createUser(String name, CreationType creationType) {
        User user = new User();
        user.setName(name);
        user.setCreationType(creationType);
        return user;
    }

}
