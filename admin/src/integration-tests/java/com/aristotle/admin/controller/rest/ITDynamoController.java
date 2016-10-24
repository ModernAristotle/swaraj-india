package com.aristotle.admin.controller.rest;


import com.aristotle.admin.controller.beans.dynamo.DomainBean;
import com.aristotle.admin.controller.beans.dynamo.DomainTemplateBean;
import com.google.common.collect.Sets;
import com.next.dynamo.persistance.Domain;
import com.next.dynamo.persistance.repository.DomainRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ITDynamoController extends AbstractBaseControllerTest {

    @Autowired
    private DomainRepository domainRepository;

    /*
    When Domain name is null
     */
    @Test
    public void testSaveDomain_whenDomainNameIsNull() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName(null);
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Domain Name can not be null or empty")));

        List<Domain> dbDomains = domainRepository.findAll();
        assertThat(dbDomains.size(), is(0));
    }

    /*
    When Domain name is empty String
     */
    @Test
    public void testSaveDomain_whenDomainNameIsEmptyString() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName("");
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Domain Name can not be null or empty")));

        List<Domain> dbDomains = domainRepository.findAll();
        assertThat(dbDomains.size(), is(0));
    }

    /*
    When Domain name is String with Spaces only
     */
    @Test
    public void testSaveDomain_whenDomainNameIsStringWithSpacesOnly() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName("   ");
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Domain Name can not be null or empty")));

        List<Domain> dbDomains = domainRepository.findAll();
        assertThat(dbDomains.size(), is(0));
    }

    /*
    When Domain name is Valid String and no aliases provided
     */
    @Test
    public void testSaveDomain_whenDomainNameIsValidString() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName("www.swarajindia.org");
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(domainBean.getName())))
                .andExpect(jsonPath("$.aliases", is(nullValue())))
                .andExpect(jsonPath("$.setting", is(nullValue())))
                .andExpect(jsonPath("$.active", is(false)));

        List<Domain> dbDomains = domainRepository.findAll();
        assertThat(dbDomains.size(), is(1));
        assertThat(dbDomains.get(0).getName(), is(domainBean.getName()));
    }

    /*
    When Domain name is Valid String and aliases provided
     */
    @Test
    public void testSaveDomain_whenDomainNameIsValidStringAndAliasesProvided() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName("www.swarajindia.org");
        domainBean.setAliases(Sets.newHashSet("swarajindia.org", "si.org"));
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(domainBean.getName())))
                .andExpect(jsonPath("$.aliases", containsInAnyOrder("swarajindia.org", "si.org")))
                .andExpect(jsonPath("$.setting", is(nullValue())))
                .andExpect(jsonPath("$.active", is(false)));
    }

    /*
    When Domain name is Valid String and aliases provided
     */
    @Test
    public void testSaveDomain_whenDomainNameIsValidStringAndSettingIsProvided() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName("www.swarajindia.org");
        domainBean.setAliases(Sets.newHashSet("swarajindia.org", "si.org"));
        domainBean.setSetting("Some Settings");
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(domainBean.getName())))
                .andExpect(jsonPath("$.aliases", containsInAnyOrder("swarajindia.org", "si.org")))
                .andExpect(jsonPath("$.setting", is(domainBean.getSetting())))
                .andExpect(jsonPath("$.active", is(false)));
    }

    /*
    When Domain name is Valid String and domain set as active
     */
    @Test
    public void testSaveDomain_whenDomainNameIsValidStringAndActive() throws Exception {
        final DomainBean domainBean = new DomainBean();
        domainBean.setName("www.swarajindia.org");
        domainBean.setAliases(Sets.newHashSet("swarajindia.org", "si.org"));
        domainBean.setSetting("Some Settings");
        domainBean.setActive(true);
        mockMvc.perform(post(DOMAIN_URL)
                .content(toJson(domainBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(domainBean.getName())))
                .andExpect(jsonPath("$.aliases", containsInAnyOrder("swarajindia.org", "si.org")))
                .andExpect(jsonPath("$.setting", is(domainBean.getSetting())))
                .andExpect(jsonPath("$.active", is(true)));
    }

    /*
    When No Domains in database
     */
    @Test
    public void testgetAllDomains_whenDbHaveNoDomains() throws Exception {

        mockMvc.perform(get(DOMAIN_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /*
    When One Domains in database
     */
    @Test
    public void testgetAllDomains_whenDbhaveOneDomain() throws Exception {
        Domain domainBean = new Domain();
        domainBean.setName("www.swarajindia.org");
        domainBean.setAliases(Sets.newHashSet("swarajindia.org", "si.org"));
        domainBean.setSetting("Some Settings");
        domainBean.setActive(true);

        domainBean = domainRepository.save(domainBean);
        mockMvc.perform(get(DOMAIN_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(domainBean.getName())))
                .andExpect(jsonPath("$[0].aliases", containsInAnyOrder("swarajindia.org", "si.org")))
                .andExpect(jsonPath("$[0].setting", is(domainBean.getSetting())))
                .andExpect(jsonPath("$[0].active", is(true)));
    }

    /*
    When two Domains in database, they will be returned sorted by name in ascending order
     */
    @Test
    public void testgetAllDomains_whenDbhaveTwoDomain() throws Exception {
        Domain domainOne = new Domain();
        domainOne.setName("www.swarajindia.org");
        domainOne.setAliases(Sets.newHashSet("swarajindia.org", "si.org"));
        domainOne.setSetting("Some Settings");
        domainOne.setActive(true);
        domainOne = domainRepository.save(domainOne);

        Domain domainTwo = new Domain();
        domainTwo.setName("www.swarajabhiyan.org");
        domainTwo.setAliases(Sets.newHashSet("swarajabhiyan.org", "sa.org"));
        domainTwo.setSetting("Some Settings for abhiyan");
        domainTwo.setActive(false);
        domainTwo = domainRepository.save(domainTwo);

        mockMvc.perform(get(DOMAIN_URL)
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(domainTwo.getName())))
                .andExpect(jsonPath("$[0].aliases", containsInAnyOrder("swarajabhiyan.org", "sa.org")))
                .andExpect(jsonPath("$[0].setting", is(domainTwo.getSetting())))
                .andExpect(jsonPath("$[0].active", is(false)))
                .andExpect(jsonPath("$[1].name", is(domainOne.getName())))
                .andExpect(jsonPath("$[1].aliases", containsInAnyOrder("swarajindia.org", "si.org")))
                .andExpect(jsonPath("$[1].setting", is(domainOne.getSetting())))
                .andExpect(jsonPath("$[1].active", is(true)));
    }

    /*
    When Domain Template do not have valid Domain Id
     */
    @Test
    public void testSaveDomainTemplate_whenDomainNameIsNull() throws Exception {
        final DomainTemplateBean domainTemplateBean = new DomainTemplateBean();
        domainTemplateBean.setDomainId(null);
        mockMvc.perform(post(DOMAIN_TEMPLATE_URL)
                .content(toJson(domainTemplateBean))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Domain Template must have valid domain")));

        List<Domain> dbDomains = domainRepository.findAll();
        assertThat(dbDomains.size(), is(0));
    }

}
