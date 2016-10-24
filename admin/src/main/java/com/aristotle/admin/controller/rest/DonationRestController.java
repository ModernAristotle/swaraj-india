package com.aristotle.admin.controller.rest;

import com.aristotle.admin.controller.beans.donation.DonationRequestBean;
import com.aristotle.admin.controller.beans.donation.DonationResponseBean;
import com.aristotle.core.exception.AppException;
import edu.emory.mathcs.backport.java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
public class DonationRestController extends BaseRestController {

    @RequestMapping(value = "/service/s/donation", method = RequestMethod.POST)
    public DonationRequestBean saveDonation(HttpServletRequest httpServletRequest, @RequestBody DonationRequestBean donationRequestBean) throws AppException {
        return donationRequestBean;
    }

    @RequestMapping(value = "/service/s/donation/user/{adminUserId}", method = RequestMethod.GET)
    public List<DonationResponseBean> getAdminDonations(HttpServletRequest httpServletRequest, @PathVariable("adminUserId") Long adminUserId) throws AppException {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/service/s/donation/search/email/{emailId}", method = RequestMethod.GET)
    public List<DonationResponseBean> searchDonationByEmail(HttpServletRequest httpServletRequest, @PathVariable("emailId") String emailId) throws AppException {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/service/s/donation/search/mobile/{mobileNumber}", method = RequestMethod.GET)
    public List<DonationResponseBean> searchDonationByMobileNumber(HttpServletRequest httpServletRequest, @PathVariable("mobileNumber") String mobileNumber) throws AppException {
        return Collections.emptyList();
    }
}
