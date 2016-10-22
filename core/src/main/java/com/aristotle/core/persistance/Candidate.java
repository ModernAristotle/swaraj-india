package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@ToString(exclude = {"location", "election"}, callSuper = true)
public class Candidate extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;//content of news which can be templates or plain text

    @Column(name = "candidate_fb_page_id")
    private String candidateFbPageId;

    @Column(name = "twitter_id")
    private String twitterId;

    @Column(name = "landing_page_url_id")
    private String landingPageUrlId;

    @Column(name = "landing_page_small_url")
    private String landingPageSmallUrl;

    @Column(name = "landing_page_full_url", length = 512)
    private String landingPageFullUrl;

    @Column(name = "donation_page_full_url")
    private String donationPageFullUrl;

    @Column(name = "donate_page_url_id")
    private String donatePageUrlId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_url_64")
    private String imageUrl64;

    @Column(name = "image_url_32")
    private String imageUrl32;

    @Column(name = "lattitude")
    private double lattitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "map_default_depth")
    private int depth;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;
    @Column(name = "election_id", insertable = false, updatable = false)
    private Long electionId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "location_id", insertable = false, updatable = false)
    private Long locationId;

}
