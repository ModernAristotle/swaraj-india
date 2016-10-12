package com.aristotle.core.enums;

public enum AppPermission {
    ADMIN_CAMPAIGN_FB,
    ADMIN_CAMPAIGN_TWITTER,
    CREATE_NEWS,
    UPDATE_NEWS,
    DELETE_NEWS,
    APPROVE_NEWS,
    CREATE_EVENT,
    UPDATE_EVENT,
    DELETE_EVENT,
    APPROVE_EVENT,
    CREATE_BLOG,
    UPDATE_BLOG,
    DELETE_BLOG,
    APPROVE_BLOG,
    CREATE_POLL,
    UPDATE_POLL,
    DELETE_POLL,
    APPROVE_POLL,
    ADMIN_SMS,
    ADMIN_EMAIL,
    ADMIN_MOBILE_GROUP,
    ADMIN_DONATION,
    ADMIN_CANDIDATE,

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
