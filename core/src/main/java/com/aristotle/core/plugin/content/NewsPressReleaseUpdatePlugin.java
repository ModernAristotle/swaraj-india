package com.aristotle.core.plugin.content;

import com.aristotle.core.persistance.Content;
import com.aristotle.core.plugin.AbstractLocationAwareDataPlugin;
import com.aristotle.core.service.ContentService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by ravi on 04/12/16.
 */
@Component
public class NewsPressReleaseUpdatePlugin extends AbstractLocationAwareDataPlugin {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentFieldSetting contentFieldSetting;

    private final Pageable firstPage = new PageRequest(0, 5);


    @Override
    public void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context, Set<Long> locationIds) throws Exception {

        List<Content> latestContent = contentService.findLatestPublishedNewsAndPressReleases(locationIds, firstPage);
        ArrayNode arrayNode = convertArray(latestContent);
        setPluginData(context, arrayNode);
    }

    @Override
    protected Collection<String> getFieldsToRemove() {
        return contentFieldSetting.getContentUpdatesFieldsToRemove();
    }
}
