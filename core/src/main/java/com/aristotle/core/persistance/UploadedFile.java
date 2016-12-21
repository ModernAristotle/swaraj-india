package com.aristotle.core.persistance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "uploaded_files")
@Getter
@Setter
@ToString(callSuper = true)
public class UploadedFile extends BaseEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "upload_source")
    private String uploadSource;

}
