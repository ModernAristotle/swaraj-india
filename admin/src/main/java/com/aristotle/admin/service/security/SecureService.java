package com.aristotle.admin.service.security;

import com.aristotle.core.enums.AppPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecureService {
    AppPermission[] permissions();
}
