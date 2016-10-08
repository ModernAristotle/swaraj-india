package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    public Phone getPhoneByPhoneNumberAndCountryCode(String phone, String countryCode);

    public Phone getPhoneByPhoneNumber(String phone);

    public List<Phone> getPhonesByUserId(Long userId);

}