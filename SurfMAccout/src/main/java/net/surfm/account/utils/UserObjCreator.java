package net.surfm.account.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.surfm.account.SDKUtils;
import net.surfm.account.dao.AccountDao;
import net.surfm.account.dao.ObjDao;
import net.surfm.account.dao.UserObjDao;
import net.surfm.account.dto.UserObjDto;
import net.surfm.account.model.Account;
import net.surfm.account.model.Obj;
import net.surfm.account.model.ObjField;
import net.surfm.account.model.UserObj;
import net.surfm.account.model.UserObjField;
import net.surfm.constant.JpaConstant;
import net.surfm.exception.SurfmRuntimeException;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.JsonMap;
import net.surfm.infrastructure.JsonMap.MergePV;
import net.surfm.infrastructure.JsonMap.MergePVO;
import net.surfm.infrastructure.SkipbleConverter;

@Scope("prototype")
@Service
public class UserObjCreator {

	@Inject
	private UserObjDao dao;
	@Inject
	private ObjDao itemDao;
	@Inject
	private AccountDao accountDao;
	@Inject
	private SkipbleConverter skipbleConverter;

	private JsonMap inData;
	private Obj baseItem;
	private UserObj result;
	private Set<UserObjField> fields;

	public UserObjCreator init(String itemUid, JsonMap i) {
		this.inData = i;
		this.baseItem = itemDao.findById(itemUid).get();
		this.result = new UserObj();
		this.result.setUid(CommUtils.randomUid(JpaConstant.COLUMN_DEFIN_UID_SIZE));
		return this;
	}
	


	public UserObjCreator process() {
		Map<String, Object> pm = inData.parsePathValue();
		fields = pm.entrySet().stream().map(genFieldFunc).filter(f-> f!=null ).collect(Collectors.toSet());
		return this;
	}

	public UserObjDto save(int amount,  String ownerUid) {
		result.setCombined(baseItem.isCombined());
		result.setAmount(amount);
		if (fields == null)
			throw new SurfmRuntimeException("fields is null");
		result.setFields(fields);
		result.setObj(baseItem);
		Account a = accountDao.findUserByUid(ownerUid).get();
		result.setOwner(a);
		result.setUpdateNum(0);
		result.setData(genData());
		return skipbleConverter.convert(dao.save(result), new UserObjDto(), StringUtils.EMPTY) ;
	}

	private JsonMap genData() {
		Collection<MergePV> pvs = baseItem.getData().parsePathValue().entrySet().stream()
				.map(f -> new MergePVO(f.getKey(), f.getValue())).collect(Collectors.toList());
		inData.merge(pvs);
		pvs = fields.stream().map(f ->new MergePVO(f.getName()  , f.getFieldInfo().getType().parse(f.getValue()) )  ).collect(Collectors.toList());
		inData.merge(pvs);
		return inData;
	}

	private Function<Map.Entry<String, Object>, UserObjField> genFieldFunc = kv -> {
		ObjField itemF = baseItem.getFields().stream().filter(f -> {
			return StringUtils.equals(f.getName(), kv.getKey());
		}).collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
			if(list.size() == 0) return null;
			if (list.size() != 1)
				throw new SurfmRuntimeException("this not find or has two filed");
			return list.get(0);
		}));
		if(itemF == null) return null;
		UserObjField ans = new UserObjField();
		ans.setFieldInfo(itemF);
		ans.setName(kv.getKey());
		ans.setValue(itemF.getType().convertAndValidateString(kv.getValue()));
		ans.setUid(SDKUtils.getUserObjFieldUid(result.getUid(), itemF.getUid()));
		ans.setObj(result);

		return ans;
	};

}
