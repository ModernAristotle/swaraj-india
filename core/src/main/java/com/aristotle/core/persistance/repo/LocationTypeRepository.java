package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationTypeRepository extends JpaRepository<LocationType, Long> {

    LocationType getLocationTypeByName(String name);
}
