package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    public abstract Volunteer getVolunteersByUserId(Long userId);

}