package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email")
@Getter
@Setter
@ToString(exclude = {"phone", "user"}, callSuper = true)
public class Email extends BaseEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_up", nullable = false)
    private String emailUp;

    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "confirmation_date")
    protected Date confirmationDate;

    @Column(name = "news_letter", columnDefinition = "BIT(1) DEFAULT 1")
    private boolean newsLetter;

    @Column(name = "confirmation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfirmationType confirmationType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_id")
    private Phone phone;
    @Column(name = "phone_id", insertable = false, updatable = false)
    private Long phoneId;

    public enum ConfirmationType {
        CONFIRMED_FACEBOOK_ACCOUNT,
        CONFIRMED_GOOGLE_ACCOUNT,
        RECEIVED_EMAIL_FROM_ACCOUNT,
        UN_CONFIRNED,
        ADMIN_ENTERED,
        DONOR_ENTERED,
        VIA_EMAIL_VERIFICATION_FLOW
    }

}
