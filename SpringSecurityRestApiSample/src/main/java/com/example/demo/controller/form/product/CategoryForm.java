package com.example.demo.controller.form.product;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryForm {
	@NotBlank
	private String categoryName;
}
