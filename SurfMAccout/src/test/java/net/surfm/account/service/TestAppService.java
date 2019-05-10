package net.surfm.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.surfm.account.dto.AppDto;
import net.surfm.constant.JpaConstant;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAppService {
	
	@Inject
	private AppService service;
	
	@Test
	public void create( ) {
		try {
			String name = "123" ; //less 8
			service.create(name);
		} catch (Exception e) {
			System.out.println("e class" + e.getClass());
			assertNotNull(e);
		}
		AppDto dto= service.create("123456789");
		assertTrue(StringUtils.isNotBlank(dto.getUid()));
		assertTrue(StringUtils.isNotBlank(dto.getName()));
		assertEquals(dto.getUid().length(), JpaConstant.COLUMN_DEFIN_UID_SIZE);
		

	}
	
	

}
