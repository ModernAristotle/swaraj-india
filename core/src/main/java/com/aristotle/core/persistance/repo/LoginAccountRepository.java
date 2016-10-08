package com.aristotle.core.persistance.repo;

import com.aristotle.core.persistance.LoginAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAccountRepository extends JpaRepository<LoginAccount, Long> {

    LoginAccount getLoginAccountByUserName(String userName);

    LoginAccount getLoginAccountByUserId(Long userId);
}
