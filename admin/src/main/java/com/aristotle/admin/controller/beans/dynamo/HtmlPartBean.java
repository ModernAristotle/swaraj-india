package com.aristotle.admin.controller.beans.dynamo;

import lombok.Data;

@Data
public class HtmlPartBean {

    protected Long id;

    protected int ver;

    private String partName;

    private String partType;

    private Long domainTemplateId;

    private String gitFilePath;
}
