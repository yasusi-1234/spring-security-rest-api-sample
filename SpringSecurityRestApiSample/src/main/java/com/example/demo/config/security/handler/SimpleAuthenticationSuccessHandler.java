package com.example.demo.config.security.handler;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.security.service.EmployeeDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * 認証が成功した時の処理
 *
 */
@Slf4j
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final Algorithm algorithm;

	private static final Long EXPIRATION_TIME = 1000L * 60L * 10L;

	public SimpleAuthenticationSuccessHandler(String secretKey) {
		Objects.requireNonNull(secretKey, "secretKey must not be null");
		this.algorithm = Algorithm.HMAC256(secretKey);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (response.isCommitted()) {
			log.info("Response has already been commited.");
			return;
		}
		setToken(response, generatedToken(authentication));
		response.setStatus(HttpStatus.OK.value());
		clearAuthenticationAttribute(request);
	}

	/**
	 * トークンを作成するメソッド
	 * 
	 * @param auth
	 * @return 作成されたトークン
	 */
	private String generatedToken(Authentication auth) {
		EmployeeDetails user = (EmployeeDetails) auth.getPrincipal();
		Date issuedAt = new Date();
		Date notBefore = new Date(issuedAt.getTime());
		Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);
		String token = JWT.create().withIssuedAt(issuedAt).withNotBefore(notBefore).withExpiresAt(expiresAt)
				.withSubject(user.getEmployee().getEmployeeId()).sign(this.algorithm);
		log.info("generated token: {}", token);
		return token;
	}

	private void setToken(HttpServletResponse response, String token) {
		// Access-Control-Expose-Headersが未指定の場合、Chromeなどのブラウザはセキュリティの観点で
		// 一部のヘッダ情報のみしかJavaScriptのコードからみえないように振舞う
		// フロント側からでも受け取れるよう、このヘッダーを追加
		response.setHeader("Access-Control-Expose-Headers", "*");
		response.setHeader("Authorization", String.format("Bearer %s", token));
	}

	/*
	 * 認証プロセス中にセッションに保存された可能性のある一時的な認証関連データを削除します。
	 */
	private void clearAuthenticationAttribute(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
