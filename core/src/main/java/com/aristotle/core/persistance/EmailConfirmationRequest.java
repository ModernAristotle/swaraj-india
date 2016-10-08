package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email_confirmation_request")
@Getter
@Setter
@ToString(callSuper = true)
public class EmailConfirmationRequest extends BaseEntity {

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "token", nullable = false)
    private String token;

}
