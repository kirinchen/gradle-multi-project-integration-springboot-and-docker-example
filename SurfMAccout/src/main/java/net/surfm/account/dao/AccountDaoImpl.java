package net.surfm.account.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.dto.LoginType;
import net.surfm.account.model.Account;

/**
 * 
 * @author kirin
 *
 */
@Repository
public class AccountDaoImpl extends BasicDaoImpl<Account> implements AccountDao {

	public AccountDaoImpl() {
		super(Account.class);
	}

	@Override
	@Transactional
	public Optional<Account> findUserByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + Account.class.getSimpleName());
		sb.append(" WHERE email=:email ");
		Query q = getEm().createQuery(sb.toString());
		q.setParameter("email", email);
		Account u = (Account) DataAccessUtils.uniqueResult(q.getResultList());
		return Optional.ofNullable(u);
	}
	
	@Override
	public Optional<Account> findUserByPhone(String phone) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + Account.class.getSimpleName());
		sb.append(" WHERE phone=:phone ");
		Query q = getEm().createQuery(sb.toString());
		q.setParameter("phone", phone);
		Account u = (Account) DataAccessUtils.uniqueResult(q.getResultList());
		return Optional.ofNullable(u);
	}

	@Override
	@Transactional
	public Optional<Account> findUserBySteamId(LoginType t, String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + Account.class.getSimpleName());
		sb.append(" WHERE " + t.getField() + "=:" + t.getField() + " ");
		Query q = getEm().createQuery(sb.toString());
		q.setParameter(t.getField(), t.toFieldType(uid));
		Account u = (Account) DataAccessUtils.uniqueResult(q.getResultList());
		return Optional.ofNullable(u);
	}

	@Override
	public Optional<Account> findUserByUid(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + Account.class.getSimpleName());
		sb.append(" WHERE uid=:uid ");
		Query q = getEm().createQuery(sb.toString());
		q.setParameter("uid", uid);
		Account u = (Account) DataAccessUtils.uniqueResult(q.getResultList());
		return Optional.ofNullable(u);
	}
	
	

	@Override
	public List<Account> listByPhone(String phone, int page, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + Account.class.getSimpleName());
		if (StringUtils.isNotBlank(phone)) {
			sb.append(" WHERE phone LIKE :phone ");
		}
		Query q = getEm().createQuery(sb.toString());
		if (StringUtils.isNotBlank(phone)) {
			q.setParameter("phone", "%"+phone+"%");
		}
		q.setFirstResult((page - 1) * size);
		q.setMaxResults(size);
		return q.getResultList();
	}



}
