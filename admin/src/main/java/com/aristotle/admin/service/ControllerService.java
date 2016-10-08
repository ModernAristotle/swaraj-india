package com.aristotle.admin.service;

import com.aristotle.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ControllerService {

    @Autowired
    private UserService userService;

}
