package com.aristotle.admin.dummy.impl;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.service.EmailManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DummyEmailManagerImpl implements EmailManager {
    List<Map<String, String>> emailSent = new ArrayList<>();

    @Override
    public void sendEmail(String toEmail, String fromName, String fromEmail, String subject, String body, String htmlContent) throws AppException {
        System.out.println("This is a test implementation of Email Manager , no Email will be sent");
        System.out.println("From Email : " + fromEmail);
        System.out.println("From Name  : " + fromName);
        System.out.println("To Email   : " + toEmail);
        System.out.println("subject    : " + subject);
        System.out.println("body       : " + body);
        System.out.println("htmlContent: " + htmlContent);
        Map<String, String> email = new HashMap<>();
        email.put("toEmail", toEmail);
        email.put("fromName", fromName);
        email.put("subject", subject);
        email.put("body", body);
        email.put("htmlContent", htmlContent);
        emailSent.add(email);
    }

    public void clear() {
        emailSent.clear();
    }

}
