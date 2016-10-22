package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.dynamo.DomainBean;
import com.aristotle.admin.service.DynamoControllerService;
import com.aristotle.core.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DynamoController extends BaseRestController {

    @Autowired
    private DynamoControllerService dynamoControllerService;

    @RequestMapping(value = "/service/s/domain", method = POST)
    public DomainBean saveElection(HttpServletRequest httpServletRequest, @RequestBody DomainBean domainBean) throws AppException {
        return dynamoControllerService.saveDomain(domainBean);
    }

    @RequestMapping(value = "/service/s/domain", method = GET)
    public List<DomainBean> getAllDOmains(HttpServletRequest httpServletRequest) throws AppException {
        return dynamoControllerService.getAllDomains();
    }
}
