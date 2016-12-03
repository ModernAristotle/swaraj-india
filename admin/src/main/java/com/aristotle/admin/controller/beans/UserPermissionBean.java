package com.aristotle.admin.controller.beans;

import com.aristotle.core.enums.AppPermission;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserPermissionBean {

    private Set<AppPermission> appPermissions;
    private boolean superAdmin;

    private boolean newsEditor;
    private boolean newsReporter;

    private boolean blogEditor;
    private boolean blogReporter;

    private boolean pressReleaseEditor;
    private boolean pressReleaseReporter;

    private boolean pollEditor;
    private boolean pollReporter;

    private boolean eventEditor;
    private boolean eventReporter;

    private boolean facebook;
    private boolean twitter;
    private boolean sms;
    private boolean email;
    private boolean call;
    private boolean mobileGroups;
    private boolean donation;
    private boolean candidates;


    private boolean domain;
    private boolean urlMapping;
    private boolean templates;
    private boolean dataPlugins;
    private boolean urltemplates;
    private boolean htmlpart;

    private boolean userRoles;
    private boolean searchVolunteer;
    private boolean editUser;


    private boolean election;

    public boolean isContent() {
        return isNews() || isBlogs() || isPoll() || isEvents() || isPressRelease();
    }

    public boolean isCampaign() {
        return superAdmin || facebook || twitter || sms || email || call || mobileGroups || donation || candidates;
    }

    public boolean isDeveloper() {
        return superAdmin || domain || urlMapping || templates || dataPlugins || urltemplates || htmlpart;
    }

    public boolean isMembers() {
        return superAdmin || userRoles || searchVolunteer || editUser;
    }

    public boolean isPickLocation() {
        return isContent() || isCampaign() || isDeveloper() || isMembers();
    }

    public boolean isElectionAdmin() {
        return superAdmin || election;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public boolean isNews() {
        return superAdmin || newsEditor || newsReporter;
    }

    public boolean isBlogs() {
        return superAdmin || blogEditor || blogReporter;
    }

    public boolean isPoll() {
        return superAdmin || pollEditor || pollReporter;
    }

    public boolean isEvents() {
        return superAdmin || eventEditor || eventReporter;
    }

    public boolean isPressRelease() {
        return superAdmin || pressReleaseEditor || pressReleaseReporter;
    }

    public boolean isFacebook() {
        return superAdmin || facebook;
    }

    public boolean isTwitter() {
        return superAdmin || twitter;
    }

    public boolean isSms() {
        return superAdmin || sms;
    }

    public boolean isEmail() {
        return superAdmin || email;
    }

    public boolean isCall() {
        return superAdmin || call;
    }

    public boolean isMobileGroups() {
        return superAdmin || mobileGroups;
    }

    public boolean isDonation() {
        return superAdmin || donation;
    }

    public boolean isCandidates() {
        return superAdmin || candidates;
    }

    public boolean isDomain() {
        return superAdmin || domain;
    }

    public boolean isUrlMapping() {
        return superAdmin || urlMapping;
    }

    public boolean isTemplates() {
        return superAdmin || templates;
    }

    public boolean isDataPlugins() {
        return superAdmin || dataPlugins;
    }

    public boolean isUrltemplates() {
        return superAdmin || urltemplates;
    }

    public boolean isHtmlpart() {
        return superAdmin || htmlpart;
    }

    public boolean isUserRoles() {
        return superAdmin || userRoles;
    }

    public boolean isSearchVolunteer() {
        return superAdmin || searchVolunteer;
    }

    public boolean isEditUser() {
        return superAdmin || editUser;
    }

    public boolean isElection() {
        return superAdmin || election;
    }



}
