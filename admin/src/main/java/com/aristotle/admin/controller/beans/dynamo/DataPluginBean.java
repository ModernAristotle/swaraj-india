package com.aristotle.admin.controller.beans.dynamo;

import lombok.Data;

@Data
public class DataPluginBean {

    protected Long id;
    protected int ver;
    private boolean disabled;
    private String pluginName;
    private String type;
    private String content;
    private String fullClassName;
    private boolean selected;

}
