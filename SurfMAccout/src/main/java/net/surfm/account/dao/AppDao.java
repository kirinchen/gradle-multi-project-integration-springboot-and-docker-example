package net.surfm.account.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.surfm.account.model.App;

public interface AppDao extends CrudRepository<App, String> {
	
	Optional<App> findOptionalByName(String name);

}
