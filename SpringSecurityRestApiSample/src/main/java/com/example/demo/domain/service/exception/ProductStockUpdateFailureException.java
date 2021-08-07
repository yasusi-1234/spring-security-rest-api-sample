package com.example.demo.domain.service.exception;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.domain.model.Product;

import lombok.Getter;

/**
 * 製品の更新処理に失敗した場合に呼び出される例外
 *
 */
public class ProductStockUpdateFailureException extends RuntimeException {

	private static final long serialVersionUID = 8842750012957018261L;

	@Getter
	Map<String, Object> map = new HashMap<>();

	public ProductStockUpdateFailureException(String message) {
		super(message);
		map.put("message", message);
	}

	public ProductStockUpdateFailureException(String message, Product product) {
		super(message);
		map.put("message", message);
		map.put("product", product);
	}
}
