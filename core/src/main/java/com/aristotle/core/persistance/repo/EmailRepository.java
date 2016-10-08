package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {

    public abstract Email getEmailByEmailUp(String email);

    public abstract List<Email> getEmailsByUserId(Long userId);

}