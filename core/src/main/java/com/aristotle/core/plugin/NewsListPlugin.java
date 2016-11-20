package com.aristotle.core.plugin;

import com.next.dynamo.service.plugin.AbstractDataPlugin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ravi on 13/11/16.
 */
@Component
public class NewsListPlugin extends AbstractDataPlugin {


    @Override
    public void applyPlugin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ModelAndView mv) {

    }
}
