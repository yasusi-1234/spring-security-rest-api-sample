package com.example.demo.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * ログイン失敗時に呼ばれるハンドラークラス
 *
 */
@Slf4j
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.warn("called authenticationFailureHandler");
		log.info("request data mailAddress : {}, password: {}", request.getParameter("mailAddress"),
				request.getParameter("password"));

		// ここらへんで本来失敗した回数などをカウントし、ロックなどの処理をするなどの処理が入るっぽい
		response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
	}

}
