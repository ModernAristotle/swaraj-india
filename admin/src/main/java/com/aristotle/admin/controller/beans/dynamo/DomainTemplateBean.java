package com.aristotle.admin.controller.beans.dynamo;

import lombok.Data;

@Data
public class DomainTemplateBean {

    protected Long id;

    protected int ver;

    private String name;

    private Long domainId;

    private boolean active;

    private String gitRepository;

    private String gitBranch;
}
