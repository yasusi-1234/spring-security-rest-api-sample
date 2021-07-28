package com.example.demo.controller.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {
	@NotBlank
	private String productName;
	@Min(value = 10)
	private int price;
	@Min(value = 0)
	private int stock;

//	private String imagePath; //画像データの受け取りも後に考える必要あり
	@Min(value = 1)
	private int categoryId;
//	@NotBlank
//	private String categoryName;
}
