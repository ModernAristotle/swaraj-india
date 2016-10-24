package com.aristotle.admin.controller.beans.dynamo;

import lombok.Data;

import java.util.Set;

@Data
public class DomainBean {

    protected Long id;

    protected int ver;

    private String name;

    private Set<String> aliases;

    private boolean active;

    private String setting;

    private Long extendedDomainId;
}
