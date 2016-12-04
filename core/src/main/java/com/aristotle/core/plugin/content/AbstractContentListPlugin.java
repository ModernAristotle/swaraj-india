package com.aristotle.core.plugin.content;

import com.aristotle.core.enums.ContentType;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.plugin.AbstractLocationAwareDataPlugin;
import com.aristotle.core.service.ContentService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by ravi on 13/11/16.
 */
@Component
@Slf4j
public abstract class AbstractContentListPlugin extends AbstractLocationAwareDataPlugin {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentFieldSetting contentFieldSetting;

    @Override
    public void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context, Set<Long> locationIds) throws Exception {

        Pageable page = getPageRequest(httpServletRequest);
        List<Content> contentList = contentService.findPublishedContent(getContentType(), locationIds, page);
        ArrayNode newsList = convertArray(contentList);
        setPluginData(context, newsList);
    }

    protected abstract ContentType getContentType();

    protected Collection<String> getFieldsToRemove() {
        return contentFieldSetting.getFieldsToRemove();
    }
}
