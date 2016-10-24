package com.aristotle.admin.service;


import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Election;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.LocationType;
import com.aristotle.core.service.ElectionService;
import com.aristotle.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ElectionControllerService {

    @Autowired
    private ElectionService electionService;
    @Autowired
    private LocationService locationService;

    public Election saveElection(Election election) throws AppException {

        Location electionLocation = getLocation(election.getLocationId());
        LocationType locationType = getLocationType(election.getLocationTypeId());
        Election dbElection = getOrCreateElection(election);
        dbElection.setLocation(electionLocation);
        dbElection.setLocationType(locationType);

        dbElection = electionService.saveElection(dbElection);
        return dbElection;
    }

    private Location getLocation(Long electionId) throws AppException {
        Location electionLocation = locationService.findLocationById(electionId);
        if (electionLocation == null && electionId != null) {
            throw new IllegalArgumentException("Incorrect Location id [locationId : " + electionId + "]");
        }
        return electionLocation;
    }

    private LocationType getLocationType(Long electionTypeId) throws AppException {
        LocationType electionLocationType = locationService.findLocationTypeById(electionTypeId);
        if (electionLocationType == null && electionTypeId != null) {
            throw new IllegalArgumentException("Incorrect Location Type id [locationTypeId : " + electionTypeId + "]");
        }
        return electionLocationType;
    }

    private Election getOrCreateElection(Election election) throws AppException {
        Election dbElection = null;
        if (election.getId() != null) {
            dbElection = electionService.findElectionById(election.getId());
            if (dbElection == null) {
                throw new AppException("No such election exists [electionid : " + election.getId() + "]");
            }
            dbElection.setCandidatureFilingDate(election.getCandidatureFilingDate());
            dbElection.setDescription(election.getDescription());
            dbElection.setEndDate(election.getEndDate());
            dbElection.setStartDate(election.getStartDate());
            dbElection.setTitle(election.getTitle());
            dbElection.setLocationId(election.getLocationId());
            dbElection.setLocationTypeId(election.getLocationTypeId());
        } else {
            dbElection = election;
        }
        return dbElection;
    }
}
