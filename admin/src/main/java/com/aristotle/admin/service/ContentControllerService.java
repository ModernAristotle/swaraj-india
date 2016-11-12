package com.aristotle.admin.service;


import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentControllerService {

    @Autowired
    private ContentService contentService;

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
        }
        return dbContent;
    }
}
