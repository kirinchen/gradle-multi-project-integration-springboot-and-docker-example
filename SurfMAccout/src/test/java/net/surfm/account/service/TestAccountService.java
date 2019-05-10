package net.surfm.account.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.surfm.account.dao.AccountDao;
import net.surfm.account.dto.UserSignupFormDto;
import net.surfm.account.model.Account;
import net.surfm.infrastructure.CommUtils;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class TestAccountService {

	public static final String email = "abc@gmail.com";
	public static final String password = "1234567";
	
	@Inject
	private AccountService service;
	@Inject 
	private AccountDao dao;
	
	@Test
	public void testSignup() {
		String _e = email;
		if(dao.findOne(_e).isPresent()) {
			_e = "QQQQ"+System.currentTimeMillis()+"@gmail.com";
		}
		
		Account a = create();
		assertEquals(a.getEmail(), _e);
		Account _a = dao.findUserByEmail(_e).get();
		assertEquals(a.getEmail(), _a.getEmail());
		CommUtils.pl(_a.getPassword());
	}
	
	public Account create() {
		UserSignupFormDto userDto = new UserSignupFormDto();
		userDto.setEmail(email);
		userDto.setPassword(password);
		return service.signup(userDto );		
	}

}
