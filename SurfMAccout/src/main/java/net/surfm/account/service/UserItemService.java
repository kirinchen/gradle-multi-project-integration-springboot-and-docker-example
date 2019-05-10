package net.surfm.account.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.dao.ObjDao;
import net.surfm.account.dao.UserObjDao;
import net.surfm.account.model.Obj;
import net.surfm.infrastructure.JsonMap;

@Service
public class UserItemService {
	@Inject
	private UserObjDao dao;
	@Inject
	private ObjDao itemDao;
	
	@Transactional
	public void create(String itemUid,JsonMap placeData) {
		Obj itIfno = itemDao.findById(itemUid).get(); 
//		UserItem useItem = 
	}
	
	

}
