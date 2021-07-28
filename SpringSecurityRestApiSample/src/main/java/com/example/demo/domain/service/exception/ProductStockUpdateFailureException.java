package com.example.demo.domain.service.exception;

/**
 * 製品の更新処理に失敗した場合に呼び出される例外
 *
 */
public class ProductStockUpdateFailureException extends RuntimeException {

	private static final long serialVersionUID = 8842750012957018261L;

	public ProductStockUpdateFailureException(String message) {
		super(message);
	}
}
