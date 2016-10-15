package com.aristotle.admin.controller.beans;

import com.aristotle.core.persistance.Location;
import com.aristotle.core.persistance.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserSessionObject implements Serializable {
    private User user;
    private UserPermissionBean userPermissionBean;
    private List<Location> userAdminLocations;
    private Location selectedAdminLocation;
}
