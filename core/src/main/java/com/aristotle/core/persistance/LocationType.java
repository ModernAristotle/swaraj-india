package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "location_type")
@Getter
@Setter
@ToString(exclude = {"parentLocationType", "roles"}, callSuper = true)
public class LocationType extends BaseEntity {


    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_type_id")
    private LocationType parentLocationType;
    @Column(name = "parent_type_id", insertable = false, updatable = false)
    private Long parentTypeId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "location_type_role",
            joinColumns = {
                    @JoinColumn(name = "location_type_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            })
    private Set<Role> roles;

    @OneToMany(mappedBy = "parentLocationType", fetch = FetchType.EAGER)
    private List<LocationType> childLocationTypes;

}
