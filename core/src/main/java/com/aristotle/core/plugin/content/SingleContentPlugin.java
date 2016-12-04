package com.aristotle.core.plugin.content;


import com.aristotle.core.persistance.Content;
import com.aristotle.core.plugin.AbstractJacksonDataPlugin;
import com.aristotle.core.service.ContentService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Component
public class SingleContentPlugin extends AbstractJacksonDataPlugin {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentFieldSetting contentFieldSetting;

    @Override
    public void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context) throws Exception {
        Long contentId = getContentId(httpServletRequest);
        Content content = contentService.findContentById(contentId);
        ObjectNode objectNode = convertObject(content);
        setPluginData(context, objectNode);
        context.set("pageTitle", new TextNode(content.getTitle()));
    }

    @Override
    protected Collection<String> getFieldsToRemove() {
        return contentFieldSetting.getFieldsToRemove();
    }
}
