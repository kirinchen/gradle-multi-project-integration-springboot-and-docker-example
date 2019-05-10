package net.surfm.account.dao;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.repository.CrudRepository;

import net.surfm.account.model.Obj;
import net.surfm.account.model.ObjField;

public interface ObjFieldDao extends CrudRepository<ObjField, String> {
	
	@PrePersist
	default void onCreate(Obj i) {
		i.setCreateAt(new Date());
		i.setUpdateAt(new Date());
	}

	@PreUpdate
	default void onUpdate(Obj i) {
		i.setUpdateAt(new Date());
	}	

}
