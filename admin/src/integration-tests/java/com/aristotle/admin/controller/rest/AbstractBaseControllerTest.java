package com.aristotle.admin.controller.rest;

import com.aristotle.admin.AdminApp;
import com.aristotle.admin.dummy.impl.DummyEmailManagerImpl;
import com.aristotle.core.enums.CreationType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.LocationType;
import com.aristotle.core.persistance.User;
import com.aristotle.core.service.EmailManager;
import com.aristotle.core.service.LocationService;
import com.aristotle.core.service.impl.LocationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
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
@SpringBootTest(classes = AdminApp.class)
@WebAppConfiguration
@EnableWebMvc
@Transactional
@ComponentScan(basePackages = {"com.aristotle.admin.dummy"})
public abstract class AbstractBaseControllerTest {

    protected static final String LOGIN_URL = "/service/us/login";
    protected static final String REGISTER_WEB_URL = "/service/us/register/web";
    protected static final String REGISTER_MOBILE_URL = "/service/us/register/mobile";
    protected static final String LOCATION_GET_ALL_STATES_URL = "/service/us/location/states";
    protected static final String ELECTION_URL = "/service/s/election";
    protected static final String DOMAIN_URL = "/service/s/domain";
    protected static final String DOMAIN_TEMPLATE_URL = "/service/s/domaintemplate";



    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected LocationService locationService;
    @Autowired
    protected EmailManager emailManager;
    protected MockMvc mockMvc;


    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        ((DummyEmailManagerImpl) emailManager).clear();
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

    protected LocationType creatreStateLocationType() throws AppException {
        return createLocationType(LocationServiceImpl.STATE_LOCATION_TYPE);
    }

    protected LocationType createLocationType(String locationTypeName) throws AppException {
        LocationType locationType = new LocationType();
        locationType.setName(locationTypeName);
        return locationService.saveLocationType(locationType);
    }

    protected Location createLocation(LocationType locationType, Location parentLocation, String name, String isdCode, String stateCode) throws AppException {
        Location location = new Location();
        location.setName(name);
        location.setLocationType(locationType);
        location.setParentLocation(parentLocation);
        location.setIsdCode(isdCode);
        location.setStateCode(stateCode);
        return locationService.saveLocation(location);
    }

}
