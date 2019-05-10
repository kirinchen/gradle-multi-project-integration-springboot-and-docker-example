package net.surfm.account.api;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import net.surfm.account.dao.AccountDao;
import net.surfm.account.service.ObjService;

@Validated
@Controller
@RequestMapping(value = "/admin/item/")
public class AdminItemController {

	@Inject
	private ObjService service;
	@Inject
	private DozerBeanMapper mapper;
	@Inject
	private AccountDao accountDao;

//	@ResponseStatus(OK)
//	@ResponseBody
//	@RequestMapping(method = GET, value = "query")
//	public List<UserItems> listUserItems(@RequestParam(required=false) String phone ,@RequestParam int page, @RequestParam int size) {
//		return service.listUserItems(phone,page, size);
//	}

//	@ResponseStatus(OK)
//	@ResponseBody
//	@RequestMapping(method = RequestMethod.POST, value = "{itemHash}/user/{userId}/plus")
//	public ItemOutDto increase(@PathVariable String itemHash, @PathVariable String userId, @RequestParam int amount,
//			@RequestBody Bundle bundle) {
//		Account a = accountDao.findUserByUid(userId).get();
//		String uid = ItemUtils.getUid(a.getId(), itemHash);
//		Item i = service.increase(uid, amount, bundle);
//		return mapper.map(i, ItemOutDto.class);
//	}


}
