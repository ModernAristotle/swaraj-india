package com.aristotle.core.persistance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Version
    @Column(name = "ver")
    protected int ver;
    @Column(name = "date_created")
    @CreatedDate
    protected Date dateCreated;
    @Column(name = "date_modified")
    @LastModifiedDate
    protected Date dateModified;
    @Column(name = "creator_id")
    @CreatedBy
    protected Long creatorId;
    @Column(name = "modifier_id")
    @LastModifiedBy
    protected Long modifierId;

}
