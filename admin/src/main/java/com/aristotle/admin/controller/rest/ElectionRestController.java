package com.aristotle.admin.controller.rest;

import com.aristotle.admin.service.ElectionControllerService;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Election;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class ElectionRestController extends BaseRestController {

    @Autowired
    private ElectionControllerService electionControllerService;

    @RequestMapping(value = "/service/s/election", method = RequestMethod.POST)
    public Election saveElection(HttpServletRequest httpServletRequest, @RequestBody Election election) throws AppException {
        return electionControllerService.saveElection(election);
    }
}
