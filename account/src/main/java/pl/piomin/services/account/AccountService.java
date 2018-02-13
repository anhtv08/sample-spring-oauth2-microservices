package pl.piomin.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class AccountService {

	public static void main(String[] args) {
		SpringApplication.run(AccountService.class, args);
	}



	@Configuration
	 public static class securityConfig extends AuthorizationServerConfigurerAdapter{

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
					.authenticationManager(this.authenticationManager)
					.accessTokenConverter(accessTokenConverter())
					.tokenStore(tokenStore());
//				.accessTokenConverter();

			//.tokenStore(tokenStore());
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer
					.tokenKeyAccess("permitAll()")
					.checkTokenAccess("isAuthenticated()");
		}
		@Bean
		public JwtAccessTokenConverter accessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			converter.setSigningKey("123");
			return converter;
		}

		@Bean
		public TokenStore tokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}
	}


}
