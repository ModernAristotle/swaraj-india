package com.aristotle.admin.controller.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserRegisterBean extends BaseBean {

    private String name;
    private String fatherName;
    private String motherName;
    private String address;
    private String password;
    private String gender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private boolean nri;
    private Long nriCountryId;
    private Long nriCountryRegionId;
    private Long nriCountryRegionAreaId;

    private Long stateLivingId;
    private Long districtLivingId;
    private Long assemblyConstituencyLivingId;
    private Long parliamentConstituencyLivingId;
    private Long stateVotingId;
    private Long districtVotingId;
    private Long assemblyConstituencyVotingId;
    private Long parliamentConstituencyVotingId;
    private String emailId;

    private Long[] interests;

    private boolean member;

    private boolean volunteer;

    private String passportNumber;

    private String identityNumber;

    private String identityType;

    private String education;
    private String professionalBackground;
    private String domainExpertise;
    private String offences;
    private String emergencyContactName;
    private String emergencyContactRelation;
    private String emergencyContactNo;

    private boolean pastVolunteer;
    private String pastOrganisation;
    private boolean knowExistingMember;
    private String existingMemberName;
    private String existingMemberEmail;
    private String existingMemberMobile;
    private String existingMemberCountryCode;

    private String mobileNumber;
    private String countryCode;

    private String nriMobileNumber;
    private String nriCountryCode;
}
