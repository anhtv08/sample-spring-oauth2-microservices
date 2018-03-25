package pl.piomin.services.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(-10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @Autowired
	// private DataSource dataSource;


//	@Autowired
//	private AuthenticationManager authenticationManager;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.sessionManagement().
//		http.authorizeRequests().antMatchers("/**").permitAll();
//		http.antMatcher("/uua/oauth/token").
		http.authorizeRequests().anyRequest().authenticated()
				.and()
				.csrf().disable();
		http.antMatcher("/auu/oauth/token").anonymous();
//		http.csrf().disable();

//		and().formLogin().loginPage("/login").permitAll().and().httpBasic().disable();
	}


//	@Autowired
//	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
//		// @formatter:off
//		auth.inMemoryAuthentication()
//				.withUser("john").password("123").roles("USER").and()
//				.withUser("tom").password("111").roles("ADMIN").and()
//				.withUser("user1").password("pass").roles("USER").and()
//				.withUser("admin").password("nimda").roles("ADMIN");
//	}// @formatter:on


//	 @Override
//	 public void configure(AuthenticationManagerBuilder auth) throws Exception
//	 {
////		 auth.jdbcAuthentication().dataSource(dataSource);
//		 auth.inMemoryAuthentication().withUser("root").password("password").roles("USER");
//		 auth.parentAuthenticationManager(authenticationManager);
//
//	 }

}
