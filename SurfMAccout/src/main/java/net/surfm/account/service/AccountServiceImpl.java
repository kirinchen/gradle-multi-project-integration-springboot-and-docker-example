package net.surfm.account.service;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.GlobalMethod;
import net.surfm.account.dao.AccountDao;
import net.surfm.account.dto.CheckProtectCodeValidator;
import net.surfm.account.dto.LoginType;
import net.surfm.infrastructure.SkipbleConverter;
import net.surfm.account.dto.UserInfoDto;
import net.surfm.account.dto.UserLoginFormDto;
import net.surfm.account.dto.UserSignupFormDto;
import net.surfm.account.model.Account;


/**
 * 
 * @author kirin
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Inject
	private AccountDao userDao;

	@Inject
	private SkipbleConverter skipbleConverter;
	
	@Inject
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;
	@Inject
	private Provider<AccountFindMerger> accountFindMergerProvider;
	@Inject
	private Provider<AccountSignuper> accountSignuperProvider;

	@Transactional
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		Optional<Account> optlUser = userDao.findUserByEmail(arg0);
		return optlUser.get();
	}

	public Optional<Account> findByUserName(String userName) {
		return userDao.findUserByEmail(userName);
	}

	@Transactional
	public void update(String userName, UserInfoDto userInfo) {
		userInfo.setEmail(null);
		userInfo.setId(null);
		userInfo.setType(null);
		Account user = findByUserName(userName).get();
		Account newUser = skipbleConverter.convert(userInfo,user ,StringUtils.EMPTY);
		userDao.persist(newUser);
	}

	public Account getCurrentAccount() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal instanceof Account ? (Account) principal : null;
	}

	@Transactional
	public Account signup(UserSignupFormDto userDto) {
		AccountSignuper as = accountSignuperProvider.get();
		as.init(userDto);
		return as.signup();
	}

	@Override
	public Account findOrCreateBySteam(LoginType t,String uid) {
		Optional<Account> oa = userDao.findUserBySteamId(t,uid);
		if (oa.isPresent()) {
			return oa.get();
		} else {
			Account u = new Account();
			String un =  String.format(t.getMailPattern(), uid); //"steam" + steamId + "@steam.com";
			u.setUsername(un);
			u.setUsername(un);
			u.setCreateAt(new Date());
			u.setEmail(un);
			GlobalMethod.setupUid(t,u,uid);
			u.setPassword(passwordEncoder.encode(uid + CheckProtectCodeValidator.TOKEN_KEY));
			u.setEnabled(true);
			u.setType(t);
			userDao.persist(u);
			return u;
		}
	}

	@Override
	public Account getAndMerge(UserLoginFormDto userDto) {
		AccountFindMerger afm = accountFindMergerProvider.get();
		afm.init(userDto);
		return afm.getAndMerge();
	}

}
