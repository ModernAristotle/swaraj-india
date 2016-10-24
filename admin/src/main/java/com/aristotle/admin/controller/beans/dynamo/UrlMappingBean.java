package com.aristotle.admin.controller.beans.dynamo;

import lombok.Data;

import java.util.Set;

@Data
public class UrlMappingBean {

    protected Long id;

    protected int ver;

    private String urlPattern;

    private Set<String> aliases;

    private boolean active;

    private boolean secured;

    private String forwardUrl;

    private Integer httpCacheTimeSeconds;

    private Long domainId;
}
