package net.surfm.account.api;

import java.util.Optional;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.surfm.account.dao.AppDao;
import net.surfm.account.dto.AppDto;
import net.surfm.account.model.App;
import net.surfm.account.service.AppService;
import net.surfm.infrastructure.OptionalDto;


@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
@Validated
@Controller
@RequestMapping(value = "/api/v1/app/")
public class AppApiController {
	
	@Inject
	private DozerBeanMapper mapper;
	@Inject
	private AppDao dao;
	@Inject
	private AppService service;

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{appName}", method = RequestMethod.GET)	
	public OptionalDto<AppDto> findByName(@PathVariable String appName) {
		Optional<App> appO = dao.findOptionalByName(appName);
		return appO.isPresent() ?  OptionalDto.gen(mapper.map(appO.get(), AppDto.class)) : OptionalDto.empty();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{appName}/create", method = RequestMethod.POST)		
	public AppDto create(@PathVariable String appName) {
		return service.create(appName);
	}
	

}
