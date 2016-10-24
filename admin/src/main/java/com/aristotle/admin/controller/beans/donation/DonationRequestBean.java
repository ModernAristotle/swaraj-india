package com.aristotle.admin.controller.beans.donation;

import lombok.Data;

@Data
public class DonationRequestBean {

    private String emailId;
    private String mobileNumber;
    private String donorName;
    private String donorAddress;
    private Long stateId;
    private Long districtId;
    private Long assemblyConstituencyId;
    private Long parliamentConstituencyId;
    private Double amount;
    private Integer pinCode;
    private String chequeNumber;
    private String type;

}
