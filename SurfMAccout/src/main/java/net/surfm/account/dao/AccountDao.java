package net.surfm.account.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import net.surfm.account.dto.LoginType;
import net.surfm.account.model.Account;

/**
 * 
 * @author kirin
 *
 */
public interface AccountDao extends BaseDao<Account> {

	Optional<Account> findUserByEmail(String email);
	
	Optional<Account> findUserByPhone(String phone);

	Optional<Account> findUserBySteamId(LoginType t, String uid);

	Optional<Account> findUserByUid(String uid);

	List<Account> listByPhone(String phone,int page, int size);
	
}
