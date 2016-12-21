package com.aristotle.core.service.impl;

import com.aristotle.core.enums.ContentStatus;
import com.aristotle.core.enums.ContentType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.persistance.UploadedFile;
import com.aristotle.core.persistance.repo.ContentRepository;
import com.aristotle.core.persistance.repo.UploadedFileRepository;
import com.aristotle.core.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.aristotle.core.util.ValidationUtil.*;
import static java.util.Arrays.asList;

/**
 * Created by ravi on 12/11/16.
 */
@Service
@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UrlHelper urlHelper;

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    private List<ContentType> newsAndPressReleaseContentType = asList(ContentType.News, ContentType.PressRelease);

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

        content = contentRepository.save(content);
        urlHelper.generateContentUrl(content);

        return content;
    }

    @Override
    public Content findContentById(Long contentId) throws AppException {
        return contentRepository.findOne(contentId);
    }

    @Override
    public List<Content> findPublishedContent(ContentType contentType, Set<Long> locationIds, Pageable pageable) throws AppException {
        if (locationIds == null || locationIds.isEmpty()) {
            return contentRepository.getGlobalPublishedContent(contentType, pageable);
        }
        List<Content> contents = contentRepository.getLocationPublishedContent(contentType.name(), locationIds, pageable);
        if (contents.isEmpty() && pageable.getPageNumber() == 0) {
            contents = contentRepository.getGlobalPublishedContent(contentType, pageable);
        }
        return contents;
    }

    @Override
    public List<Content> findLatestPublishedNewsAndPressReleases(Set<Long> locationIds, Pageable pageable) throws AppException {
        log.info("newsAndPressReleaseContentType = " + newsAndPressReleaseContentType);
        if (locationIds == null || locationIds.isEmpty()) {
            return contentRepository.getLatestGlobalPublishedContent(pageable);
        }
        return contentRepository.getLatestLocationPublishedContent(locationIds, pageable);
    }

    @Override
    public UploadedFile saveUploadedFile(String remoteFileName, long fileSize, String uploadSource) {
        UploadedFile uploadedFile = uploadedFileRepository.getUploadedFileByFileName(remoteFileName);
        if (uploadedFile == null) {
            uploadedFile = new UploadedFile();
            uploadedFile.setFileName(remoteFileName);
        }
        uploadedFile.setSize(fileSize);
        uploadedFile.setFileType(getFileType(remoteFileName));
        uploadedFile.setUploadSource(uploadSource);
        uploadedFile = uploadedFileRepository.save(uploadedFile);
        return uploadedFile;
    }

    @Override
    public List<UploadedFile> getUploadedFiles(int pageNumber, int pageSize) {
        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
        return uploadedFileRepository.findAll(pageable).getContent();
    }

    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public List<Content> findContent(ContentType contentType, Pageable pageable) throws AppException {
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
