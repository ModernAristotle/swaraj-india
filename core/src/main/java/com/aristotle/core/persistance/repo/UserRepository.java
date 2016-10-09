package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    public abstract User getUserByMembershipNumber(String membershipNumber);

    public abstract User getUserByPassportNumber(String passportNumber);

    @Query("select distinct user from User user, Volunteer vl join vl.interests interests where  interests.id in ?1 and vl.userId=user.id and user.nri=true")
    Page<User> searchNriUserForVolunteerIntrest(List<Long> interestIds, Pageable pageable);

    @Query("select user from User user where user.nri=true")
    Page<User> searchNriOnly(Pageable pageable);

    @Query("select distinct user from User user, Volunteer vl join vl.interests interests where  interests.id in ?1 and vl.userId=user.id")
    Page<User> searchGlobalUserForVolunteerIntrest(List<Long> interestIds, Pageable pageable);

    @Query("select distinct user from User user, UserLocation ul, Volunteer vl join vl.interests interests where interests.id in ?2 and vl.userId=user.id and ul.userId=user.id and ul.locationId=?1")
    Page<User> searchLocationUserForVolunteerIntrest(Long locationId, List<Long> interestIds, Pageable pageable);

    @Query("select distinct user from User user, UserLocation ul where ul.locationId=?1 and ul.userId=user.id")
    Page<User> searchLocationUser(Long locationId, Pageable pageable);

//    @Query("select distinct user from User user where user.reindex=true")
//    Page<User> searchUserByReindex(Pageable pageable);

    @Query("select distinct user from User user where user.creatorId=?1")
    Page<User> searchUserByCreatorId(Long userId, Pageable pageable);

}
