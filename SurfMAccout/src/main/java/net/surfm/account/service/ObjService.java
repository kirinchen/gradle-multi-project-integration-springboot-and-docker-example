package net.surfm.account.service;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.SDKUtils;
import net.surfm.account.dao.AppDao;
import net.surfm.account.dao.ObjDao;
import net.surfm.account.dto.ObjDto;
import net.surfm.account.model.App;
import net.surfm.account.model.Obj;
import net.surfm.account.model.ObjField;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.SkipbleConverter;

@Service
public class ObjService {

	public static final String KEY_SUBTOTAL = "@SUBTOTAL";

	
	@Inject
	private ObjDao dao;
	@Inject
	private AppDao appDao;
	@Inject
	private DozerBeanMapper mapper;
	@PersistenceContext
	private EntityManager em;
	@Inject 
	private SkipbleConverter skipbleConverter;



	
	
	@Transactional
	public ObjDto create(String appUid,ObjDto dto) {
		Obj item = skipbleConverter.convert(dto,new Obj(),StringUtils.EMPTY);
		if(!dto.isSystemed()) {
			App app = appDao.findById(appUid).get();
			item.setApp(app);
		}
		item.setObjHash(CommUtils.sha1(item.getData().toString()));
		item.setupUid();
		item.setFields(dto.getFields().stream().map(d->{
			ObjField of = mapper.map(d, ObjField.class);
			of.setObj(item);
			of.setUid(SDKUtils.getObjFieldUid(item.getUid(), of.getName()));
			return of;
		}).collect(Collectors.toList()));
		Obj _item = dao.save(item);
		return mapper.map(_item, ObjDto.class);
	}
	

	


}
