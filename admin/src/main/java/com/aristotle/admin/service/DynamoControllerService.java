package com.aristotle.admin.service;

import com.aristotle.admin.controller.beans.dynamo.*;
import com.aristotle.core.exception.AppException;
import com.next.dynamo.exception.DynamoException;
import com.next.dynamo.persistance.*;
import com.next.dynamo.service.DynamoService;
import com.next.dynamo.service.GitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Transactional(rollbackFor = Exception.class)
public class DynamoControllerService {

    @Autowired
    private DynamoService dynamoService;

    @Autowired
    private GitService gitService;

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

    public void refreshDomainTemplate(Long domainTemplateId) throws AppException {
        try {
            gitService.refreshDomainTemplateFromGit(domainTemplateId);
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<String> getDomaintemplateGitFiles(Long domainTemplateId) throws AppException {
        try {
            return gitService.getDomaintemplateGitFiles(domainTemplateId);
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

    public UrlMappingBean saveUrlMapping(UrlMappingBean urlMappingBean) throws AppException {

        try {
            UrlMapping urlMapping = getOrCreateUrlMapping(urlMappingBean);
            urlMapping.setDomain(getDomain(urlMappingBean.getDomainId()));
            urlMapping = dynamoService.saveUrlMapping(urlMapping);
            dynamoService.saveUrlMappingPlugins(urlMapping.getId(), urlMappingBean.getDataPlugins());

            copyProperties(urlMapping, urlMappingBean);
            return urlMappingBean;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<UrlMappingBean> getAllUrlMappingOfDomain(Long domainId) throws AppException {

        try {
            List<UrlMapping> urlMappings = dynamoService.getUrlMappingByDomainId(domainId);
            List<UrlMappingBean> urlMappingBeanList = new ArrayList<>(urlMappings.size());
            for (UrlMapping oneUrlMapping : urlMappings) {
                UrlMappingBean urlMappingBean = new UrlMappingBean();
                copyProperties(oneUrlMapping, urlMappingBean);
                urlMappingBeanList.add(urlMappingBean);
            }
            return urlMappingBeanList;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    private UrlMapping getOrCreateUrlMapping(UrlMappingBean urlMappingBean) throws DynamoException, AppException {
        UrlMapping urlMapping;
        if (urlMappingBean.getId() != null) {
            urlMapping = dynamoService.getUrlMappingById(urlMappingBean.getId());
            if (urlMapping == null) {
                throw new AppException("No Url Mapping found for [id=" + urlMappingBean.getId() + "]");
            }
        } else {
            urlMapping = new UrlMapping();
        }
        copyProperties(urlMappingBean, urlMapping);
        return urlMapping;
    }

    private Domain getDomain(Long domainId) throws DynamoException {
        if (domainId != null) {
            Domain domain = dynamoService.getDomainById(domainId);
            return domain;
        }
        return null;
    }

    public HtmlPartBean saveHtmlPart(HtmlPartBean htmlPartBean) throws AppException {
        try {
            PartTemplate partTemplate = getOrCreateHtmlPart(htmlPartBean);
            partTemplate.setDomainTemplate(getDomainTemplate(htmlPartBean.getDomainTemplateId()));
            partTemplate = dynamoService.savePartTemplate(partTemplate);
            copyProperties(partTemplate, htmlPartBean);
            return htmlPartBean;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<HtmlPartBean> getAllHtmlPartOfDomainTemplate(Long domainTemplateId) throws AppException {
        try {
            List<PartTemplate> partTemplates = dynamoService.findPartTemplateByDomainTemplate(domainTemplateId);
            List<HtmlPartBean> htmlPartBeanList = convertToHtmlPartBean(partTemplates);
            return htmlPartBeanList;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<HtmlPartBean> getAllHtmlMainPartOfDomainTemplate(Long domainTemplateId) throws AppException {
        try {
            List<PartTemplate> partTemplates = dynamoService.findMainPartTemplateByDomainTemplate(domainTemplateId);
            List<HtmlPartBean> htmlPartBeanList = convertToHtmlPartBean(partTemplates);
            return htmlPartBeanList;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    private List<HtmlPartBean> convertToHtmlPartBean(List<PartTemplate> partTemplates) {
        List<HtmlPartBean> htmlPartBeanList = new ArrayList<>(partTemplates.size());
        for (PartTemplate onePartTemplate : partTemplates) {
            HtmlPartBean htmlPartBean = new HtmlPartBean();
            copyProperties(onePartTemplate, htmlPartBean);
            htmlPartBeanList.add(htmlPartBean);
        }
        return htmlPartBeanList;
    }

    private PartTemplate getOrCreateHtmlPart(HtmlPartBean htmlPartBean) throws DynamoException, AppException {
        PartTemplate partTemplate;
        if (htmlPartBean.getId() != null) {
            partTemplate = dynamoService.getPartTemplateById(htmlPartBean.getId());
            if (partTemplate == null) {
                throw new AppException("No Part Template found for [id=" + htmlPartBean.getId() + "]");
            }
        } else {
            partTemplate = new PartTemplate();
        }
        copyProperties(htmlPartBean, partTemplate);
        return partTemplate;
    }

    private DomainTemplate getDomainTemplate(Long domainTemplateId) throws DynamoException {
        if (domainTemplateId != null) {
            DomainTemplate domainTemplate = dynamoService.getDomainTemplateById(domainTemplateId);
            return domainTemplate;
        }
        return null;
    }

    private PartTemplate getPartTemplate(Long partTemplateId) throws DynamoException {
        if (partTemplateId != null) {
            PartTemplate partTemplate = dynamoService.getPartTemplateById(partTemplateId);
            return partTemplate;
        }
        return null;
    }

    private UrlMapping getUrlMapping(Long urlMappingId) throws DynamoException {
        if (urlMappingId != null) {
            UrlMapping urlMapping = dynamoService.getUrlMappingById(urlMappingId);
            return urlMapping;
        }
        return null;
    }

    public UrlTemplateBean saveUrlTemplate(UrlTemplateBean urlTemplateBean) throws AppException {
        try {
            PageTemplate pageTemplate = getOrCreatePageTemplate(urlTemplateBean);
            pageTemplate.setDomainTemplate(getDomainTemplate(urlTemplateBean.getDomainTemplateId()));
            pageTemplate.setMainTemplate(getPartTemplate(urlTemplateBean.getMainTemplateId()));
            pageTemplate.setUrlMapping(getUrlMapping(urlTemplateBean.getUrlMappingId()));
            pageTemplate = dynamoService.savePageTemplate(pageTemplate);
            copyProperties(pageTemplate, urlTemplateBean);
            return urlTemplateBean;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    private PageTemplate getOrCreatePageTemplate(UrlTemplateBean urlTemplateBean) throws DynamoException, AppException {
        PageTemplate pageTemplate;
        if (urlTemplateBean.getId() != null) {
            pageTemplate = dynamoService.getPageTemplateById(urlTemplateBean.getId());
            if (pageTemplate == null) {
                throw new AppException("No Part Template found for [id=" + urlTemplateBean.getId() + "]");
            }
        } else {
            pageTemplate = new PageTemplate();
        }
        copyProperties(urlTemplateBean, pageTemplate);
        return pageTemplate;
    }

    public List<UrlTemplateBean> getAllUrlTemplateOfDomainTemplate(Long domainTemplateId) throws AppException {
        try {
            List<PageTemplate> partTemplates = dynamoService.findPageTemplatesByDomainTemplateId(domainTemplateId);
            List<UrlTemplateBean> urlTemplateBeanList = new ArrayList<>(partTemplates.size());
            for (PageTemplate onePageTemplate : partTemplates) {
                UrlTemplateBean urlTemplateBean = new UrlTemplateBean();
                copyProperties(onePageTemplate, urlTemplateBean);
                urlTemplateBean.setUrlPattern(onePageTemplate.getUrlMapping().getUrlPattern());
                urlTemplateBean.setMainTemplateName(onePageTemplate.getMainTemplate().getPartName());
                urlTemplateBeanList.add(urlTemplateBean);
            }
            return urlTemplateBeanList;
        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }

    public List<DataPluginBean> getActiveCustomDataPlugins(Long ulrMappingId) throws AppException {
        try {
            List<UrlMappingPlugin> urlMappingPlugins = dynamoService.findUrlMappingPluginByUrlMapping(ulrMappingId);
            List<CustomDataPlugin> customDataPlugins = dynamoService.getActiveCustomDataPlugins();
            List<DataPluginBean> dataPluginBeanList = new ArrayList<>(customDataPlugins.size());
            Map<Long, DataPlugin> dataPluginMap = urlMappingPlugins.stream().collect(Collectors.toMap(oneUrlMapping -> oneUrlMapping.getDataPlugin().getId(), oneUrlMapping -> oneUrlMapping.getDataPlugin()));

            for (CustomDataPlugin oneCustomDataPlugin : customDataPlugins) {
                DataPluginBean dataPluginBean = new DataPluginBean();
                BeanUtils.copyProperties(oneCustomDataPlugin, dataPluginBean);
                if (dataPluginMap.containsKey(oneCustomDataPlugin.getId())) {
                    dataPluginBean.setSelected(true);
                }
                dataPluginBeanList.add(dataPluginBean);
            }
            return dataPluginBeanList;

        } catch (DynamoException e) {
            throw new AppException(e);
        }
    }
}
