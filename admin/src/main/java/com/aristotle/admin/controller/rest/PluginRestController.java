package com.aristotle.admin.controller.rest;

import com.aristotle.core.exception.AppException;
import com.next.dynamo.exception.DynamoException;
import com.next.dynamo.service.plugin.PluginManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by ravi on 13/11/16.
 */
@RestController
@Slf4j
public class PluginRestController extends BaseRestController {

    @Autowired
    private PluginManager pluginManager;

    @RequestMapping(value = "/service/s/refresh/plugin", method = GET)
    @ResponseBody
    public String refreshPlugins(HttpServletRequest httpServletRequest) throws AppException {
        try {
            pluginManager.updateDbWithAllPlugins();
        } catch (DynamoException e) {
            log.error("Unable to refresh plugins", e);
            return "Unable to refresh plugins : " + e.getMessage();
        }
        return "Plugin Refreshed";
    }

}
