package net.surfm.account.sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = { "net.surfm" })
@SpringBootApplication
@PropertySource({ "classpath:account_prod.properties", "classpath:account_${env}.properties" })
public class TestMain {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TestMain.class, args);
		
	}
	
}
