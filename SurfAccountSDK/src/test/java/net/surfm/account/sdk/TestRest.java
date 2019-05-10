package net.surfm.account.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.surfm.account.SDKContant;
import net.surfm.account.SDKUtils;
import net.surfm.account.dto.LoginResult;
import net.surfm.account.dto.UserObjDto;
import net.surfm.account.dto.UserSignupFormDto;
import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.JsonMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRest {
	
	@Inject
	private PublicRest publicRest;
	@Inject
	private UserObjAPIRest userObjAPIRest;
	
	private LoginResult loginResult;

	@Before
	public void init() {
		 UserSignupFormDto userDto = new UserSignupFormDto();
		 userDto.setPassword("12345678");
		 userDto.setEmail("aaa@gmail.com");
		 userDto.setRecaptchaResponse("xxxx");
		 userDto.setSkipCode("11112122");
		 loginResult = publicRest.signUp(userDto ).getBody();
	}	
	
	@Test
	public void testCreate() {
		String itemUid = SDKUtils.getSysObjUid(SDKContant.COC_OBJ_NAME);
		JsonMap i = new JsonMap();
		i.put("shortName", "QQQ");
		i.put("testField", "1234");
		UserObjDto dto = userObjAPIRest.create(itemUid, i, 1, loginResult.getUid()).getBody();
		assertTrue(dto!=null);
		assertEquals(dto.getUid().length(), JpaConstant.COLUMN_DEFIN_UID_SIZE);
		CommUtils.pl("dto.getUid()="+dto.getUid());
		assertTrue(StringUtils.isNotBlank(dto.getUid()));
		assertEquals(dto.getData().get("shortName"),"QQQ" );
		assertEquals(dto.getData().get("testField"),"1234" );
		
		String appUid = SDKUtils.getAppUid("Dreamon");
		itemUid = SDKUtils.getObjUid(appUid, "Pet");
		
		i.putByPath("testMap"+JsonMap.PATH_DOT+"f1", 201);
		i.putByPath("attr"+JsonMap.PATH_DOT+"def", 99);
		i.putByPath("attr"+JsonMap.PATH_DOT+"ccc", true);
		dto = userObjAPIRest.create(itemUid, i, 100, loginResult.getUid()).getBody();
		CommUtils.pl("dto.getUid()="+dto.getUid());
		assertEquals(dto.getData().get("shortName"),"QQQ" );
		assertEquals(dto.getData().get("testField"),"1234" );
		assertEquals(dto.getData().getByPath("testMap"+JsonMap.PATH_DOT+"f1"),201 );
		assertEquals(dto.getData().getByPath("attr"+JsonMap.PATH_DOT+"def"),99d );
		assertEquals(dto.getData().getByPath("attr"+JsonMap.PATH_DOT+"ccc"),true );
		
		
	}
	
	
}
