package com.aristotle.admin.controller.beans;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPermissionBean {

    private boolean superAdmin;
    private boolean news;
    private boolean blogs;
    private boolean poll;
    private boolean events;


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
        return superAdmin || news || twitter || poll || events;
    }

    public boolean isCampaign() {
        return superAdmin || facebook || blogs || sms || email || call || mobileGroups || donation || candidates;
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
        return superAdmin || news;
    }

    public boolean isBlogs() {
        return superAdmin || blogs;
    }

    public boolean isPoll() {
        return superAdmin || poll;
    }

    public boolean isEvents() {
        return superAdmin || events;
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
