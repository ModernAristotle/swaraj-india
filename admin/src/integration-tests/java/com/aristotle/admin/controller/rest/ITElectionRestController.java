package com.aristotle.admin.controller.rest;


import com.aristotle.core.persistance.Election;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.LocationType;
import com.aristotle.core.persistance.repo.ElectionRepository;
import com.aristotle.core.service.ElectionService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ITElectionRestController extends AbstractBaseControllerTest {

    @Autowired
    protected ElectionService electionService;

    @Autowired
    protected ElectionRepository electionRepository;

    @Test
    public void testSaveElection_whenSendingEmptyElectionBean() throws Exception {
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(new Election()))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election title can not be blank")));
    }

    /*
    Test save election when sending title as "  "
     */
    @Test
    public void testSaveElection_whenSendingBlankTitle() throws Exception {
        Election election = new Election();
        election.setTitle("  ");
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election title can not be blank")));
    }

    /*
    Test save election when sending title as non blank/empty and other fields are null
     */
    @Test
    public void testSaveElection_whenSendingOnlyTitle() throws Exception {
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election Location can not be null")));
    }

    /*
    Test save election when sending
    title as non blank/empty
    Location id as non existing location
    and other fields are null
     */
    @Test
    public void testSaveElection_whenSendingOnlyTitleAndWrongLocationId() throws Exception {
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setLocationId(1000000L);
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", startsWith("Incorrect Location id [locationId : ")));
    }

    /*
    Test save election when sending
    title as non blank/empty
    Location id as existing location
    and LocationTypeId as null
     */
    @Test
    public void testSaveElection_whenSendingOnlyTitleAndCorrectLocationId() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setLocationId(location.getId());
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election Location type can not be null")));
    }

    /*
    Test save election when sending
    title as non blank/empty
    Location id as existing location
    and LocationTypeId is some non existant Id
     */
    @Test
    public void testSaveElection_whenSendingTitleLocationIdAndIncorrectLocationTypeId() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setLocationId(location.getId());
        election.setLocationTypeId(10000L);
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", startsWith("Incorrect Location Type id [locationTypeId : ")));
    }

    /*
    Test save election when sending
    title as non blank/empty
    Location id as existing location
    and LocationTypeId is some existant Id
    ElectionStart date as null
     */
    @Test
    public void testSaveElection_whenSendingTitleLocationIdCorrectLocationTypeIdAndNullElectionStartDate() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setLocationId(location.getId());
        election.setLocationTypeId(locationType.getId());
        election.setStartDate(null);
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election start date can not be null")));
    }

    /*
    Test save election when sending
    title as non blank/empty
    Location id as existing location
    and LocationTypeId is some existant Id
    ElectionStart date is not null
    Election end Date is null
     */
    @Test
    public void testSaveElection_whenSendingTitleLocationIdCorrectLocationTypeIdElectionStartDateAndNullEndDate() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setLocationId(location.getId());
        election.setLocationTypeId(locationType.getId());
        election.setStartDate(getDateAfterNDays(10));
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election end date can not be null")));
    }

    /*
    Test save election when sending
    Election Candidature Filing Date is Null
     */
    @Test
    public void testSaveElection_whenCandidatureElectionDateIsNull() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setLocationId(location.getId());
        election.setLocationTypeId(locationType.getId());
        election.setStartDate(getDateAfterNDays(10));
        election.setEndDate(getDateAfterNDays(20));
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election Candidature filing date can not be null")));
    }

    /*
        Test save election when startDate is after end Date
         */
    @Test
    public void testSaveElection_whenStartDateIsAfterEndDate() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setDescription("This Delhi election description");
        election.setLocationId(location.getId());
        election.setLocationTypeId(locationType.getId());
        election.setStartDate(getDateAfterNDays(20));
        election.setEndDate(getDateAfterNDays(19));
        election.setCandidatureFilingDate(getDateAfterNDays(5));
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election start date must be before Election End Date")));

    }

    /*
        Test save caldidature filing date is after election startDate
     */
    @Test
    public void testSaveElection_whenCandidatureFilingDateisAfterStartDate() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setDescription("This Delhi election description");
        election.setLocationId(location.getId());
        election.setLocationTypeId(locationType.getId());
        election.setStartDate(getDateAfterNDays(10));
        election.setEndDate(getDateAfterNDays(20));
        election.setCandidatureFilingDate(getDateAfterNDays(12));
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Election Candidature date must be before Election Start Date")));

    }

    /*
    Test save election when sending all Required Data
     */
    @Test
    public void testSaveElection_whenSendingAllRequiredData() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setDescription("This Delhi election description");
        election.setLocationId(location.getId());
        election.setLocationTypeId(locationType.getId());
        election.setStartDate(getDateAfterNDays(10));
        election.setEndDate(getDateAfterNDays(20));
        election.setCandidatureFilingDate(getDateAfterNDays(5));
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.description", is(election.getDescription())))
                .andExpect(jsonPath("$.title", is(election.getTitle())))
                .andExpect(jsonPath("$.startDate", is(election.getStartDate().getTime())))
                .andExpect(jsonPath("$.endDate", is(election.getEndDate().getTime())))
                .andExpect(jsonPath("$.candidatureFilingDate", is(election.getCandidatureFilingDate().getTime())))
                .andExpect(jsonPath("$.locationId", is(election.getLocationId().intValue())))
                .andExpect(jsonPath("$.locationTypeId", is(election.getLocationTypeId().intValue())))
        ;

        List<Election> dbElections = electionRepository.findAll();
        Assert.assertThat(dbElections.size(), is(1));
    }

    /*
   Test save/update election when sending all Required Data
    */
    @Test
    public void testSaveElection_whenSendingAllRequiredDataForUpdate() throws Exception {
        LocationType locationType = createLocationType("state");
        Location location = createLocation(locationType, null, "Delhi", null, "DL");
        Election election = new Election();
        election.setTitle("Delhi MCD elections");
        election.setDescription("This Delhi election description");
        election.setLocationId(location.getId());
        election.setLocation(location);
        election.setLocationTypeId(locationType.getId());
        election.setLocationType(locationType);
        election.setStartDate(getDateAfterNDays(10));
        election.setEndDate(getDateAfterNDays(20));
        election.setCandidatureFilingDate(getDateAfterNDays(5));
        election = electionService.saveElection(election);
        election.setTitle(election.getTitle() + "_update");
        election.setDescription(election.getDescription() + "_update");
        election.setStartDate(getDateAfterNDays(6));
        election.setEndDate(getDateAfterNDays(25));
        election.setCandidatureFilingDate(getDateAfterNDays(1));
        mockMvc.perform(post(ELECTION_URL)
                .content(toJson(election))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.description", is(election.getDescription())))
                .andExpect(jsonPath("$.title", is(election.getTitle())))
                .andExpect(jsonPath("$.startDate", is(election.getStartDate().getTime())))
                .andExpect(jsonPath("$.endDate", is(election.getEndDate().getTime())))
                .andExpect(jsonPath("$.candidatureFilingDate", is(election.getCandidatureFilingDate().getTime())))
                .andExpect(jsonPath("$.locationId", is(election.getLocationId().intValue())))
                .andExpect(jsonPath("$.locationTypeId", is(election.getLocationTypeId().intValue())))
        ;

        List<Election> dbElections = electionRepository.findAll();
        Assert.assertThat(dbElections.size(), is(1));
    }

    private Date getDateAfterNDays(int numberOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, numberOfDays);
        return calendar.getTime();
    }
}
