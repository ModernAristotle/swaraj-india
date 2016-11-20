package com.aristotle.core.plugin;


import com.next.dynamo.service.plugin.AbstractDataPlugin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SingleContentPlugin extends AbstractDataPlugin {
    @Override
    public void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv) {

    }
}
