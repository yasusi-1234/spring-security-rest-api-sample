package com.example.demo.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryForm {
	@NotBlank
	private String categoryName;
}
