package net.surfm.account.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.surfm.account.model.Authority;

public interface AuthorityDao extends CrudRepository<Authority, Integer> {

	Optional<Authority> findOptionalByRole(Authority.Role role);

}
