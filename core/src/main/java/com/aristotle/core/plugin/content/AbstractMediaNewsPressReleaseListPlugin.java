package com.aristotle.core.plugin.content;

import com.aristotle.core.persistance.Content;
import com.aristotle.core.plugin.AbstractLocationAwareDataPlugin;
import com.aristotle.core.service.ContentService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.jsoup.Jsoup;
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
 * Created by ravi on 04/12/16.
 */
@Component
public class AbstractMediaNewsPressReleaseListPlugin extends AbstractLocationAwareDataPlugin {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentFieldSetting contentFieldSetting;


    @Override
    public void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context, Set<Long> locationIds) throws Exception {
        Pageable firstPage = getPageRequest(httpServletRequest);
        List<Content> latestContent = contentService.findLatestPublishedNewsAndPressReleases(locationIds, firstPage);
        ArrayNode arrayNode = convertArray(latestContent);
        int size = arrayNode.size();
        for (int i = 0; i < size; i++) {
            ObjectNode oneObjectNode = (ObjectNode) arrayNode.get(i);
            String html = oneObjectNode.get("content").asText();
            String withOutHtmlTags = Jsoup.parse(html).text();
            if (withOutHtmlTags.length() > 150) {
                withOutHtmlTags = withOutHtmlTags.substring(0, 149) + "...";
            }
            oneObjectNode.set("contentSmall", new TextNode(withOutHtmlTags));

        }
        setPluginData(context, arrayNode);
    }

    @Override
    protected Collection<String> getFieldsToRemove() {
        return contentFieldSetting.getFieldsToRemove();
    }
}
