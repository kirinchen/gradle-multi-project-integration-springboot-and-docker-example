package net.surfm.account.api;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.surfm.account.dto.UserObjDto;
import net.surfm.account.service.UserObjService;
import net.surfm.infrastructure.JsonMap;

@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
@Validated
@Controller
@RequestMapping(value = "/api/v1/userobj/")
public class UserObjApiController {
	
	@Inject
	private  UserObjService service;

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{ownerUid}/create/{itemUid}/", method = RequestMethod.POST)	
	public UserObjDto create(@PathVariable  String itemUid,@RequestBody JsonMap i,@RequestParam int amount,@PathVariable String ownerUid) {
		return service.create(itemUid, i, amount, ownerUid);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{userUid}/listAll", method = RequestMethod.GET)		
	public Collection<UserObjDto> listByUser(@PathVariable String userUid){
		return service.listByUser(userUid);
	}
	
//	@ResponseStatus(HttpStatus.OK)
//	@ResponseBody
//	@RequestMapping(value = "/coin/", method = RequestMethod.GET)
//	public ItemOutDto getCoin(@CurrentUser Account activeUser) {
//		Item i = service.getCoinItem(activeUser);
//		return mapper.map(i, ItemOutDto.class);
//	}

}
