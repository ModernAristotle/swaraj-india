package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "interest_group")
@Getter
@Setter
@ToString(exclude = {"interests"}, callSuper = true)
public class InterestGroup extends BaseEntity {

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "interestGroup", fetch = FetchType.EAGER)
    private List<Interest> interests;

}
