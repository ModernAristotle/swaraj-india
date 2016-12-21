package com.aristotle.core.plugin.content;

import com.aristotle.core.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ravi on 04/12/16.
 */
@Component
public class MediaNewsPressReleasePlugin extends MediaNewsPressReleaseListPlugin {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentFieldSetting contentFieldSetting;

    private final Pageable firstPage = new PageRequest(0, 3);

    protected Pageable getPageRequest(HttpServletRequest httpServletRequest) {
        return firstPage;
    }

}
