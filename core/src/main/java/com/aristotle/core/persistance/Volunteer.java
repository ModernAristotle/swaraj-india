package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "volunteer")
@Getter
@Setter
@ToString(exclude = {"interests", "user"}, callSuper = true)
public class Volunteer extends BaseEntity {

    @Column(name = "education")
    private String education;
    @Column(name = "professional_background", length = 512)
    private String professionalBackground;
    @Column(name = "domain_expertise")
    private String domainExpertise;
    @Column(name = "offences", length = 512)
    private String offences;
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    @Column(name = "emergency_contact_relation")
    private String emergencyContactRelation;
    @Column(name = "emergency_contact_no")
    private String emergencyContactNo;
    @Column(name = "emergency_contact_no_country_code")
    private String emergencyContactCountryCode;
    @Column(name = "emergency_contact_no_country_iso2")
    private String emergencyContactCountryIso2;
    @Column(name = "info_recorded_by")
    private String infoRecordedBy;
    @Column(name = "info_recorded_at")
    private String infoRecordedAt;
    @Column(name = "past_volunteer")
    private boolean pastVolunteer;
    @Column(name = "past_organisation", length = 128)
    private String pastOrganisation;
    @Column(name = "know_existing_member")
    private boolean knowExistingMember;
    @Column(name = "existing_member_name", length = 64)
    private String existingMemberName;
    @Column(name = "existing_member_email", length = 64)
    private String existingMemberEmail;
    @Column(name = "existing_member_mobile", length = 16)
    private String existingMemberMobile;
    @Column(name = "existing_member_country_code", length = 8)
    private String existingMemberCountryCode;
    @Column(name = "existing_member_country_iso2", length = 8)
    private String existingMemberCountryIso2;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "volunteer_interests",
            joinColumns = {
                    @JoinColumn(name = "volunteer_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "interest_id")
            })
    Set<Interest> interests;

}
