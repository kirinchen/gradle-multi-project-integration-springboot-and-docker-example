package net.surfm.account;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import net.surfm.account.aop.AuthorizationFilter;
import net.surfm.infrastructure.SkipbleConverter;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = { "net.surfm" })
@SpringBootApplication
@PropertySource({ "classpath:prod.properties", "classpath:${env}.properties" })
public class Application {
	

	@Inject
	private AuthorizationFilter authorizationFilter;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
	}

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }	
	
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		DozerBeanMapper ans = new DozerBeanMapper();
		return ans;
	}
	
	@Bean
	public SkipbleConverter skipbleConverter() {
		return new SkipbleConverter();
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(authorizationFilter);
		registration.addUrlPatterns("/api/v1/xgun/player/*");
		registration.addUrlPatterns("/v1/item/*");
		registration.addUrlPatterns("/base/*");
		registration.setName("WebCORSFilter");
		registration.setOrder(1);
		return registration;
	}



	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.setDateFormat(new StdDateFormat());
		return mapper;
	}
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setIncludeHeaders(false);
	    return loggingFilter;
	}	

	/*
	 * @Bean(name = "WebCORSFilter") public Filter webCORSFilter() { return new
	 * AuthorizationFilter(); }
	 */

}
