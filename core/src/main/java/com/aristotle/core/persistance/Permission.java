package com.aristotle.core.persistance;

import com.aristotle.core.enums.AppPermission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "permission")
@Getter
@Setter
@ToString(callSuper = true)
public class Permission extends BaseEntity {

    @Column(name = "permission_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppPermission permission;


}
