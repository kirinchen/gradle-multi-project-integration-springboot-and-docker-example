package net.surfm.account.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author kirin
 *
 * @param <T>
 */
public interface BaseDao<T> {

  Optional<T> findOne(final Serializable id);

  List<T> listAll();

  void persist(final T entity);

  void merge(final T entity);
  
  void remove(final T entity);
  
}
