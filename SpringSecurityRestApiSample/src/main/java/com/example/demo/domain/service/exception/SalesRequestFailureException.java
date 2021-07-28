package com.example.demo.domain.service.exception;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.domain.model.Product;

import lombok.Getter;

/**
 * 製品の情報の更新処理や、リクエストされた情報に異常があった場合に送出する例外
 *
 */
public class SalesRequestFailureException extends RuntimeException {

	private static final long serialVersionUID = -5090358530531364303L;
	@Getter
	private Map<String, Object> responseMap = new HashMap<>();

	public SalesRequestFailureException(String message) {
		super(message);
		responseMap.put("message", message);
	}

	public SalesRequestFailureException(String message, Product product) {
		super(message);
		responseMap.put("message", message);
		responseMap.put("product", product);
	}
}
