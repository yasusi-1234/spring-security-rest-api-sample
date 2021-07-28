package com.example.demo.controller.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/*
 * リクエスト側にEmployee・Product・Category・Role・Salesの更新数情報を送ってもらうか
 * サーバー側でDB検索をしてSalesインスタンスを作成するか迷ったが、サーバー側で作成する
 * 方針とした
 */
@Data
public class SalesForm {
	@Min(1)
	private int soldCount;
	@NotBlank
	private String productId;
	@NotBlank
	private String employeeId;
//	@NotNull
//	@Valid
//	private EmployeeForm employeeForm;
}
