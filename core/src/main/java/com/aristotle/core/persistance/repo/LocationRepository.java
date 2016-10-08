package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location getLocationByNameUpAndLocationTypeId(String name, Long locationTypeId);

    Location getLocationByIsdCode(String isdCode);

    @Query("select distinct loc from Location loc, User user join user.locationRoles lr where  user.id=?1 and lr.locationId=loc.id")
    List<Location> getAdminLocationsOfUser(Long userId);

    List<Location> getLocationsByLocationTypeIdOrderByNameAsc(Long locationTypeId);

    List<Location> getLocationsByLocationTypeIdAndParentLocationIdOrderByNameAsc(Long locationTypeId, long parentLocationId);

    List<Location> getLocationsByParentLocationId(long parentLocationId);

}
