package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "login_account")
@Getter
@Setter
@ToString(exclude = {"user"}, callSuper = true)
public class LoginAccount extends BaseEntity {

    @Column(name = "user_name", nullable = false, length = 256)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

}
