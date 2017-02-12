package com.next.aristotle.web.service;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeControllerService {

    @Autowired
    private ContentService contentService;

    private final Pageable firstPage = new PageRequest(0, 5);


    public void generateHomePageModel(ModelAndView modelAndView) throws AppException {
        List<Content> latestContent = contentService.findLatestPublishedNewsAndPressReleases(null, firstPage);
        modelAndView.getModelMap().addAttribute("latestContent", latestContent);

        List<Content> latestThreeContent = new ArrayList<Content>(latestContent);
        if (latestContent.size() > 3) {
            latestThreeContent = latestThreeContent.subList(0, 3);
        }

        modelAndView.getModelMap().addAttribute("latestThreeContent", latestThreeContent);


    }
}
