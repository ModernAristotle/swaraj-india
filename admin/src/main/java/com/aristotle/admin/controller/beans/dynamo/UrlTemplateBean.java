package com.aristotle.admin.controller.beans.dynamo;

import lombok.Data;

@Data
public class UrlTemplateBean {

    protected Long id;

    protected int ver;

    private Long domainTemplateId;

    private String gitFilePath;

    private String urlPattern;

    private Long urlMappingId;

    private Long mainTemplateId;

    private String mainTemplateName;

}
