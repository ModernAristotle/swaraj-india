package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    List<UserLocation> getUserLocationByUserId(Long userId);

    @Query("select userLocation from UserLocation userLocation where userLocation.userId=?1 and userLocation.userLocationType=?2 and userLocation.location.locationType.name=?3 order by dateCreated desc")
    UserLocation getUserLocationByUserIdAndLocationTypesAndUserLocationType(Long userId, String userLocationType, String locationType);

}
