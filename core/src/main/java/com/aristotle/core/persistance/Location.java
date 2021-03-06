package com.aristotle.core.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Getter
@Setter
@ToString(exclude = {"locationType", "parentLocation"}, callSuper = true)
public class Location extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_up", nullable = false)
    @JsonIgnore
    private String nameUp;

    @Column(name = "isd_code")
    private String isdCode;

    @Column(name = "state_code")
    private String stateCode;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_type_id")
    @JsonIgnore
    private LocationType locationType;
    @Column(name = "location_type_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long locationTypeId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Location parentLocation;
    @Column(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long parentLocationId;

}
