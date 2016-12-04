package com.aristotle.core.plugin.content;

import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Created by ravi on 04/12/16.
 */
@Component
public class ContentFieldSetting {

    public Collection<String> getFieldsToRemove() {
        return asList("contentStatus", "creatorId", "modifierId", "rejectionReason", "ver");
    }

    public Collection<String> getContentUpdatesFieldsToRemove() {
        return asList("contentStatus", "creatorId", "modifierId", "rejectionReason", "content", "ver", "dateCreated", "dateModified", "imageUrl", "global");
    }
}
