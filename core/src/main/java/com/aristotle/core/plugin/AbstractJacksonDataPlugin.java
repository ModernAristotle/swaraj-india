package com.aristotle.core.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.next.dynamo.service.plugin.AbstractDataPlugin;
import com.next.dynamo.service.plugin.HttpParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * Created by ravi on 13/11/16.
 * All Plugin should extend this class
 */
@Component
@Slf4j
public abstract class AbstractJacksonDataPlugin extends AbstractDataPlugin {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ContextSetting contextSetting;

    private ObjectNode emptyObjectNode;
    private ArrayNode emptyArrayNode;

    @PostConstruct
    public void init() {
        emptyObjectNode = objectMapper.createObjectNode();
        emptyArrayNode = objectMapper.createArrayNode();
    }

    protected void setPluginData(ObjectNode context, JsonNode jsonNode) {
        context.set(getName(), jsonNode);
    }


    protected ArrayNode convertArray(Collection collection) {
        if (collection == null) {
            return emptyArrayNode;
        }
        ArrayNode arrayNode = objectMapper.valueToTree(collection);
        log.info("Date Format : " + objectMapper.getDateFormat().toString());
        if (getFieldsToRemove() != null) {
            arrayNode.forEach(a -> ((ObjectNode) a).remove(getFieldsToRemove()));
        }
        return arrayNode;
    }

    protected ObjectNode convertObject(Object object) {
        if (object == null) {
            return emptyObjectNode;
        }
        ObjectNode objectNode = objectMapper.valueToTree(object);
        if (getFieldsToRemove() != null) {
            objectNode.remove(getFieldsToRemove());
        }
        return objectNode;
    }

    public final void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv) {
        ObjectNode context = (ObjectNode) mv.getModel().get(contextSetting.getContextFieldName());
        try {
            applyPlugin(httpServletRequest, httpServletResponse, mv, context);
        } catch (Exception e) {
            log.error("Unable to execute plugin", e);
        }
    }

    public abstract void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context) throws Exception;

    protected abstract Collection<String> getFieldsToRemove();

    protected Long getContentId(HttpServletRequest httpServletRequest) {
        Map<String, String> pathParams = (Map<String, String>) httpServletRequest.getAttribute(HttpParameters.PATH_PARAMETER_PARAM);
        String newsIdStr = pathParams.get("id");
        if (newsIdStr == null) {
            newsIdStr = httpServletRequest.getParameter("id");
        }
        try {
            return Long.parseLong(newsIdStr);
        } catch (Exception ex) {
            return null;
        }
    }

}
