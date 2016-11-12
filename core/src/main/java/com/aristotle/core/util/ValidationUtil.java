package com.aristotle.core.util;

import java.util.Collection;
import java.util.Date;

public class ValidationUtil {

    public static void assertNotBlank(String data, String errorMessage) {
        if (data == null || data.trim().equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void assertNotNull(Object data, String errorMessage) {
        if (data == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void assertBefore(Date beforeDate, Date afterDate, String errorMessage) {
        if (beforeDate.after(afterDate)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void assertNotEmpty(Collection data, String errorMessage) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
