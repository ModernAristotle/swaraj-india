package com.aristotle.core.plugin.content;

import com.aristotle.core.enums.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by ravi on 13/11/16.
 */
@Component
@Slf4j
public class NewsListPlugin extends AbstractContentListPlugin {

    @Override
    protected ContentType getContentType() {
        return ContentType.News;
    }
}
