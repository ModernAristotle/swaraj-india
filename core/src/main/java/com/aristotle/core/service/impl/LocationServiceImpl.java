package com.aristotle.core.service.impl;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.LocationType;
import com.aristotle.core.persistance.repo.LocationRepository;
import com.aristotle.core.persistance.repo.LocationTypeRepository;
import com.aristotle.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackFor = {Throwable.class})
public class LocationServiceImpl implements LocationService {

    public static final String COUNTRY_LOCATION_TYPE = "Country";
    public static final String STATE_LOCATION_TYPE = "State";
    public static final String PARLIAMENT_CONSTITUENCY_LOCATION_TYPE = "ParliamentConstituency";
    public static final String DISTRICT_LOCATION_TYPE = "District";
    public static final String ASSEMBLY_CONSTITUENCY_LOCATION_TYPE = "AssemblyConstituency";
    @Autowired
    private LocationTypeRepository locationTypeRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocationsOfType(LocationType locationType, Long parentLocationId) throws AppException {
        return getAllLocationsOfType(locationType.getId(), parentLocationId);
    }

    @Override
    public List<Location> getAllLocationsOfType(Long locationTypeId, Long parentLocationId) throws AppException {
        if (parentLocationId == null) {
            return locationRepository.getLocationsByLocationTypeIdOrderByNameAsc(locationTypeId);
        }
        return locationRepository.getLocationsByLocationTypeIdAndParentLocationIdOrderByNameAsc(locationTypeId, parentLocationId);
    }

    @Override
    public List<LocationType> getAllLocationTypes() throws AppException {
        return locationTypeRepository.findAll();
    }

    @Override
    public List<Location> getAllCountries() throws AppException {
        LocationType locationType = locationTypeRepository.getLocationTypeByName(COUNTRY_LOCATION_TYPE);
        if (locationType == null) {
            return Collections.emptyList();
        }
        return locationRepository.getLocationsByLocationTypeIdOrderByNameAsc(locationType.getId());
    }

    @Override
    public List<Location> getAllStates() throws AppException {
        LocationType locationType = locationTypeRepository.getLocationTypeByName(STATE_LOCATION_TYPE);
        if (locationType == null) {
            return Collections.emptyList();
        }
        return locationRepository.getLocationsByLocationTypeIdOrderByNameAsc(locationType.getId());
    }

    @Override
    public List<Location> getAllParliamentConstituenciesOfState(Long stateId) throws AppException {
        LocationType locationType = locationTypeRepository.getLocationTypeByName(PARLIAMENT_CONSTITUENCY_LOCATION_TYPE);
        if (locationType == null) {
            return Collections.emptyList();
        }
        return locationRepository.getLocationsByLocationTypeIdAndParentLocationIdOrderByNameAsc(locationType.getId(), stateId);
    }

    @Override
    public List<Location> getAllDistrictOfState(Long stateId) throws AppException {
        LocationType locationType = locationTypeRepository.getLocationTypeByName(DISTRICT_LOCATION_TYPE);
        if (locationType == null) {
            return Collections.emptyList();
        }
        return locationRepository.getLocationsByLocationTypeIdAndParentLocationIdOrderByNameAsc(locationType.getId(), stateId);
    }

    @Override
    public List<Location> getAllAssemblyConstituenciesOfDistrict(Long districtId) throws AppException {
        LocationType locationType = locationTypeRepository.getLocationTypeByName(ASSEMBLY_CONSTITUENCY_LOCATION_TYPE);
        if (locationType == null) {
            return Collections.emptyList();
        }
        return locationRepository.getLocationsByLocationTypeIdAndParentLocationIdOrderByNameAsc(locationType.getId(), districtId);
    }

    @Override
    public List<Location> getAllChildLocations(Long locationId) throws AppException {
        return locationRepository.getLocationsByParentLocationId(locationId);
    }

    @Override
    public List<LocationType> getAllLocationUnderLocationType(Long locationTypeId) throws AppException {
        LocationType locationType = locationTypeRepository.findOne(locationTypeId);
        if (locationType == null) {
            return Collections.emptyList();
        }
        List<LocationType> locationTypes = new ArrayList<LocationType>();
        locationTypes.add(locationType);
        addChildLocationTypes(locationType, locationTypes);
        return locationTypes;
    }

    private void addChildLocationTypes(LocationType locationType, List<LocationType> locationTypes) {
        if (locationType.getChildLocationTypes() == null || locationType.getChildLocationTypes().isEmpty()) {
            return;
        }
        for (LocationType oneChilLocationType : locationType.getChildLocationTypes()) {
            locationTypes.add(oneChilLocationType);
            addChildLocationTypes(oneChilLocationType, locationTypes);
        }
    }

    @Override
    public Location findLocationById(Long locationId) throws AppException {
        return locationRepository.findOne(locationId);
    }

    @Override
    public LocationType saveLocationType(LocationType locationType) throws AppException {
        return locationTypeRepository.save(locationType);
    }

    @Override
    public Location saveLocation(Location location) throws AppException {
        if (location.getLocationType() == null) {
            throw new AppException("Location Type can not be null");
        }
        LocationType locationType = locationTypeRepository.findOne(location.getLocationType().getId());
        location.setLocationType(locationType);
        location.setNameUp(location.getName().toUpperCase());
        if (location.getParentLocation() != null) {
            Location parentLocation = locationRepository.findOne(location.getParentLocation().getId());
            location.setParentLocation(parentLocation);
        }
        return locationRepository.save(location);
    }

}
