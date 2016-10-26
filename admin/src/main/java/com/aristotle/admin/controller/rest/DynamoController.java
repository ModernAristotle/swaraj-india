package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.dynamo.*;
import com.aristotle.admin.controller.rest.error.ApiErrorResponse;
import com.aristotle.admin.service.DynamoControllerService;
import com.aristotle.core.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/service/s/refresh/domaintemplate/{domainTemplateId}", method = GET)
    public ApiErrorResponse refreshDOmainTemplate(HttpServletRequest httpServletRequest, @PathVariable("domainTemplateId") Long domainTemplateId) throws AppException {
        dynamoControllerService.refreshDomainTemplate(domainTemplateId);
        return new ApiErrorResponse(HttpStatus.ACCEPTED.value(), "Refresh Initiated");
    }

    @RequestMapping(value = "/service/s/gitfiles/domaintemplate/{domainTemplateId}", method = GET)
    public List<String> getDomaintemplateGitFiles(HttpServletRequest httpServletRequest, @PathVariable("domainTemplateId") Long domainTemplateId) throws AppException {
        return dynamoControllerService.getDomaintemplateGitFiles(domainTemplateId);
    }

    @RequestMapping(value = "/service/s/urlmapping", method = POST)
    public UrlMappingBean saveUrlMapping(HttpServletRequest httpServletRequest, @RequestBody UrlMappingBean urlMappingBean) throws AppException {
        return dynamoControllerService.saveUrlMapping(urlMappingBean);
    }

    @RequestMapping(value = "/service/s/urlmapping/domain/{domainId}", method = GET)
    public List<UrlMappingBean> getAllUrlMappings(HttpServletRequest httpServletRequest, @PathVariable("domainId") Long domainId) throws AppException {
        return dynamoControllerService.getAllUrlMappingOfDomain(domainId);
    }

    @RequestMapping(value = "/service/s/htmlpart", method = POST)
    public HtmlPartBean saveHtmlPart(HttpServletRequest httpServletRequest, @RequestBody HtmlPartBean htmlPartBean) throws AppException {
        return dynamoControllerService.saveHtmlPart(htmlPartBean);
    }

    @RequestMapping(value = "/service/s/htmlpart/domaintemplate/{domainTemplateId}", method = GET)
    public List<HtmlPartBean> getAllHtmlPartsByDomainTemplateId(HttpServletRequest httpServletRequest, @PathVariable("domainTemplateId") Long domainTemplateId) throws AppException {
        return dynamoControllerService.getAllHtmlPartOfDomainTemplate(domainTemplateId);
    }

    @RequestMapping(value = "/service/s/htmlpart/main/domaintemplate/{domainTemplateId}", method = GET)
    public List<HtmlPartBean> getAllHtmlMainPartsByDomainTemplateId(HttpServletRequest httpServletRequest, @PathVariable("domainTemplateId") Long domainTemplateId) throws AppException {
        return dynamoControllerService.getAllHtmlMainPartOfDomainTemplate(domainTemplateId);
    }

    @RequestMapping(value = "/service/s/urltemplate", method = POST)
    public UrlTemplateBean saveUrlTemplate(HttpServletRequest httpServletRequest, @RequestBody UrlTemplateBean urlTemplateBean) throws AppException {
        return dynamoControllerService.saveUrlTemplate(urlTemplateBean);
    }

    @RequestMapping(value = "/service/s/urltemplate/domaintemplate/{domainTemplateId}", method = GET)
    public List<UrlTemplateBean> getAllUrlTemplatesByDomainTemplateId(HttpServletRequest httpServletRequest, @PathVariable("domainTemplateId") Long domainTemplateId) throws AppException {
        return dynamoControllerService.getAllUrlTemplateOfDomainTemplate(domainTemplateId);
    }


}
