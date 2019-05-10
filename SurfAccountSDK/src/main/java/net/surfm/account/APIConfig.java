package net.surfm.account;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@PropertySource({ "classpath:account_prod.properties", "classpath:account_${env}.properties" })
public class APIConfig {



//    public static void main(String[] args) {
//        new SpringApplicationBuilder(APIConfig.class)
//            .profiles("app")
//            .run(args);
//        System.out.println("HI~");
//    }
	
	@Bean("Account_SDK")
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


}
