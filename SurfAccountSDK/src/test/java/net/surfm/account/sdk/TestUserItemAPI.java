package net.surfm.account.sdk;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserItemAPI {

	@Inject
	private UserObjAPIRest api;
	@Value("${testUid}")
	private String testUid;

	@Test
	public void testIncreaseCoin() {
	}
	
	@Test
	public void testIncreaseCoins() {
		
	}
	
	@Test
	public void testGetCoin() {
	}

}
