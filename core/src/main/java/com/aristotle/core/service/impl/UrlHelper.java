package com.aristotle.core.service.impl;

import com.aristotle.core.persistance.Content;
import org.springframework.stereotype.Component;

/**
 * Created by ravi on 04/12/16.
 */
@Component
public class UrlHelper {

    public void generateContentUrl(Content content) {
        if (content.getUrlTitle() == null || content.getUrlTitle().trim().isEmpty()) {
            char[] titleChars = content.getTitle().toCharArray();
            StringBuilder sb = new StringBuilder();
            sb.append("/content/");
            sb.append(content.getContentType());
            sb.append("/");
            sb.append(content.getId());
            sb.append("/");
            for (char oneChar : titleChars) {
                if (Character.isLetterOrDigit(oneChar)) {
                    sb.append(oneChar);
                }
                if (Character.isSpaceChar(oneChar)) {
                    sb.append('_');
                }
            }
            content.setUrlTitle(sb.toString().toLowerCase());
        }

    }
}
