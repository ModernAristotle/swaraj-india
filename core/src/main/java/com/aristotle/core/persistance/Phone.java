package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Getter
@Setter
@ToString(exclude = {"phone", "user"}, callSuper = true)
public class Phone extends BaseEntity {

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "phone_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @Column(name = "confirmed", columnDefinition = "BIT(1) DEFAULT 0")
    private boolean confirmed;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    public enum PhoneType {
        MOBILE,
        LANDLINE,
        NRI_MOBILE
    }

}
