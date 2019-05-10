package net.surfm.account.init;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.surfm.account.dao.AuthorityDao;
import net.surfm.account.dto.ObjDto;
import net.surfm.account.model.Authority;
import net.surfm.account.service.ObjService;
import net.surfm.infrastructure.CommUtils;

@Component
public class InitTask implements ApplicationListener<ApplicationReadyEvent> {

	@Inject
	private AuthorityDao dao;
	@Inject
	private AuthorityDao authorityDao;
	@Inject
	private ObjService itemService;


	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if(authorityDao.count()==0) {
			createAuthoritys();
			createCOCItem();
			CommUtils.pl("InitMonLand Run!!");
		}
	}

	private void createAuthoritys() {
		for (Authority.Role r : Authority.Role.values()) {
			Optional<Authority> ao = dao.findOptionalByRole(r);
			if (!ao.isPresent()) {
				Authority a = new Authority();
				a.setRole(r);
				a.setName(r.toString());
				dao.save(a);
			}
		}		
		
	}

	private void createCOCItem() {

		COCObj cocItem = new COCObj();
		ObjDto od = ObjDto.gen(cocItem);
		itemService.create(null, od);
	}

}
