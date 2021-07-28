package com.example.demo.controller.form.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BindErrorHelper {

	/**
	 * BindingResultクラスからエラーの情報をMap化して返却するメソッド
	 * 
	 * @param bindingResult
	 * @return Key:エラーのあったフィールド Value:エラーメッセージ を格納したエラー情報Map
	 */
	public static Map<String, String> getErrorDetailsMap(BindingResult bindingResult) {
		List<FieldError> errorList = bindingResult.getFieldErrors();
		Map<String, String> errorMap = new HashMap<>();
		int errorCount = bindingResult.getErrorCount();
		for (int i = 0; i < errorCount; i++) {
			FieldError fieldError = errorList.get(i);
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return errorMap;
	}
}
