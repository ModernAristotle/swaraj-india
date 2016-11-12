package com.aristotle.core.persistance;

import com.aristotle.core.enums.ContentStatus;
import com.aristotle.core.enums.ContentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "content")
@Getter
@Setter
@ToString(exclude = {"locations"}, callSuper = true)
public class Content extends BaseEntity {

    @Column(name = "title", length = 255)
    private String title; // Title of the news/post item
    @Column(name = "url_title", length = 255)
    private String urlTitle; // Title of the news/post item
    @Column(name = "content_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType contentType;// Type of content like News etc
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;//content of news which can be html or plain text
    @Column(name = "image_url")
    private String imageUrl;// image preview url for this news item
    @Column(name = "author")
    private String author;// nullable, name of the person who wrote this article
    @Column(name = "publish_date")
    private Date publishDate;//Publish date of this item
    @Column(name = "global_allowed")
    private boolean global;//Whether this Content is available global or not
    @Column(name = "content_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus;
    @Column(name = "rejection_reason")
    private String rejectionReason;//

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "content_location",
            joinColumns = {
                    @JoinColumn(name = "content_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "location_id")
            })
    @JsonIgnore
    private Set<Location> locations;

}
