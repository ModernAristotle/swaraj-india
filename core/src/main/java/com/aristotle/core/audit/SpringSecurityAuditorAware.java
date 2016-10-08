package com.aristotle.core.audit;

import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Long getCurrentAuditor() {
        // System.out.println("Getting User from SecurityContextHolder");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            // System.out.println("Returning Null " + authentication);
//            return null;
//        }
//        // System.out.println("Returning user : " + authentication.getPrincipal());
//        return ((User) authentication.getPrincipal()).getId();
        return null;
    }
}