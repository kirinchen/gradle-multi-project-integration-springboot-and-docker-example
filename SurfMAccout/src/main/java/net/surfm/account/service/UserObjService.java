package net.surfm.account.service;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.dao.AccountDao;
import net.surfm.account.dao.UserObjDao;
import net.surfm.account.dto.UserObjDto;
import net.surfm.account.model.Account;
import net.surfm.account.utils.UserObjCreator;
import net.surfm.infrastructure.JsonMap;
import net.surfm.infrastructure.SkipbleConverter;

@Service
public class UserObjService {

	@Inject
	private Provider<UserObjCreator> userObjCreatorProvider;
	@Inject
	private UserObjDao dao;
	@Inject
	private SkipbleConverter skipbleConverter;
	@Inject
	private AccountDao accountDao;

	@Transactional
	public UserObjDto create(String itemUid, JsonMap i, int amount, String ownerUid) {
		return userObjCreatorProvider.get().init(itemUid, i).process().save(amount, ownerUid);
	}

	@Transactional
	public Collection<UserObjDto> listByUser(String ownerUid) {
		return listByUser(accountDao.findUserByUid(ownerUid).get());
	}

	@Transactional
	public Collection<UserObjDto> listByUser(Account a) {
		return dao.findByOwner(a).stream().map(i -> skipbleConverter.convert(i, new UserObjDto(), StringUtils.EMPTY))
				.collect(Collectors.toList());
	}
	
	//public UserObjDto 

}
