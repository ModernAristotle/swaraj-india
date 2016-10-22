package com.aristotle.core.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "elections")
@Getter
@Setter
@ToString(exclude = {"location", "locationType"}, callSuper = true)
public class Election extends BaseEntity {

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "title", length = 512)
    private String title;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "candidateure_filing_date")
    private Date candidatureFilingDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;
    @Column(name = "location_id", insertable = false, updatable = false)
    private Long locationId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_type_id")
    @JsonIgnore
    private LocationType locationType;
    @Column(name = "location_type_id", insertable = false, updatable = false)
    private Long locationTypeId;
}
