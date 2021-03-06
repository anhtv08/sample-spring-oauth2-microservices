package pl.piomin.services.gateway;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(-10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource);

//		.withClient("mobile-app")
//				.authorities("ROLE_CLIENT")
//				.authorizedGrantTypes("implicit", "refresh_token")
//				.scopes("read")
//				.autoApprove(true)
//				.and()

		auth.inMemoryAuthentication()
				.withUser("root").password("password").roles("USER");
//		auth.inMemoryAuthentication().withUser("mobile-app").


	}
	
}
