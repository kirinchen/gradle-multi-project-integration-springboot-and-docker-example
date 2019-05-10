package net.surfm.account;



import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Inject
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	       http.csrf().disable().headers() .
	       addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)).
	       and().formLogin().
	       defaultSuccessUrl("/hello").
	       loginPage("/login").
	       failureUrl("/login?error").
	       permitAll().and().logout().
	       logoutSuccessUrl("/login?logout")
	       .logoutUrl("/logout").permitAll().and()
	       .authorizeRequests()
	       .antMatchers("/admin/**").hasAuthority("ADMIN").and()
           .authorizeRequests()
           .antMatchers(
            		   "/",
            		   "/assets/**",
            		   "/home","/login","/logout",
            		   "/api/v1/public/**",
            		   "/api/v1/obj/**",
            		   "/api/v1/userobj/**",
            		   "/api/v1/app/**"
            		   ).permitAll().anyRequest().authenticated().and().
           logout().permitAll() ;



	}

	@Bean(name = "DaoAuthenticationProvider")
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return daoAuthenticationProvider;
	}
//
	@Bean(name = "passwordEncoder")
	public PasswordEncoder getPasswordEncoder() {
		PasswordEncoder passwordEncoder = new ScryptPasswordEncryptor();
		return passwordEncoder;
	}
//
//	
//	
//	
//
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication().withUser("test").password("test").roles("USER").and().withUser("test1")
				.password("test1").roles("USER").and().withUser("fabrice").password("fab123").roles("USER").and()
				.withUser("paulson").password("bond").roles("ADMIN", "USER");*/
		auth.authenticationProvider(daoAuthenticationProvider());
		auth.userDetailsService(userDetailsService);
	}
}
