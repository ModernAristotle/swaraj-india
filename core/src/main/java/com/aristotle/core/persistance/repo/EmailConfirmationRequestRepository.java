package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.EmailConfirmationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationRequestRepository extends JpaRepository<EmailConfirmationRequest, Long> {

    EmailConfirmationRequest getEmailConfirmationRequestByToken(String token);

    EmailConfirmationRequest getEmailConfirmationRequestByEmail(String email);
}
