package com.example.demo.domain.service.exception;

/**
 * DBに既に登録されているメールアドレスが存在した場合に投げられる例外クラス
 *
 */
public class MailAddressAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = -842791777479456782L;

	public MailAddressAlreadyRegisteredException(String message) {
		super(message);
	}
}
