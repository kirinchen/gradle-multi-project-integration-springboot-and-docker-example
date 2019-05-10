package net.surfm.account.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.surfm.account.model.Account;
import net.surfm.account.model.UserObj;

public interface UserObjDao extends CrudRepository<UserObj, String> {
	List<UserObj> findByOwner(Account a);
}
