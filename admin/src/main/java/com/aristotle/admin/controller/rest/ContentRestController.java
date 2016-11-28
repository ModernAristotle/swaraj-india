package com.aristotle.admin.controller.rest;

import com.aristotle.admin.service.ContentControllerService;
import com.aristotle.core.enums.ContentType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
public class ContentRestController extends BaseRestController {

    @Autowired
    private ContentControllerService contentControllerService;

    @RequestMapping(value = "/service/s/news", method = RequestMethod.POST)
    public Content saveNews(HttpServletRequest httpServletRequest, @RequestBody Content content) throws AppException {
        content.setContentType(ContentType.News);
        return contentControllerService.saveContent(content);
    }

    @RequestMapping(value = "/service/s/news", method = RequestMethod.GET)
    public List<Content> getNews(HttpServletRequest httpServletRequest) throws AppException {
        return contentControllerService.getContentList(ContentType.News);
    }

    @RequestMapping(value = "/service/s/news/status", method = RequestMethod.POST)
    public Content updateNewsStatus(HttpServletRequest httpServletRequest, @RequestBody Content content) throws AppException {
        return contentControllerService.updateContentStatus(ContentType.News, content.getId(), content.getContentStatus());
    }

    @RequestMapping(value = "/service/s/blog", method = RequestMethod.POST)
    public Content saveBlog(HttpServletRequest httpServletRequest, @RequestBody Content content) throws AppException {
        content.setContentType(ContentType.Blog);
        return contentControllerService.saveContent(content);
    }

    @RequestMapping(value = "/service/s/blog", method = RequestMethod.GET)
    public List<Content> getBlogs(HttpServletRequest httpServletRequest) throws AppException {
        return contentControllerService.getContentList(ContentType.Blog);
    }

    @RequestMapping(value = "/service/s/blog/status", method = RequestMethod.POST)
    public Content updateBlogStatus(HttpServletRequest httpServletRequest, @RequestBody Content content) throws AppException {
        return contentControllerService.updateContentStatus(ContentType.Blog, content.getId(), content.getContentStatus());
    }

    @RequestMapping(value = "/service/s/pressrelease", method = RequestMethod.POST)
    public Content savePressRelease(HttpServletRequest httpServletRequest, @RequestBody Content content) throws AppException {
        content.setContentType(ContentType.PressRelease);
        return contentControllerService.saveContent(content);
    }

    @RequestMapping(value = "/service/s/pressrelease", method = RequestMethod.GET)
    public List<Content> getPressReleases(HttpServletRequest httpServletRequest) throws AppException {
        return contentControllerService.getContentList(ContentType.PressRelease);
    }

    @RequestMapping(value = "/service/s/pressrelease/status", method = RequestMethod.POST)
    public Content updatePressReleaseStatus(HttpServletRequest httpServletRequest, @RequestBody Content content) throws AppException {
        return contentControllerService.updateContentStatus(ContentType.PressRelease, content.getId(), content.getContentStatus());
    }
}
