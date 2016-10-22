package com.aristotle.admin.service;

import com.aristotle.admin.controller.beans.dynamo.DomainBean;
import com.aristotle.core.exception.AppException;
import com.next.dynamo.exception.DynamoException;
import com.next.dynamo.persistance.Domain;
import com.next.dynamo.service.DynamoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DynamoControllerService {

    @Autowired
    private DynamoService dynamoService;

    public DomainBean saveDomain(DomainBean domainBean) throws AppException {

        try {
            Domain domain = getOrCreateDomain(domainBean);
            domain = dynamoService.saveDomain(domain);
            BeanUtils.copyProperties(domain, domainBean);
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
                BeanUtils.copyProperties(oneDomain, domainBean);
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
        BeanUtils.copyProperties(domainBean, domain);
        return domain;
    }
}
