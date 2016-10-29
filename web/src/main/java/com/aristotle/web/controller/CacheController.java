package com.aristotle.web.controller;

import com.github.jknack.handlebars.Template;
import com.next.dynamo.exception.DynamoException;
import com.next.dynamo.service.plugin.PluginManager;
import com.next.dynamo.service.ui.UiTemplateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CacheController {


    @Autowired
    private UiTemplateManager<Template> uiTemplateManager;

    @Autowired
    private PluginManager pluginManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = {"/admin/refresh/ui"}, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String serverSideHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView modelAndView) throws IOException, DynamoException {
        pluginManager.refresh();
        uiTemplateManager.refresh();
        return "Cache Refreshed";
    }
}
