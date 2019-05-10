package net.surfm.account.api;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.surfm.account.service.ObjService;

@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
@Validated
@Controller
@RequestMapping(value = "/v1/item/")
public class ItemController {
	
	@Inject
	private ObjService service;
	@Inject
	private DozerBeanMapper mapper;
	
//	@ResponseStatus(HttpStatus.OK)
//	@ResponseBody
//	@RequestMapping(value = "/coin/", method = RequestMethod.GET)
//	public ItemOutDto getCoin(@CurrentUser Account activeUser) {
//		Item i = service.getCoinItem(activeUser);
//		return mapper.map(i, ItemOutDto.class);
//	}

}
