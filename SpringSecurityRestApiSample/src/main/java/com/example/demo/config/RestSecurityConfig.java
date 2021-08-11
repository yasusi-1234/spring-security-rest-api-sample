package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.config.security.handler.SimpleAccessDeniedHandler;
import com.example.demo.config.security.handler.SimpleAuthenticationEntryPoint;
import com.example.demo.config.security.handler.SimpleAuthenticationFailureHandler;
import com.example.demo.config.security.handler.SimpleAuthenticationSuccessHandler;
import com.example.demo.config.security.handler.SimpleTokenFilter;
import com.example.demo.domain.repository.EmployeeRepository;

@EnableWebSecurity
@Configuration
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${scurity.secret-key:secret}")
	private String secretKey = "secret";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// authorize 後に認可設定も加える
		http.authorizeRequests().antMatchers("/swagger-ui.htm**").permitAll() // 全て許可
				.anyRequest().authenticated(); // 他は認証されているUserのみ許可
		// exception
		http.exceptionHandling()
				// 未認証のユーザーが認証の必要なAPIにアクセスしたときの処理
				.authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
				// ユーザーは認証済みだが未認可のリソースへアクセスしたときの処理
				.accessDeniedHandler(new SimpleAccessDeniedHandler());

		// Login
		http.formLogin().loginProcessingUrl("/login").permitAll()
				// email
				.usernameParameter("mailAddress")
				// password
				.passwordParameter("password")
				// 認証に成功した際の処理
				.successHandler(new SimpleAuthenticationSuccessHandler(this.secretKey))
				// 認証に失敗した際の処理
				.failureHandler(new SimpleAuthenticationFailureHandler());

		// Logout
		http.logout().logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		// csrf設定：無効化
		http.csrf().disable();
		// session設定：無効化
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// cors設定
		http.cors().configurationSource(corsConfiguration());
		// ★4 AUTHORIZE
		// トークンの検証と認可を行うフィルター
		// ユーザー名・パスワードで認証を行うフィルター
		// (UsernamePasswordAuthenticationFilter)より前に実行するように設定します。
		http.addFilterBefore(new SimpleTokenFilter(employeeRepository, secretKey),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder,
			@Qualifier("employeeDetailsService") UserDetailsService userDetailsService, PasswordEncoder encoder)
			throws Exception {
		builder.eraseCredentials(true).userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/swagger-resources/**", "/v2/api-docs**", "/static**");
	}

	private CorsConfigurationSource corsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
		corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
				"Cache-Control", "Content-Type", "Accept-Language"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

}
