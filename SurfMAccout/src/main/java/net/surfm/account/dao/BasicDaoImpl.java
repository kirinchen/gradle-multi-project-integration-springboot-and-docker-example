package net.surfm.account.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 
 * @author kirin
 *
 */
public class BasicDaoImpl<T> implements BaseDao<T> {

	@PersistenceContext
	private EntityManager em;

	private Class<T> cls;

	public BasicDaoImpl(Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public Optional<T> findOne(Serializable id) {
		T t = em.find(cls, id);
		return Optional.ofNullable(t);
	}

	@Override
	public List<T> listAll() {
		TypedQuery<T> allQuery = em.createQuery(getListAllCriteriaQuery());
		return allQuery.getResultList();
	}

	private CriteriaQuery<T> getListAllCriteriaQuery() {
		CriteriaQuery<T> cq = getCriteriaQuery();
		Root<T> personRoot = getRoot(cq);
		CriteriaQuery<T> all = cq.select(personRoot);
		return all;
	}

	public Root<T> getRoot(CriteriaQuery<T> cq) {
		Root<T> rootEntry = cq.from(cls);
		return rootEntry;
	}

	public CriteriaQuery<T> getCriteriaQuery() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(cls);
		return cq;
	}

	@Override
	public void persist(T entity) {
		em.persist(entity);
	}

	@Override
	public void merge(T entity) {
		em.merge(entity);
	}

	public EntityManager getEm() {
		return em;
	}

	public CriteriaBuilder getCb() {
		return em.getCriteriaBuilder();
	}

	@Override
	public void remove(T entity) {
		em.remove(entity);
	}

}
