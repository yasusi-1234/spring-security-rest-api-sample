package com.example.demo.config.security.handler;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.config.security.service.EmployeeDetails;
import com.example.demo.domain.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTokenFilter extends GenericFilterBean {

	private final EmployeeRepository employeeRepository;

	private final Algorithm algorithm;

	public SimpleTokenFilter(EmployeeRepository repository, String secretKey) {
		Objects.requireNonNull(secretKey, "secretKey must be not null");
		this.employeeRepository = repository;
		this.algorithm = Algorithm.HMAC256(secretKey);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = resolveToken(request);
		log.info("request remoteAddr: {}", request.getRemoteAddr());

		if (token == null) {
			log.info("token is null access");
			chain.doFilter(request, response);
			return;
		}

		try {
			authentication(verifyToken(token));
		} catch (TokenExpiredException ex) {
			log.error("token expired error.", ex);
			SecurityContextHolder.clearContext();

			HttpServletResponse res = ((HttpServletResponse) response);

			res.setHeader("Access-Control-Expose-Headers", "Token-Expired"); // これを設定しない場合JS側で取得できない場合がある
			res.setHeader("Token-Expired", "true");
			res.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

		} catch (JWTVerificationException ex) {
			log.error("verify token error.", ex);
			SecurityContextHolder.clearContext();
			((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(),
					HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
		log.info("token check register");
		chain.doFilter(request, response);
	}

	/**
	 * リクエストのAuthorizationからトークン情報の取得をする
	 * 
	 * @param request
	 * @return Authorization情報が無いまたはBearer の文字列が先頭になければNull、存在した場合は先頭のBearer
	 *         を除いた文字列トークン
	 */
	private String resolveToken(ServletRequest request) {
		String token = ((HttpServletRequest) request).getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7);
	}

	private DecodedJWT verifyToken(String token) {
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}

	private void authentication(DecodedJWT jwt) {
		String employeeId = jwt.getSubject();
		employeeRepository.findById(employeeId).ifPresent(employee -> {
			EmployeeDetails employeeDetails = new EmployeeDetails(employee);
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(employeeDetails, null, employeeDetails.getAuthorities()));
		});
	}

}
