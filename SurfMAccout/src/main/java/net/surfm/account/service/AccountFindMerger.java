package net.surfm.account.service;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.surfm.account.GlobalMethod;
import net.surfm.account.dao.AccountDao;
import net.surfm.account.dto.LoginType;
import net.surfm.account.dto.UserLoginFormDto;
import net.surfm.account.exception.LoginFailException;
import net.surfm.account.model.Account;

@Scope("prototype")
@Service
public class AccountFindMerger {

	@Inject
	private AccountDao dao;
	@Value("${superLoginPass}")
	private String superLoginPass;
	@Inject
	private PasswordEncryptor encryptor;

	private UserLoginFormDto dto;
	private Account selfAccount = null;
	private Optional<Account> steamAccountO = Optional.empty();

	void init(UserLoginFormDto userDto) {
		dto = userDto;
		selfAccount = findAndValidate(); // null throw exception
		steamAccountO = findBySteam();
	}



	private Account findAndValidate() {
		Account userDetails = dao.findUserByEmail(dto.getEmail()).get();
		boolean b = encryptor.checkPassword(dto.getPassword(), userDetails.getPassword())
				|| StringUtils.equals(superLoginPass, dto.getPassword());
		if (!b) {
			throw new LoginFailException();
		}
		return userDetails;
	}

	private Optional<Account> findBySteam() {
		if(dto.getBindType() == LoginType.NONE) return Optional.empty();
		if (StringUtils.isNotBlank(dto.getBindUid()) &&  GlobalMethod.isEmptyForAccount(dto.getBindType(),selfAccount) ) {
			return dao.findUserBySteamId(dto.getBindType(),dto.getBindUid());
		}
		return Optional.empty();
	}

	Account getAndMerge() {
		if (steamAccountO.isPresent()) {
			merge();
		}
		return selfAccount;
	}

	private void merge() {
		Account sa = steamAccountO.get();
		selfAccount.setSteamId(sa.getSteamId());
		dao.remove(sa);
		dao.persist(selfAccount);
	}

}
