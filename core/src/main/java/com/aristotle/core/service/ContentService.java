package com.aristotle.core.service;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Content;

/**
 * Created by ravi on 12/11/16.
 */
public interface ContentService {

    Content saveContent(Content content) throws AppException;

    Content findContentById(Long contentId) throws AppException;
}
