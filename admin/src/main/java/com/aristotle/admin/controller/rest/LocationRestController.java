package com.aristotle.admin.controller.rest;

import com.aristotle.core.exception.AppException;
import com.aristotle.core.persistance.Location;
import com.aristotle.core.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
public class LocationRestController extends BaseRestController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/service/us/location/states", method = RequestMethod.GET)
    public List<Location> getAllIndianStates(HttpServletRequest httpServletRequest) throws AppException {
        return locationService.getAllStates();
    }

    @RequestMapping(value = "/service/us/location/districts/{stateId}", method = RequestMethod.GET)
    public List<Location> getAllDistrictOfState(HttpServletRequest httpServletRequest, @PathVariable("stateId") Long stateId) throws AppException {
        return locationService.getAllDistrictOfState(stateId);
    }

    @RequestMapping(value = "/service/us/location/acs/{districtId}", method = RequestMethod.GET)
    public List<Location> getAllAssemblyConstituenciesOfDistrict(HttpServletRequest httpServletRequest, @PathVariable("districtId") Long districtId) throws AppException {
        return locationService.getAllAssemblyConstituenciesOfDistrict(districtId);
    }

    @RequestMapping(value = "/service/us/location/pc/{stateId}", method = RequestMethod.GET)
    public List<Location> getAllParliamentConstituenciesOfState(HttpServletRequest httpServletRequest, @PathVariable("stateId") Long stateId) throws AppException {
        return locationService.getAllParliamentConstituenciesOfState(stateId);
    }

    @RequestMapping(value = "/service/us/location/countries", method = RequestMethod.GET)
    public List<Location> getAllCountries(HttpServletRequest httpServletRequest) throws AppException {
        return locationService.getAllCountries();
    }

    @RequestMapping(value = "/service/us/location/cr/{countryId}", method = RequestMethod.GET)
    public List<Location> getAllCountryRegions(HttpServletRequest httpServletRequest, @PathVariable("countryId") Long countryId) throws AppException {
        return locationService.getAllChildLocations(countryId);
    }

    @RequestMapping(value = "/service/us/location/cra/{countryRegionId}", method = RequestMethod.GET)
    public List<Location> getAllCountryRegionAreas(HttpServletRequest httpServletRequest, @PathVariable("countryRegionId") Long countryRegionId) throws AppException {
        return locationService.getAllChildLocations(countryRegionId);
    }

    @RequestMapping(value = "/service/us/location/{locationId}", method = RequestMethod.GET)
    public Location getLocationById(HttpServletRequest httpServletRequest, @PathVariable("locationId") Long locationId) throws AppException {
        return locationService.findLocationById(locationId);
    }

}
