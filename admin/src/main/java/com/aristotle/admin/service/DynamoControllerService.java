package com.aristotle.admin.service;

import com.aristotle.admin.controller.beans.dynamo.DomainBean;
import com.aristotle.admin.controller.beans.dynamo.DomainTemplateBean;
import com.aristotle.core.exception.AppException;
import com.next.dynamo.exception.DynamoException;
import com.next.dynamo.persistance.Domain;
import com.next.dynamo.persistance.DomainTemplate;
import com.next.dynamo.service.DynamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Transactional(rollbackFor = Exception.class)
public class DynamoControllerService {

    @Autowired
    private DynamoService dynamoService;

    public DomainBean saveDomain(DomainBean domainBean) throws AppException {

        try {
            Domain domain = getOrCreateDomain(domainBean);
            domain = dynamoService.saveDomain(domain);
            copyProperties(domain, domainBean);
            return domainBean;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<DomainBean> getAllDomains() throws AppException {

        try {
            Page<Domain> domains = dynamoService.getDomains(0, 100);
            List<DomainBean> domainBeanList = new ArrayList<>(domains.getSize());
            for (Domain oneDomain : domains) {
                DomainBean domainBean = new DomainBean();
                copyProperties(oneDomain, domainBean);
                domainBeanList.add(domainBean);
            }
            return domainBeanList;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    private Domain getOrCreateDomain(DomainBean domainBean) throws DynamoException, AppException {
        Domain domain;
        if (domainBean.getId() != null) {
            domain = dynamoService.getDomainById(domainBean.getId());
            if (domain == null) {
                throw new AppException("No domain found for [id=" + domainBean.getId() + "]");
            }
        } else {
            domain = new Domain();
        }
        copyProperties(domainBean, domain);
        return domain;
    }

    public DomainTemplateBean saveDomainTemplate(DomainTemplateBean domainTemplateBean) throws AppException {

        try {
            DomainTemplate domainTemplate = getOrCreateDomainTemplate(domainTemplateBean);
            if (domainTemplateBean.getDomainId() != null) {
                Domain domain = dynamoService.getDomainById(domainTemplateBean.getDomainId());
                domainTemplate.setDomain(domain);
            }
            domainTemplate = dynamoService.saveDomainTemplate(domainTemplate);
            copyProperties(domainTemplate, domainTemplateBean);
            return domainTemplateBean;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<DomainTemplateBean> getAllDomainTemplates() throws AppException {

        try {
            List<DomainTemplate> domainTemplates = dynamoService.getAllDomainTemplates();
            List<DomainTemplateBean> domainTemplateBeanList = new ArrayList<>(domainTemplates.size());
            for (DomainTemplate oneDomainTemplate : domainTemplates) {
                DomainTemplateBean domainTemplateBean = new DomainTemplateBean();
                copyProperties(oneDomainTemplate, domainTemplateBean);
                domainTemplateBeanList.add(domainTemplateBean);
            }
            return domainTemplateBeanList;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    private DomainTemplate getOrCreateDomainTemplate(DomainTemplateBean domainTemplateBean) throws DynamoException, AppException {
        DomainTemplate domainTemplate;
        if (domainTemplateBean.getId() != null) {
            domainTemplate = dynamoService.getDomainTemplateById(domainTemplateBean.getId());
            if (domainTemplate == null) {
                throw new AppException("No domain template found for [id=" + domainTemplateBean.getId() + "]");
            }
        } else {
            domainTemplate = new DomainTemplate();
        }
        copyProperties(domainTemplateBean, domainTemplate);
        return domainTemplate;
    }
}
