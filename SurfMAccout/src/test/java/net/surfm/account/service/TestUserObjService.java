package net.surfm.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.SDKContant;
import net.surfm.account.SDKUtils;
import net.surfm.account.dto.AppDto;
import net.surfm.account.dto.ObjDto;
import net.surfm.account.dto.UserObjDto;
import net.surfm.account.model.Account;
import net.surfm.account.model.App;
import net.surfm.account.testdata.DreamonConstant;
import net.surfm.account.testdata.DreamonPlayer;
import net.surfm.account.testdata.PetObj;
import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.JsonMap;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserObjService {

	public static final String APP_NAME = "Dreamon";
	
	@Inject
	private AppService appService;
	@Inject
	private ObjService itemService;	
	@Inject
	private UserObjService service;
	@Inject
	private TestAccountService testAccountService;
	

	private Account account;

	@Before
	public void init() {
		account = testAccountService.create();
		Optional<App> appO = appService.getDao().findOptionalByName(APP_NAME);
		if(!appO.isPresent()) {
			createData();
		} 		
	}

	private void createData() {
		AppDto appDto = appService.create(APP_NAME);
		PetObj petObj = new PetObj();
		itemService.create(appDto.getUid(), ObjDto.gen(petObj));
		
		DreamonPlayer player = new DreamonPlayer();
		itemService.create(appDto.getUid(), ObjDto.gen(player));
	}

	@Test
	public void testCreate() {
		String itemUid = SDKUtils.getSysObjUid(SDKContant.COC_OBJ_NAME);
		JsonMap i = new JsonMap();
		i.put("shortName", "QQQ");
		i.put("testField", "1234");
		UserObjDto dto = service.create(itemUid, i, 100, account.getUid());
		assertEquals(dto.getUid().length(), JpaConstant.COLUMN_DEFIN_UID_SIZE);
		CommUtils.pl("dto.getUid()="+dto.getUid());
		assertTrue(StringUtils.isNotBlank(dto.getUid()));
		assertEquals(dto.getData().get("shortName"),"QQQ" );
		assertEquals(dto.getData().get("testField"),"1234" );
		
		String appUid = SDKUtils.getAppUid(APP_NAME);
		itemUid = SDKUtils.getObjUid(appUid, DreamonConstant.OBJ_NAME_PET);
		
		i.putByPath("testMap"+JsonMap.PATH_DOT+"f1", 201);
		i.putByPath("attr"+JsonMap.PATH_DOT+"def", 99);
		i.putByPath("attr"+JsonMap.PATH_DOT+"ccc", true);
		dto = service.create(itemUid, i, 100, account.getUid());
		CommUtils.pl("dto.getUid()="+dto.getUid());
		assertEquals(dto.getData().get("shortName"),"QQQ" );
		assertEquals(dto.getData().get("testField"),"1234" );
		assertEquals(dto.getData().getByPath("testMap"+JsonMap.PATH_DOT+"f1"),201 );
		assertEquals(dto.getData().getByPath("attr"+JsonMap.PATH_DOT+"def"),99d );
		assertEquals(dto.getData().getByPath("attr"+JsonMap.PATH_DOT+"ccc"),true );
		
		
	}

}
