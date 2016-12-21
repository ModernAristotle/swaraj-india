package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

    UploadedFile getUploadedFileByFileName(String fileName);
}
