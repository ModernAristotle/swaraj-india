package com.aristotle.admin.controller.rest;

import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.LocationType;
import com.aristotle.core.service.LocationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ITLocationRestController extends AbstractBaseControllerTest {

    @Autowired
    private LocationService locationService;

    /*
    Test When LocationType and Location both doesnt exists
     */
    @Test
    public void getStates_WhenDatabaseIsEmpty() throws Exception {
        mockMvc.perform(get(LOCATION_GET_ALL_STATES_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /*
    Test when State Location Type exists but no State exists
     */
    @Test
    public void getStates_WhenStateTytpeExists() throws Exception {
        creatreStateLocationType();
        mockMvc.perform(get(LOCATION_GET_ALL_STATES_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /*
   Test when State Location Type exists and One State Exists
    */
    @Test
    public void getStates_WhenStateTytpeExistsAndOneStateExists() throws Exception {
        LocationType locationType = creatreStateLocationType();
        Location location = createLocation(locationType, null, "Haryana", null, "HR");
        mockMvc.perform(get(LOCATION_GET_ALL_STATES_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(location.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(location.getName())))
                .andExpect(jsonPath("$[0].stateCode", is(location.getStateCode())))
                .andExpect(jsonPath("$[0].isdCode", is(nullValue())))
                .andExpect(jsonPath("$[0].nameUp").doesNotExist());
    }

    /*
   Test when State Location Type exists and Two State Exists
    */
    @Test
    public void getStates_WhenStateTytpeExistsAndTwoStateExists() throws Exception {
        LocationType locationType = creatreStateLocationType();
        Location locationRajsthan = createLocation(locationType, null, "Rajsthan", null, "RJ");
        Location locationHaryna = createLocation(locationType, null, "Haryana", null, "HR");
        mockMvc.perform(get(LOCATION_GET_ALL_STATES_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(locationHaryna.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(locationHaryna.getName())))
                .andExpect(jsonPath("$[0].stateCode", is(locationHaryna.getStateCode())))
                .andExpect(jsonPath("$[0].isdCode", is(nullValue())))
                .andExpect(jsonPath("$[0].nameUp").doesNotExist())
                .andExpect(jsonPath("$[1].id", is(locationRajsthan.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(locationRajsthan.getName())))
                .andExpect(jsonPath("$[1].stateCode", is(locationRajsthan.getStateCode())))
                .andExpect(jsonPath("$[1].isdCode", is(nullValue())))
                .andExpect(jsonPath("$[1].nameUp").doesNotExist());
    }

}
