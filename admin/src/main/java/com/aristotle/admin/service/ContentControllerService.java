package com.aristotle.admin.service;


import com.aristotle.admin.service.security.SecureService;
import com.aristotle.core.enums.ContentStatus;
import com.aristotle.core.enums.ContentType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.aristotle.core.enums.AppPermission.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentControllerService {

    @Autowired
    private ContentService contentService;

    @SecureService(permissions = {NEWS_REPORTER, NEWS_EDITOR})
    public Content saveContent(Content content) throws AppException {

        Content dbContent = getOrCreateContent(content);
        dbContent = contentService.saveContent(dbContent);
        return dbContent;
    }

    private Content getOrCreateContent(Content content) throws AppException {
        Content dbContent = null;
        if (content.getId() != null) {
            dbContent = contentService.findContentById(content.getId());
            if (dbContent == null) {
                throw new AppException("No such content exists [electionid : " + content.getId() + "]");
            }
            dbContent.setContent(content.getContent());
            dbContent.setContentStatus(content.getContentStatus());
            dbContent.setAuthor(content.getAuthor());
            dbContent.setContentType(content.getContentType());
            dbContent.setTitle(content.getTitle());
            dbContent.setImageUrl(content.getImageUrl());
            dbContent.setRejectionReason(content.getRejectionReason());
        } else {
            dbContent = content;
            dbContent.setContentStatus(ContentStatus.Pending);
            dbContent.setGlobal(true);
        }
        return dbContent;
    }

    @SecureService(permissions = {NEWS_REPORTER, NEWS_EDITOR})
    public List<Content> getNewstList() throws AppException {
        return contentService.findContent(ContentType.News);
    }

    @SecureService(permissions = {NEWS_EDITOR})
    public Content publishNews(Long newsId) throws AppException {
        return updateContentStatus(ContentType.News, newsId, ContentStatus.Published);
    }

    @SecureService(permissions = {BLOG_REPORTER, BLOG_EDITOR})
    public List<Content> getBlogList() throws AppException {
        return contentService.findContent(ContentType.Blog);
    }

    @SecureService(permissions = {BLOG_EDITOR})
    public Content publishBlog(Long blogId) throws AppException {
        return updateContentStatus(ContentType.Blog, blogId, ContentStatus.Published);
    }

    @SecureService(permissions = {PRESS_RELEASE_REPORTER, PRESS_RELEASE_EDITOR})
    public List<Content> getPressReleaseList() throws AppException {
        return contentService.findContent(ContentType.PressRelease);
    }

    @SecureService(permissions = {PRESS_RELEASE_EDITOR})
    public Content publishPressRelease(Long contentId) throws AppException {
        return updateContentStatus(ContentType.PressRelease, contentId, ContentStatus.Published);
    }

    private Content updateContentStatus(ContentType contentType, Long contentId, ContentStatus contentStatus) throws AppException {
        Content dbContent = contentService.findContentById(contentId);
        if (dbContent == null) {
            throw new IllegalArgumentException("No Such content found [id=" + contentId + "]");
        }
        if (dbContent.getContentType() != contentType) {
            throw new IllegalArgumentException("No Such content found [id=" + contentId + ", contentType= " + contentType + "]");
        }
        dbContent.setContentStatus(contentStatus);
        dbContent = contentService.saveContent(dbContent);
        return dbContent;
    }
}
