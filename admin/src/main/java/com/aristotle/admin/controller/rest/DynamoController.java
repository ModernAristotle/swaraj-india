package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.dynamo.DomainBean;
import com.aristotle.admin.controller.beans.dynamo.DomainTemplateBean;
import com.aristotle.admin.controller.beans.dynamo.UrlMappingBean;
import com.aristotle.admin.service.DynamoControllerService;
import com.aristotle.core.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public DomainBean saveDomain(HttpServletRequest httpServletRequest, @RequestBody DomainBean domainBean) throws AppException {
        return dynamoControllerService.saveDomain(domainBean);
    }

    @RequestMapping(value = "/service/s/domain", method = GET)
    public List<DomainBean> getAllDomains(HttpServletRequest httpServletRequest) throws AppException {
        return dynamoControllerService.getAllDomains();
    }

    @RequestMapping(value = "/service/s/domaintemplate", method = POST)
    public DomainTemplateBean saveDomainTemplate(HttpServletRequest httpServletRequest, @RequestBody DomainTemplateBean domainTemplateBean) throws AppException {
        return dynamoControllerService.saveDomainTemplate(domainTemplateBean);
    }

    @RequestMapping(value = "/service/s/domaintemplate", method = GET)
    public List<DomainTemplateBean> getAllDomainTemplate(HttpServletRequest httpServletRequest) throws AppException {
        return dynamoControllerService.getAllDomainTemplates();
    }

    @RequestMapping(value = "/service/s/urlmapping", method = POST)
    public UrlMappingBean saveUrlMapping(HttpServletRequest httpServletRequest, @RequestBody UrlMappingBean urlMappingBean) throws AppException {
        return dynamoControllerService.saveUrlMapping(urlMappingBean);
    }

    @RequestMapping(value = "/service/s/urlmapping/domain/{domainId}", method = GET)
    public List<UrlMappingBean> getAllUrlMappings(HttpServletRequest httpServletRequest, @PathVariable("domainId") Long domainId) throws AppException {
        return dynamoControllerService.getAllUrlMappingOfDomain(domainId);
    }

}
