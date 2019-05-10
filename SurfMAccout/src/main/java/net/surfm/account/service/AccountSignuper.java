package net.surfm.account.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.surfm.account.dao.AccountDao;
import net.surfm.account.dao.AuthorityDao;
import net.surfm.account.dto.LoginType;
import net.surfm.account.dto.UserSignupFormDto;
import net.surfm.account.exception.PhoneExistException;
import net.surfm.account.exception.UserExistException;
import net.surfm.account.model.Account;
import net.surfm.account.model.Authority;
import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.SkipbleConverter;

@Scope("prototype")
@Service
public class AccountSignuper {

	@Inject
	private AccountDao dao;
	@Inject
	private SkipbleConverter skipbleConverter;
	@Inject
	private AuthorityDao authorityDao;
	@Inject
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	private UserSignupFormDto dto = null;
	private Optional<Account> steamAccountO = Optional.empty();

	void init(UserSignupFormDto userDto) {
		validate(userDto);
		dto = userDto;
		if (dto.getBindType() != LoginType.NONE) {
			steamAccountO = dao.findUserBySteamId(dto.getBindType(), dto.getBindUid());
		}
	}

	private void validate(UserSignupFormDto d) {
		Optional<Account> userO = dao.findUserByEmail(d.getEmail());
		if (userO.isPresent())
			throw new UserExistException();
		userO = dao.findUserByPhone(d.getPhone());
		if (userO.isPresent())
			throw new PhoneExistException();
	}

	Account signup() {
		Account u = skipbleConverter.convert(dto, new Account(), StringUtils.EMPTY);
		u.setUid(CommUtils.randomUid(JpaConstant.COLUMN_DEFIN_UID_SIZE));
		u.setUsername(u.getEmail());
		u.setEnabled(true);
		u.set_password(u.getPassword());
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setType(LoginType.SLEF);
		u.setCreateAt(new Date());
		Authority a = authorityDao.findOptionalByRole(Authority.Role.NORMAL).get();
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(a);
		u.setAuthoritiesSet(authorities);
		dao.persist(u);
		if (steamAccountO.isPresent()) {
			merge(u, steamAccountO.get());
		}
		return u;
	}

	private void merge(Account newA, Account steamA) {
		newA.setSteamId(steamA.getSteamId());
		dao.remove(steamA);
		dao.persist(newA);
	}

}
