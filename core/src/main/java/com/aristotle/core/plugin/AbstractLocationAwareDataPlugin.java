package com.aristotle.core.plugin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.next.dynamo.service.ui.UiTemplateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ravi on 13/11/16.
 * All Plugin should extend this class
 */
@Component
@Slf4j
public abstract class AbstractLocationAwareDataPlugin extends AbstractJacksonDataPlugin {

    @Autowired
    private UiTemplateManager uiTemplateManager;

    public final void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context) throws Exception {
        Set<Long> locationIds = new HashSet<Long>(2);
        Long domainLocation = uiTemplateManager.getDomainLocation(httpServletRequest);
        if (domainLocation != null) {
            locationIds.add(domainLocation);
        }
        // if user location not found then deriveit from user session/cookies
//        if (loggedInUserLocations == null || loggedInUserLocations.isEmpty()) {
//            loggedInUserLocations = httpSessionUtil.getLoggedInUserLocations(httpServletRequest);
//        }
        applyPlugin(httpServletRequest, httpServletResponse, mv, context, locationIds);
    }

    public abstract void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv, ObjectNode context, Set<Long> locationIds) throws Exception;

}
