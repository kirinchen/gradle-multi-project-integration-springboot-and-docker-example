package net.surfm.account.api;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.surfm.account.dao.AccountDao;
import net.surfm.account.dao.ObjDao;
import net.surfm.account.dto.ObjDto;
import net.surfm.account.service.ObjService;
import net.surfm.infrastructure.SkipbleConverter;

@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
@Validated
@Controller
@RequestMapping(value = "/api/v1/obj/")
public class ObjApiController {

	@Inject
	private ObjService service;
	@Inject
	private ObjDao dao;
	@Inject
	private SkipbleConverter skipbleConverter;
	
	@Value("${apikey}")
	private String apikey;

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "app/{appUid}/", method = RequestMethod.POST)
	public ObjDto create(@PathVariable String appUid,@RequestBody ObjDto dto) {
		return service.create(appUid, dto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)	
	public ObjDto findByUid(@PathVariable String uid) {
		return skipbleConverter.convert(dao.findById(uid).get(), new ObjDto(), StringUtils.EMPTY);
	}
	
/*	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/{uid}/coin/{count}/increase/", method = RequestMethod.POST)
	public ItemOutDto increaseCoin(@PathVariable String uid, @PathVariable int count, @Valid @RequestBody Bundle b,
			@RequestParam(value = "ApiKey") String apiKey) {
		if (!StringUtils.equals(apiKey, this.apikey))
			throw new ApiKeyFailException(apiKey);
		Account a = accountDao.findUserByUid(uid).get();
		return increaseCoin(a.getEmail(), count, b);
	}

	private ItemOutDto increaseCoin(String email, int count, Bundle b) {
		Item i = service.increaseCoin(email, count, b);
		return mapper.map(i, ItemOutDto.class);
	}

	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/{uid}/coins/increase/", method = RequestMethod.POST)
	public List<ItemOutDto> increaseCoins(@PathVariable String uid, @Valid @RequestBody List<PlusCoinBatchDto> bs,
			@RequestParam(value = "ApiKey") String apiKey) {
		if (!StringUtils.equals(apiKey, this.apikey))
			throw new ApiKeyFailException(apiKey);
		Account a = accountDao.findUserByUid(uid).get();
		return bs.stream().map(b -> {
			return increaseCoin(a.getEmail(), b.getCount(), b.getBundle());
		}).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/{uid}/coin/", method = RequestMethod.GET)
	public ItemOutDto getCoin(@PathVariable String uid, @RequestParam(value = "ApiKey") String apiKey) {
		if (!StringUtils.equals(apiKey, this.apikey))
			throw new ApiKeyFailException(apiKey);
		CommUtils.pl("uid="+uid);
		Account a = accountDao.findUserByUid(uid).get();
		Item i = service.getCoinItem(a);
		return mapper.map(i, ItemOutDto.class);
	}
*/


}
