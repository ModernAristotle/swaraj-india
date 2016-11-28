package com.aristotle.core.service.impl;

import com.aristotle.core.enums.ContentStatus;
import com.aristotle.core.enums.ContentType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.persistance.repo.ContentRepository;
import com.aristotle.core.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.aristotle.core.util.ValidationUtil.*;

/**
 * Created by ravi on 12/11/16.
 */
@Service
@Transactional(rollbackFor = {Throwable.class})
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Content saveContent(Content content) throws AppException {
        assertNotBlank(content.getTitle(), "Title can not be blank");
        assertNotBlank(content.getContent(), "Content can not be null");
        assertNotNull(content.getContentStatus(), "Content Status can not be null");
        assertNotNull(content.getContentType(), "`Content type can not be null");

        if (content.getContentStatus() == ContentStatus.Rejected) {
            assertNotBlank(content.getRejectionReason(), "Rejection reason can not be blank");
        }

        if (!content.isGlobal()) {
            assertNotEmpty(content.getLocations(), "Content either should be set global or must be linked to atleast one location");
        }
        generateUrlTitle(content);

        content = contentRepository.save(content);
        return content;
    }

    @Override
    public Content findContentById(Long contentId) throws AppException {
        return contentRepository.findOne(contentId);
    }

    @Override
    public List<Content> findContent(ContentType contentType) throws AppException {
        Pageable pageable = new PageRequest(0, 100);
        return contentRepository.getContent(contentType, pageable);
    }

    private void generateUrlTitle(Content content) {
        if (content.getUrlTitle() == null || content.getUrlTitle().trim().isEmpty()) {
            char[] titleChars = content.getTitle().toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char oneChar : titleChars) {
                if (Character.isLetterOrDigit(oneChar)) {
                    sb.append(oneChar);
                }
                if (Character.isSpaceChar(oneChar)) {
                    sb.append('_');
                }
            }
            content.setUrlTitle(sb.toString());
        }


    }
}
