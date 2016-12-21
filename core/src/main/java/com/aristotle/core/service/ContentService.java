package com.aristotle.core.service;

import com.aristotle.core.enums.ContentType;
import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;
import com.aristotle.core.persistance.UploadedFile;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Created by ravi on 12/11/16.
 */
public interface ContentService {

    Content saveContent(Content content) throws AppException;

    Content findContentById(Long contentId) throws AppException;

    List<Content> findContent(ContentType contentType, Pageable pageable) throws AppException;

    List<Content> findPublishedContent(ContentType contentType, Set<Long> locationIds, Pageable pageable) throws AppException;

    List<Content> findLatestPublishedNewsAndPressReleases(Set<Long> locationIds, Pageable pageable) throws AppException;

    UploadedFile saveUploadedFile(String remoteFileName, long size, String uploadSource);

    List<UploadedFile> getUploadedFiles(int pageNumber, int pageSize);
}
