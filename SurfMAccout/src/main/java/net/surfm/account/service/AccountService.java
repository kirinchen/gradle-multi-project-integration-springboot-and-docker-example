package net.surfm.account.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.dto.LoginType;
import net.surfm.account.dto.UserInfoDto;
import net.surfm.account.dto.UserLoginFormDto;
import net.surfm.account.dto.UserSignupFormDto;
import net.surfm.account.model.Account;

public interface AccountService extends UserDetailsService {

	public Optional<Account> findByUserName(String userName);

	@Transactional
	public void update(String userName, UserInfoDto userInfo);

	public Account getCurrentAccount();

	@Transactional
	public Account signup(UserSignupFormDto userDto);

	@Transactional
	public Account findOrCreateBySteam(LoginType t,String uid);

	@Transactional
	public Account getAndMerge(UserLoginFormDto userDto);

}
