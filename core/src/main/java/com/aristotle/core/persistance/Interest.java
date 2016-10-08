package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "interests")
@Getter
@Setter
@ToString(exclude = {"interestGroup"}, callSuper = true)
public class Interest extends BaseEntity {


    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_group_id")
    private InterestGroup interestGroup;
    @Column(name = "interest_group_id", insertable = false, updatable = false)
    private Long interestGroupId;

}
