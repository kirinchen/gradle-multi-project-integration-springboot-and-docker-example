package net.surfm.account.api;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.surfm.account.aop.CurrentUser;
import net.surfm.account.dto.UserObjDto;
import net.surfm.account.model.Account;
import net.surfm.account.service.UserObjService;
import net.surfm.infrastructure.JsonMap;

@Validated
@Controller
@RequestMapping(value = "/userobj/")
public class UserObjController {

	@Inject
	private  UserObjService service;	
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{ownerUid}/create/{itemUid}/", method = RequestMethod.POST)	
	public UserObjDto create(@PathVariable  String itemUid,@RequestBody JsonMap i,@RequestParam int amount,@CurrentUser Account activeUser) {
		return service.create(itemUid, i, amount, activeUser.getUid());
	}	
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "listAll", method = RequestMethod.GET)		
	public Collection<UserObjDto> listByUser(@CurrentUser Account activeUser){
		return service.listByUser(activeUser);
	}
	
}
