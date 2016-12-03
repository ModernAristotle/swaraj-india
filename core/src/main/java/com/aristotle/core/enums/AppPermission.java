package com.aristotle.core.enums;

public enum AppPermission {
    NEWS_REPORTER,
    NEWS_EDITOR,

    BLOG_REPORTER,
    BLOG_EDITOR,

    PRESS_RELEASE_REPORTER,
    PRESS_RELEASE_EDITOR,

    POLL_REPORTER,
    POLL_EDITOR,

    EVENT_EDITOR,
    EVENT_REPORTER,

    ADMIN_CAMPAIGN_FB,
    ADMIN_CAMPAIGN_TWITTER,


    ADMIN_SMS,
    ADMIN_EMAIL,
    ADMIN_MOBILE_GROUP,
    ADMIN_DONATION,
    ADMIN_CANDIDATE,

    ADMIN_ELECTION,

    ADD_MEMBER,
    UPDATE_MEMBER,
    SEARCH_MEMBER,
    UPDATE_GLOBAL_MEMBER,
    VIEW_MEMBER,
    EDIT_USER_ROLES,


    TREASURY,
    EDIT_OFFICE_ADDRESS,

    ADMIN_GLOBAL_CAMPAIGN,

    WEB_ADMIN_DRAFT,
    WEB_ADMIN,
    EDIT_TEAM,
    CALL_CAMPAIGN_ADMIN,
    USER_DATA_UPLOAD;

    private AppPermission() {
        // TODO Auto-generated constructor stub
    }

    private AppPermission(String name, String description, boolean addStateRoles, boolean addDistrictRoles, boolean addAcRoles, boolean addPcRoles, boolean addCountryRole,
                          boolean addCountryRegionRole, boolean addCuntryRegionAreaRole) {

    }
}
