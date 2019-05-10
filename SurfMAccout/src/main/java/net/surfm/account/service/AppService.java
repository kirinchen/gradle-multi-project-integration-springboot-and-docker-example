package net.surfm.account.service;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.dao.AppDao;
import net.surfm.account.dto.AppDto;
import net.surfm.account.model.App;

@Service
public class AppService {
	
	@Inject
	private AppDao dao;
	@Inject
	private DozerBeanMapper mapper;
	
	@Transactional
	public AppDto create( String name) {
		App app = new App();
		app.setName(name);
		app.setupUid();
		dao.save(app);
		return mapper.map(app, AppDto.class); 
	}
	
	public AppDao getDao() {
		return dao;
	}
	

}
