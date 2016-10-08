package com.aristotle.core.persistance;

import com.aristotle.core.enums.CreationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"referenceUser", "membershipConfirmedBy"}, callSuper = true)
public class User extends BaseEntity {

    @Column(name = "ivr_state")
    private String ivrState;

    @Column(name = "ivr_district")
    private String ivrDistrict;

    @Column(name = "sms_msg")
    private String smsMessage;

    @Column(name = "membership_no")
    private String membershipNumber;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "reference_mobile_number")
    private String referenceMobileNumber;

    @Column(name = "address", length = 512)
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "creation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreationType creationType;


    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "nri")
    private boolean nri;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_user_id")
    private User referenceUser;
    @Column(name = "reference_user_id", insertable = false, updatable = false)
    private Long referenceUserId;

    @Column(name = "profile", columnDefinition = "LONGTEXT")
    private String profile;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_all_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            })
    Set<Role> allRoles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_location_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "location_role_id")
            })
    Set<LocationRole> locationRoles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_interests",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "interest_id")
            })
    Set<Interest> interests;

    @Column(name = "allow_tweets", nullable = false)
    private boolean allowTweets;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "super_admin", nullable = false)
    private boolean superAdmin;

    @Column(name = "member", nullable = false)
    private boolean member;

    @Column(name = "donor", nullable = false)
    private boolean donor;

    @Column(name = "volunteer", nullable = false)
    private boolean volunteer;

    @Column(name = "reindex", nullable = true)
    private boolean reindex;

    @Column(name = "membership_status")
    private String membershipStatus;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "identity_type")
    private String identityType;

    @Column(name = "voter_id")
    private String voterId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_confirmed_by")
    private User membershipConfirmedBy;
    @Column(name = "membership_confirmed_by", insertable = false, updatable = false)
    private Long membershipConfirmedById;

}
