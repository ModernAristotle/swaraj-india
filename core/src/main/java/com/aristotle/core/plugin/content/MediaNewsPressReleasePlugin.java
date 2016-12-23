package com.aristotle.core.plugin.content;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ravi on 04/12/16.
 */
@Component
public class MediaNewsPressReleasePlugin extends AbstractMediaNewsPressReleaseListPlugin {

    private final Pageable firstPage = new PageRequest(0, 3);

    protected Pageable getPageRequest(HttpServletRequest httpServletRequest) {
        return firstPage;
    }

}
