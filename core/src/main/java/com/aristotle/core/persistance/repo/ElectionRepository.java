package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {

}